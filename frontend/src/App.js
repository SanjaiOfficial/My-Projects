import { useState, useEffect } from "react";
import "./App.css";
import axios from "axios";

const BACKEND_URL = process.env.REACT_APP_BACKEND_URL;
const API = `${BACKEND_URL}/api`;

// Task status and priority constants
const TASK_STATUS = {
  TODO: 'todo',
  IN_PROGRESS: 'in_progress',
  DONE: 'done'
};

const TASK_PRIORITY = {
  LOW: 'low',
  MEDIUM: 'medium',
  HIGH: 'high'
};

const PRIORITY_COLORS = {
  high: 'bg-red-100 text-red-800 border-red-200',
  medium: 'bg-yellow-100 text-yellow-800 border-yellow-200',
  low: 'bg-green-100 text-green-800 border-green-200'
};

const STATUS_COLORS = {
  todo: 'bg-gray-100 text-gray-800',
  in_progress: 'bg-blue-100 text-blue-800',
  done: 'bg-green-100 text-green-800'
};

// Utility functions
const formatDate = (dateString) => {
  if (!dateString) return '';
  try {
    const date = new Date(dateString);
    // Check if date is valid
    if (isNaN(date.getTime())) return '';
    return date.toLocaleDateString();
  } catch (error) {
    console.warn('Date formatting error:', error);
    return '';
  }
};

const isOverdue = (dueDateString) => {
  if (!dueDateString) return false;
  try {
    const dueDate = new Date(dueDateString);
    if (isNaN(dueDate.getTime())) return false;
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return dueDate < today;
  } catch (error) {
    console.warn('Date validation error:', error);
    return false;
  }
};

// Main App Component
function App() {
  const [tasks, setTasks] = useState([]);
  const [categories, setCategories] = useState([]);
  const [stats, setStats] = useState({});
  const [view, setView] = useState('dashboard'); // dashboard, board, calendar
  const [showTaskModal, setShowTaskModal] = useState(false);
  const [editingTask, setEditingTask] = useState(null);
  const [showCategoryModal, setShowCategoryModal] = useState(false);
  const [filters, setFilters] = useState({
    status: 'all',
    priority: 'all',
    category: 'all'
  });

  // Fetch data
  const fetchTasks = async () => {
    try {
      const response = await axios.get(`${API}/tasks`);
      setTasks(response.data);
    } catch (error) {
      console.error('Error fetching tasks:', error);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await axios.get(`${API}/categories`);
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const fetchStats = async () => {
    try {
      const response = await axios.get(`${API}/dashboard/stats`);
      setStats(response.data);
    } catch (error) {
      console.error('Error fetching stats:', error);
    }
  };

  const initializeDefaultCategories = async () => {
    try {
      await axios.post(`${API}/init/default-categories`);
      fetchCategories();
    } catch (error) {
      console.error('Error initializing categories:', error);
    }
  };

  useEffect(() => {
    initializeDefaultCategories();
    fetchTasks();
    fetchCategories();
    fetchStats();
  }, []);

  // Task operations
  const createTask = async (taskData) => {
    try {
      await axios.post(`${API}/tasks`, taskData);
      fetchTasks();
      fetchStats();
      setShowTaskModal(false);
    } catch (error) {
      console.error('Error creating task:', error);
    }
  };

  const updateTask = async (taskId, updates) => {
    try {
      await axios.put(`${API}/tasks/${taskId}`, updates);
      fetchTasks();
      fetchStats();
    } catch (error) {
      console.error('Error updating task:', error);
    }
  };

  const deleteTask = async (taskId) => {
    try {
      await axios.delete(`${API}/tasks/${taskId}`);
      fetchTasks();
      fetchStats();
    } catch (error) {
      console.error('Error deleting task:', error);
    }
  };

  // Category operations
  const createCategory = async (categoryData) => {
    try {
      await axios.post(`${API}/categories`, categoryData);
      fetchCategories();
      setShowCategoryModal(false);
    } catch (error) {
      console.error('Error creating category:', error);
    }
  };

  // Filter tasks
  const getFilteredTasks = () => {
    return tasks.filter(task => {
      if (filters.status !== 'all' && task.status !== filters.status) return false;
      if (filters.priority !== 'all' && task.priority !== filters.priority) return false;
      if (filters.category !== 'all' && task.category_id !== filters.category) return false;
      return true;
    });
  };

  // Group tasks by status for board view
  const getTasksByStatus = () => {
    const filtered = getFilteredTasks();
    return {
      todo: filtered.filter(task => task.status === TASK_STATUS.TODO),
      in_progress: filtered.filter(task => task.status === TASK_STATUS.IN_PROGRESS),
      done: filtered.filter(task => task.status === TASK_STATUS.DONE)
    };
  };

  const formatDate = (dateString) => {
    if (!dateString) return '';
    try {
      const date = new Date(dateString);
      // Check if date is valid
      if (isNaN(date.getTime())) return '';
      return date.toLocaleDateString();
    } catch (error) {
      console.warn('Date formatting error:', error);
      return '';
    }
  };

  const isOverdue = (dueDateString) => {
    if (!dueDateString) return false;
    try {
      const dueDate = new Date(dueDateString);
      if (isNaN(dueDate.getTime())) return false;
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      return dueDate < today;
    } catch (error) {
      console.warn('Date validation error:', error);
      return false;
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-4">
            <div className="flex items-center space-x-4">
              <h1 className="text-2xl font-bold text-gray-900">TaskFlow</h1>
              <div className="flex space-x-1">
                <button
                  onClick={() => setView('dashboard')}
                  className={`px-3 py-2 rounded-lg text-sm font-medium transition-colors ${
                    view === 'dashboard' ? 'bg-blue-100 text-blue-700' : 'text-gray-600 hover:text-gray-900'
                  }`}
                >
                  Dashboard
                </button>
                <button
                  onClick={() => setView('board')}
                  className={`px-3 py-2 rounded-lg text-sm font-medium transition-colors ${
                    view === 'board' ? 'bg-blue-100 text-blue-700' : 'text-gray-600 hover:text-gray-900'
                  }`}
                >
                  Board
                </button>
                <button
                  onClick={() => setView('list')}
                  className={`px-3 py-2 rounded-lg text-sm font-medium transition-colors ${
                    view === 'list' ? 'bg-blue-100 text-blue-700' : 'text-gray-600 hover:text-gray-900'
                  }`}
                >
                  List
                </button>
              </div>
            </div>
            <div className="flex items-center space-x-3">
              <button
                onClick={() => setShowCategoryModal(true)}
                className="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
              >
                Categories
              </button>
              <button
                onClick={() => {
                  setEditingTask(null);
                  setShowTaskModal(true);
                }}
                className="px-4 py-2 bg-blue-600 text-white text-sm font-medium rounded-lg hover:bg-blue-700 transition-colors"
              >
                New Task
              </button>
            </div>
          </div>
        </div>
      </header>

      {/* Filters */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <div className="flex flex-wrap gap-4 mb-6">
          <select
            value={filters.status}
            onChange={(e) => setFilters({...filters, status: e.target.value})}
            className="px-3 py-2 border border-gray-300 rounded-lg text-sm"
          >
            <option value="all">All Status</option>
            <option value="todo">To Do</option>
            <option value="in_progress">In Progress</option>
            <option value="done">Done</option>
          </select>
          
          <select
            value={filters.priority}
            onChange={(e) => setFilters({...filters, priority: e.target.value})}
            className="px-3 py-2 border border-gray-300 rounded-lg text-sm"
          >
            <option value="all">All Priority</option>
            <option value="high">High</option>
            <option value="medium">Medium</option>
            <option value="low">Low</option>
          </select>
          
          <select
            value={filters.category}
            onChange={(e) => setFilters({...filters, category: e.target.value})}
            className="px-3 py-2 border border-gray-300 rounded-lg text-sm"
          >
            <option value="all">All Categories</option>
            {categories.map(category => (
              <option key={category.id} value={category.id}>{category.name}</option>
            ))}
          </select>
        </div>

        {/* Dashboard View */}
        {view === 'dashboard' && (
          <div className="space-y-6">
            {/* Stats Cards */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-6 gap-4">
              <div className="bg-white p-6 rounded-lg shadow-sm">
                <div className="text-2xl font-bold text-gray-900">{stats.total_tasks || 0}</div>
                <div className="text-sm text-gray-600">Total Tasks</div>
              </div>
              <div className="bg-white p-6 rounded-lg shadow-sm">
                <div className="text-2xl font-bold text-gray-600">{stats.todo_tasks || 0}</div>
                <div className="text-sm text-gray-600">To Do</div>
              </div>
              <div className="bg-white p-6 rounded-lg shadow-sm">
                <div className="text-2xl font-bold text-blue-600">{stats.in_progress_tasks || 0}</div>
                <div className="text-sm text-gray-600">In Progress</div>
              </div>
              <div className="bg-white p-6 rounded-lg shadow-sm">
                <div className="text-2xl font-bold text-green-600">{stats.done_tasks || 0}</div>
                <div className="text-sm text-gray-600">Completed</div>
              </div>
              <div className="bg-white p-6 rounded-lg shadow-sm">
                <div className="text-2xl font-bold text-red-600">{stats.high_priority_tasks || 0}</div>
                <div className="text-sm text-gray-600">High Priority</div>
              </div>
              <div className="bg-white p-6 rounded-lg shadow-sm">
                <div className="text-2xl font-bold text-orange-600">{stats.overdue_tasks || 0}</div>
                <div className="text-sm text-gray-600">Overdue</div>
              </div>
            </div>

            {/* Recent Tasks */}
            <div className="bg-white rounded-lg shadow-sm">
              <div className="p-6 border-b">
                <h2 className="text-lg font-medium text-gray-900">Recent Tasks</h2>
              </div>
              <div className="divide-y">
                {getFilteredTasks().slice(0, 10).map(task => (
                  <TaskCard key={task.id} task={task} onUpdate={updateTask} onDelete={deleteTask} onEdit={setEditingTask} />
                ))}
              </div>
            </div>
          </div>
        )}

        {/* Board View */}
        {view === 'board' && (
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            {Object.entries(getTasksByStatus()).map(([status, statusTasks]) => (
              <div key={status} className="bg-white rounded-lg shadow-sm">
                <div className="p-4 border-b">
                  <h3 className="font-medium text-gray-900 capitalize">
                    {status.replace('_', ' ')} ({statusTasks.length})
                  </h3>
                </div>
                <div className="p-4 space-y-3 max-h-96 overflow-y-auto">
                  {statusTasks.map(task => (
                    <div
                      key={task.id}
                      className="p-3 border border-gray-200 rounded-lg hover:shadow-sm transition-shadow cursor-pointer"
                      onClick={() => setEditingTask(task)}
                    >
                      <div className="flex items-center justify-between mb-2">
                        <span className="font-medium text-gray-900">{task.title}</span>
                        <span className={`px-2 py-1 text-xs rounded-full border ${PRIORITY_COLORS[task.priority]}`}>
                          {task.priority}
                        </span>
                      </div>
                      {task.description && (
                        <p className="text-sm text-gray-600 mb-2 line-clamp-2">{task.description}</p>
                      )}
                      <div className="flex items-center justify-between text-xs text-gray-500">
                        {task.category_name && (
                          <span className="bg-gray-100 px-2 py-1 rounded">{task.category_name}</span>
                        )}
                        {task.due_date && (
                          <span className={isOverdue(task.due_date) ? 'text-red-600 font-medium' : ''}>
                            Due: {formatDate(task.due_date)}
                          </span>
                        )}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ))}
          </div>
        )}

        {/* List View */}
        {view === 'list' && (
          <div className="bg-white rounded-lg shadow-sm">
            <div className="p-6 border-b">
              <h2 className="text-lg font-medium text-gray-900">All Tasks</h2>
            </div>
            <div className="divide-y">
              {getFilteredTasks().map(task => (
                <TaskCard key={task.id} task={task} onUpdate={updateTask} onDelete={deleteTask} onEdit={setEditingTask} />
              ))}
            </div>
          </div>
        )}
      </div>

      {/* Task Modal */}
      {showTaskModal && (
        <TaskModal
          task={editingTask}
          categories={categories}
          onClose={() => {
            setShowTaskModal(false);
            setEditingTask(null);
          }}
          onSave={editingTask ? (updates) => updateTask(editingTask.id, updates) : createTask}
        />
      )}

      {/* Edit Task Modal */}
      {editingTask && !showTaskModal && (
        <TaskModal
          task={editingTask}
          categories={categories}
          onClose={() => setEditingTask(null)}
          onSave={(updates) => {
            updateTask(editingTask.id, updates);
            setEditingTask(null);
          }}
        />
      )}

      {/* Category Modal */}
      {showCategoryModal && (
        <CategoryModal
          categories={categories}
          onClose={() => setShowCategoryModal(false)}
          onSave={createCategory}
        />
      )}
    </div>
  );
}

// Task Card Component
const TaskCard = ({ task, onUpdate, onDelete, onEdit }) => {
  const handleStatusChange = (newStatus) => {
    onUpdate(task.id, { status: newStatus });
  };

  return (
    <div className="p-6 hover:bg-gray-50 transition-colors">
      <div className="flex items-start justify-between">
        <div className="flex-1">
          <div className="flex items-center space-x-3 mb-2">
            <button
              onClick={() => handleStatusChange(
                task.status === TASK_STATUS.DONE ? TASK_STATUS.TODO : TASK_STATUS.DONE
              )}
              className={`w-5 h-5 rounded-full border-2 transition-colors ${
                task.status === TASK_STATUS.DONE
                  ? 'bg-green-500 border-green-500'
                  : 'border-gray-300 hover:border-green-500'
              }`}
            >
              {task.status === TASK_STATUS.DONE && (
                <svg className="w-3 h-3 text-white mx-auto mt-0.5" fill="currentColor" viewBox="0 0 20 20">
                  <path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd" />
                </svg>
              )}
            </button>
            <h3 className={`font-medium ${task.status === TASK_STATUS.DONE ? 'line-through text-gray-500' : 'text-gray-900'}`}>
              {task.title}
            </h3>
            <span className={`px-2 py-1 text-xs rounded-full ${STATUS_COLORS[task.status]}`}>
              {task.status.replace('_', ' ')}
            </span>
            <span className={`px-2 py-1 text-xs rounded-full border ${PRIORITY_COLORS[task.priority]}`}>
              {task.priority}
            </span>
          </div>
          {task.description && (
            <p className="text-gray-600 mb-2">{task.description}</p>
          )}
          <div className="flex items-center space-x-4 text-sm text-gray-500">
            {task.category_name && (
              <span className="bg-gray-100 px-2 py-1 rounded">{task.category_name}</span>
            )}
            {task.due_date && (
              <span className={isOverdue(task.due_date) ? 'text-red-600 font-medium' : ''}>
                Due: {formatDate(task.due_date)}
              </span>
            )}
            <span>Created: {formatDate(task.created_at)}</span>
          </div>
        </div>
        <div className="flex items-center space-x-2 ml-4">
          <button
            onClick={() => onEdit(task)}
            className="text-blue-600 hover:text-blue-700 transition-colors"
          >
            Edit
          </button>
          <button
            onClick={() => onDelete(task.id)}
            className="text-red-600 hover:text-red-700 transition-colors"
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  );
};

// Task Modal Component
const TaskModal = ({ task, categories, onClose, onSave }) => {
  const [formData, setFormData] = useState({
    title: task?.title || '',
    description: task?.description || '',
    status: task?.status || TASK_STATUS.TODO,
    priority: task?.priority || TASK_PRIORITY.MEDIUM,
    category_id: task?.category_id || '',
    due_date: task?.due_date || ''
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    const saveData = { ...formData };
    if (!saveData.due_date) delete saveData.due_date;
    if (!saveData.category_id) delete saveData.category_id;
    onSave(saveData);
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div className="bg-white rounded-lg shadow-xl max-w-md w-full max-h-96 overflow-y-auto">
        <form onSubmit={handleSubmit} className="p-6">
          <h2 className="text-lg font-medium text-gray-900 mb-4">
            {task ? 'Edit Task' : 'Create Task'}
          </h2>
          
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Title</label>
              <input
                type="text"
                required
                value={formData.title}
                onChange={(e) => setFormData({...formData, title: e.target.value})}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
              <textarea
                rows="3"
                value={formData.description}
                onChange={(e) => setFormData({...formData, description: e.target.value})}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
            </div>
            
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
                <select
                  value={formData.status}
                  onChange={(e) => setFormData({...formData, status: e.target.value})}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                >
                  <option value="todo">To Do</option>
                  <option value="in_progress">In Progress</option>
                  <option value="done">Done</option>
                </select>
              </div>
              
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Priority</label>
                <select
                  value={formData.priority}
                  onChange={(e) => setFormData({...formData, priority: e.target.value})}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                >
                  <option value="low">Low</option>
                  <option value="medium">Medium</option>
                  <option value="high">High</option>
                </select>
              </div>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Category</label>
              <select
                value={formData.category_id}
                onChange={(e) => setFormData({...formData, category_id: e.target.value})}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              >
                <option value="">No Category</option>
                {categories.map(category => (
                  <option key={category.id} value={category.id}>{category.name}</option>
                ))}
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Due Date</label>
              <input
                type="date"
                value={formData.due_date}
                onChange={(e) => setFormData({...formData, due_date: e.target.value})}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
            </div>
          </div>
          
          <div className="flex space-x-3 mt-6">
            <button
              type="submit"
              className="flex-1 bg-blue-600 text-white py-2 px-4 rounded-lg hover:bg-blue-700 transition-colors"
            >
              {task ? 'Update' : 'Create'}
            </button>
            <button
              type="button"
              onClick={onClose}
              className="flex-1 bg-gray-300 text-gray-700 py-2 px-4 rounded-lg hover:bg-gray-400 transition-colors"
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

// Category Modal Component
const CategoryModal = ({ categories, onClose, onSave }) => {
  const [formData, setFormData] = useState({ name: '', color: '#3B82F6' });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(formData);
    setFormData({ name: '', color: '#3B82F6' });
  };

  const predefinedColors = [
    '#3B82F6', '#10B981', '#F59E0B', '#8B5CF6', '#EF4444',
    '#06B6D4', '#84CC16', '#F97316', '#EC4899', '#6B7280'
  ];

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div className="bg-white rounded-lg shadow-xl max-w-md w-full">
        <div className="p-6">
          <h2 className="text-lg font-medium text-gray-900 mb-4">Manage Categories</h2>
          
          <form onSubmit={handleSubmit} className="space-y-4 mb-6">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Category Name</label>
              <input
                type="text"
                required
                value={formData.name}
                onChange={(e) => setFormData({...formData, name: e.target.value})}
                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              />
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Color</label>
              <div className="flex space-x-2 mb-2">
                {predefinedColors.map(color => (
                  <button
                    key={color}
                    type="button"
                    onClick={() => setFormData({...formData, color})}
                    className={`w-8 h-8 rounded-full border-2 transition-all ${
                      formData.color === color ? 'border-gray-400 scale-110' : 'border-gray-200'
                    }`}
                    style={{ backgroundColor: color }}
                  />
                ))}
              </div>
              <input
                type="color"
                value={formData.color}
                onChange={(e) => setFormData({...formData, color: e.target.value})}
                className="w-full h-10 rounded-lg border border-gray-300"
              />
            </div>
            
            <button
              type="submit"
              className="w-full bg-blue-600 text-white py-2 px-4 rounded-lg hover:bg-blue-700 transition-colors"
            >
              Add Category
            </button>
          </form>
          
          {/* Existing Categories */}
          <div className="space-y-2 mb-6 max-h-40 overflow-y-auto">
            {categories.map(category => (
              <div key={category.id} className="flex items-center justify-between p-2 border border-gray-200 rounded">
                <div className="flex items-center space-x-3">
                  <div
                    className="w-4 h-4 rounded-full"
                    style={{ backgroundColor: category.color }}
                  />
                  <span className="text-sm">{category.name}</span>
                </div>
              </div>
            ))}
          </div>
          
          <button
            onClick={onClose}
            className="w-full bg-gray-300 text-gray-700 py-2 px-4 rounded-lg hover:bg-gray-400 transition-colors"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
};

export default App;