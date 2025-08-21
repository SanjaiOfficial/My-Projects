# IMDb-Style Movies Platform

A comprehensive RESTful API platform for managing movies, ratings, and reviews built with Spring Boot 3, Java 17, and PostgreSQL.

## 🚀 Features

- **Movie Management**: Create, read, update, and delete movies with detailed information
- **User Management**: User registration and profile management
- **Rating System**: Users can rate movies on a scale of 0.5 to 10.0
- **Review System**: Comprehensive review system with helpful voting
- **CSV Import**: Bulk import movies and ratings from CSV files
- **Advanced Search**: Search movies by title, genre, director, actor, release date, and rating
- **Pagination**: All list endpoints support pagination and sorting
- **API Documentation**: Complete Swagger/OpenAPI documentation
- **Exception Handling**: Comprehensive error handling with meaningful responses
- **Data Validation**: Request validation with detailed error messages

## 🛠 Technology Stack

- **Backend**: Spring Boot 3.2.1, Java 17
- **Database**: PostgreSQL 15
- **ORM**: Spring Data JPA with Hibernate
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Validation**: Bean Validation (Hibernate Validator)
- **CSV Processing**: OpenCSV
- **DTO Mapping**: MapStruct
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose

## 📋 Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **Docker** and **Docker Compose**
- **Git**

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd movies-platform
```

### 2. Start PostgreSQL Database

```bash
docker-compose up -d postgres
```

This will start:
- PostgreSQL database on port `5432`
- PgAdmin web interface on port `8080` (admin@movies.com / admin123)

### 3. Build and Run the Application

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8090/api`

### 4. Import Sample Data (Optional)

You can import sample movie and rating data using the CSV import endpoints:

```bash
# Import movies
curl -X POST http://localhost:8090/api/admin/import/movies/resource

# Import ratings
curl -X POST http://localhost:8090/api/admin/import/ratings/resource

# Or import all data at once
curl -X POST http://localhost:8090/api/admin/import/all
```

## 📚 API Documentation

Once the application is running, you can access the interactive API documentation:

- **Swagger UI**: http://localhost:8090/api/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8090/api/v3/api-docs

## 🗂 Project Structure

```
src/main/java/com/imdb/movies/
├── controller/          # REST Controllers
├── dto/                # Data Transfer Objects
│   ├── request/        # Request DTOs
│   └── response/       # Response DTOs
├── entity/             # JPA Entities
├── exception/          # Custom Exceptions & Global Handler
├── mapper/             # MapStruct Mappers
├── repository/         # JPA Repositories
├── service/            # Business Logic Services
├── config/             # Configuration Classes
└── MoviesApplication.java

src/main/resources/
├── data/               # Sample CSV files
├── application.yml     # Application Configuration
└── ...
```

## 🔌 API Endpoints

### Movies
- `GET /movies` - Get all movies (paginated)
- `GET /movies/{id}` - Get movie by ID
- `GET /movies/imdb/{imdbId}` - Get movie by IMDB ID
- `POST /movies` - Create new movie
- `PUT /movies/{id}` - Update movie
- `DELETE /movies/{id}` - Delete movie (soft delete)
- `GET /movies/search?q={query}` - Search movies
- `GET /movies/genre/{genre}` - Get movies by genre
- `GET /movies/director/{director}` - Get movies by director
- `GET /movies/actor/{actor}` - Get movies by actor
- `GET /movies/top-rated` - Get top-rated movies
- `GET /movies/recent` - Get recently added movies

### Users
- `GET /users` - Get all users (paginated)
- `GET /users/{id}` - Get user by ID
- `GET /users/username/{username}` - Get user by username
- `POST /users` - Create new user
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user (soft delete)
- `GET /users/search?q={query}` - Search users

### Ratings
- `GET /ratings` - Get all ratings (paginated)
- `GET /ratings/{id}` - Get rating by ID
- `POST /ratings` - Create new rating
- `PUT /ratings/{id}` - Update rating
- `DELETE /ratings/{id}` - Delete rating
- `GET /ratings/user/{userId}` - Get ratings by user
- `GET /ratings/movie/{movieId}` - Get ratings by movie
- `GET /ratings/movie/{movieId}/distribution` - Get rating distribution

### Reviews
- `GET /reviews` - Get all reviews (paginated)
- `GET /reviews/{id}` - Get review by ID
- `POST /reviews` - Create new review
- `PUT /reviews/{id}` - Update review
- `DELETE /reviews/{id}` - Delete review
- `GET /reviews/user/{userId}` - Get reviews by user
- `GET /reviews/movie/{movieId}` - Get reviews by movie
- `GET /reviews/search?q={query}` - Search reviews
- `POST /reviews/{id}/helpful` - Mark review as helpful

### CSV Import (Admin)
- `POST /admin/import/movies/file` - Import movies from uploaded CSV
- `POST /admin/import/ratings/file` - Import ratings from uploaded CSV
- `POST /admin/import/movies/resource` - Import movies from classpath
- `POST /admin/import/ratings/resource` - Import ratings from classpath
- `POST /admin/import/all` - Import all data from classpath

## 📊 Database Schema

### Movies Table
- `id` (Primary Key)
- `title`, `original_title`, `description`
- `release_date`, `runtime_minutes`
- `genres[]`, `directors[]`, `actors[]`
- `poster_url`, `imdb_id`, `tmdb_id`
- `average_rating`, `rating_count`
- `is_active`, `created_at`, `updated_at`

### Users Table
- `id` (Primary Key)
- `username` (Unique), `email` (Unique)
- `first_name`, `last_name`
- `is_active`, `created_at`, `updated_at`

### Ratings Table
- `id` (Primary Key)
- `rating` (0.5 - 10.0)
- `user_id` (Foreign Key), `movie_id` (Foreign Key)
- `is_active`, `created_at`, `updated_at`
- Unique constraint on (user_id, movie_id)

### Reviews Table
- `id` (Primary Key)
- `title`, `content`, `is_spoiler`
- `helpful_count`
- `user_id` (Foreign Key), `movie_id` (Foreign Key)
- `is_active`, `created_at`, `updated_at`
- Unique constraint on (user_id, movie_id)

## 📝 CSV Import Format

### Movies CSV Format
```csv
title,original_title,description,release_date,runtime_minutes,genres,directors,actors,poster_url,imdb_id,tmdb_id
```

### Ratings CSV Format
```csv
imdb_id,rating,user_comment
```

## 🔧 Configuration

Key configuration properties in `application.yml`:

```yaml
server:
  port: 8090
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/movies_db
    username: movies_user
    password: movies_password
  
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: false

app:
  csv:
    import:
      batch-size: 1000
```

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

## 📦 Building for Production

### Create JAR file:
```bash
mvn clean package
```

### Run the JAR:
```bash
java -jar target/movies-platform-1.0.0.jar
```

### Docker Build:
```bash
# Build Docker image
docker build -t movies-platform .

# Run with Docker Compose
docker-compose up -d
```

## 🔍 Monitoring

The application includes Spring Boot Actuator for monitoring:

- **Health Check**: http://localhost:8090/api/actuator/health
- **Metrics**: http://localhost:8090/api/actuator/metrics
- **Info**: http://localhost:8090/api/actuator/info

## 🐛 Troubleshooting

### Common Issues:

1. **Database Connection Error**
   - Ensure PostgreSQL is running: `docker-compose ps`
   - Check connection details in `application.yml`

2. **Port Already in Use**
   - Change server port in `application.yml`
   - Or stop the process using the port

3. **CSV Import Fails**
   - Check CSV format matches expected structure
   - Ensure proper encoding (UTF-8)
   - Check application logs for detailed error messages

4. **OutOfMemoryError during CSV Import**
   - Reduce batch size in configuration
   - Increase JVM heap size: `-Xmx2g`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Make your changes and add tests
4. Commit your changes: `git commit -am 'Add some feature'`
5. Push to the branch: `git push origin feature-name`
6. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- Create an issue on GitHub
- Email: support@movies.com
- Documentation: Check Swagger UI for detailed API documentation

## 🚀 Deployment

### Production Checklist:
- [ ] Update database configuration for production
- [ ] Configure proper logging levels
- [ ] Set up SSL/HTTPS
- [ ] Configure proper CORS settings
- [ ] Set up database backups
- [ ] Configure monitoring and alerting
- [ ] Update Swagger server URLs
- [ ] Set up CI/CD pipeline

---

**Happy Coding!** 🎬🍿