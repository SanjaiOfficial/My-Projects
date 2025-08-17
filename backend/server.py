from fastapi import FastAPI, APIRouter, HTTPException
from dotenv import load_dotenv
from starlette.middleware.cors import CORSMiddleware
from motor.motor_asyncio import AsyncIOMotorClient
import os
import logging
from pathlib import Path
from pydantic import BaseModel, Field
from typing import List, Optional
import uuid
from datetime import datetime, date
from enum import Enum

ROOT_DIR = Path(__file__).parent
load_dotenv(ROOT_DIR / '.env')

# MongoDB connection
mongo_url = os.environ['MONGO_URL']
client = AsyncIOMotorClient(mongo_url)
db = client[os.environ['DB_NAME']]

# Create the main app without a prefix
app = FastAPI()

# Create a router with the /api prefix
api_router = APIRouter(prefix="/api")

# Enums
class TaskStatus(str, Enum):
    TODO = "todo"
    IN_PROGRESS = "in_progress"
    DONE = "done"

class TaskPriority(str, Enum):
    LOW = "low"
    MEDIUM = "medium"
    HIGH = "high"

# Models
class Category(BaseModel):
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    name: str
    color: str
    created_at: datetime = Field(default_factory=datetime.utcnow)

class CategoryCreate(BaseModel):
    name: str
    color: str

class Task(BaseModel):
    id: str = Field(default_factory=lambda: str(uuid.uuid4()))
    title: str
    description: Optional[str] = ""
    status: TaskStatus = TaskStatus.TODO
    priority: TaskPriority = TaskPriority.MEDIUM
    category_id: Optional[str] = None
    category_name: Optional[str] = None
    due_date: Optional[date] = None
    created_at: datetime = Field(default_factory=datetime.utcnow)
    updated_at: datetime = Field(default_factory=datetime.utcnow)
    completed_at: Optional[datetime] = None

class TaskCreate(BaseModel):
    title: str
    description: Optional[str] = ""
    status: TaskStatus = TaskStatus.TODO
    priority: TaskPriority = TaskPriority.MEDIUM
    category_id: Optional[str] = None
    due_date: Optional[date] = None

class TaskUpdate(BaseModel):
    title: Optional[str] = None
    description: Optional[str] = None
    status: Optional[TaskStatus] = None
    priority: Optional[TaskPriority] = None
    category_id: Optional[str] = None
    due_date: Optional[date] = None

# Task routes
@api_router.get("/tasks", response_model=List[Task])
async def get_tasks():
    tasks = await db.tasks.find().to_list(1000)
    
    # Enrich tasks with category names
    for task in tasks:
        if task.get('category_id'):
            category = await db.categories.find_one({"id": task['category_id']})
            if category:
                task['category_name'] = category['name']
    
    return [Task(**task) for task in tasks]

@api_router.get("/tasks/{task_id}", response_model=Task)
async def get_task(task_id: str):
    task = await db.tasks.find_one({"id": task_id})
    if not task:
        raise HTTPException(status_code=404, detail="Task not found")
    
    # Enrich with category name
    if task.get('category_id'):
        category = await db.categories.find_one({"id": task['category_id']})
        if category:
            task['category_name'] = category['name']
    
    return Task(**task)

@api_router.post("/tasks", response_model=Task)
async def create_task(task_input: TaskCreate):
    task_dict = task_input.dict()
    task_obj = Task(**task_dict)
    
    # Enrich with category name if category_id provided
    if task_obj.category_id:
        category = await db.categories.find_one({"id": task_obj.category_id})
        if category:
            task_obj.category_name = category['name']
    
    await db.tasks.insert_one(task_obj.dict())
    return task_obj

@api_router.put("/tasks/{task_id}", response_model=Task)
async def update_task(task_id: str, task_update: TaskUpdate):
    task = await db.tasks.find_one({"id": task_id})
    if not task:
        raise HTTPException(status_code=404, detail="Task not found")
    
    update_data = task_update.dict(exclude_unset=True)
    if update_data:
        update_data['updated_at'] = datetime.utcnow()
        
        # Set completed_at when marking as done
        if update_data.get('status') == TaskStatus.DONE:
            update_data['completed_at'] = datetime.utcnow()
        elif update_data.get('status') in [TaskStatus.TODO, TaskStatus.IN_PROGRESS]:
            update_data['completed_at'] = None
        
        await db.tasks.update_one({"id": task_id}, {"$set": update_data})
    
    updated_task = await db.tasks.find_one({"id": task_id})
    
    # Enrich with category name
    if updated_task.get('category_id'):
        category = await db.categories.find_one({"id": updated_task['category_id']})
        if category:
            updated_task['category_name'] = category['name']
    
    return Task(**updated_task)

@api_router.delete("/tasks/{task_id}")
async def delete_task(task_id: str):
    result = await db.tasks.delete_one({"id": task_id})
    if result.deleted_count == 0:
        raise HTTPException(status_code=404, detail="Task not found")
    return {"message": "Task deleted successfully"}

# Category routes
@api_router.get("/categories", response_model=List[Category])
async def get_categories():
    categories = await db.categories.find().to_list(1000)
    return [Category(**category) for category in categories]

@api_router.post("/categories", response_model=Category)
async def create_category(category_input: CategoryCreate):
    category_dict = category_input.dict()
    category_obj = Category(**category_dict)
    await db.categories.insert_one(category_obj.dict())
    return category_obj

@api_router.delete("/categories/{category_id}")
async def delete_category(category_id: str):
    result = await db.categories.delete_one({"id": category_id})
    if result.deleted_count == 0:
        raise HTTPException(status_code=404, detail="Category not found")
    return {"message": "Category deleted successfully"}

# Dashboard stats
@api_router.get("/dashboard/stats")
async def get_dashboard_stats():
    total_tasks = await db.tasks.count_documents({})
    todo_tasks = await db.tasks.count_documents({"status": TaskStatus.TODO})
    in_progress_tasks = await db.tasks.count_documents({"status": TaskStatus.IN_PROGRESS})
    done_tasks = await db.tasks.count_documents({"status": TaskStatus.DONE})
    
    # High priority tasks count
    high_priority_tasks = await db.tasks.count_documents({"priority": TaskPriority.HIGH, "status": {"$ne": TaskStatus.DONE}})
    
    # Overdue tasks (past due date and not done)
    today = date.today()
    overdue_tasks = await db.tasks.count_documents({
        "due_date": {"$lt": today.isoformat()},
        "status": {"$ne": TaskStatus.DONE}
    })
    
    return {
        "total_tasks": total_tasks,
        "todo_tasks": todo_tasks,
        "in_progress_tasks": in_progress_tasks,
        "done_tasks": done_tasks,
        "high_priority_tasks": high_priority_tasks,
        "overdue_tasks": overdue_tasks
    }

# Initialize default categories
@api_router.post("/init/default-categories")
async def init_default_categories():
    default_categories = [
        {"name": "Work", "color": "#3B82F6"},
        {"name": "Personal", "color": "#10B981"},
        {"name": "Health", "color": "#F59E0B"},
        {"name": "Learning", "color": "#8B5CF6"},
        {"name": "Shopping", "color": "#EF4444"}
    ]
    
    existing_count = await db.categories.count_documents({})
    if existing_count == 0:
        for cat_data in default_categories:
            category_obj = Category(**cat_data)
            await db.categories.insert_one(category_obj.dict())
    
    return {"message": "Default categories initialized"}

# Include the router in the main app
app.include_router(api_router)

app.add_middleware(
    CORSMiddleware,
    allow_credentials=True,
    allow_origins=os.environ.get('CORS_ORIGINS', '*').split(','),
    allow_methods=["*"],
    allow_headers=["*"],
)

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

@app.on_event("shutdown")
async def shutdown_db_client():
    client.close()