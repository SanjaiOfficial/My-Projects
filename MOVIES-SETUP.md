# Movies Platform (Spring Boot 3 + PostgreSQL)

IMDb-style Movies, Ratings & Reviews with CSV import.

## Prerequisites
- Java 17 (e.g., Temurin or OpenJDK)
- Maven 3.9+
- Docker (for PostgreSQL)

## 1) Start PostgreSQL
```bash
docker compose up -d
```
This starts a Postgres 16 instance on localhost:5432 with DB `movies` and user/password `movies/movies`.

## 2) Build and Run the App
```bash
mvn clean package -DskipTests
java -jar target/movies-platform-0.0.1-SNAPSHOT.jar
```
Or run directly with Maven:
```bash
mvn spring-boot:run
```

The app runs on http://localhost:8080

## 3) CSV Files (optional)
Place CSV files at:
- `./data/movies.csv` with header: `imdb_id,title,year,runtime_minutes,genres`
- `./data/ratings.csv` with header: `imdb_id,user_id,score`

Sample files are included under `data/`.

Enable auto-import on startup by editing `src/main/resources/application.yml`:
```yaml
app:
  import:
    auto: true
  csv:
    movie-file: ./data/movies.csv
    rating-file: ./data/ratings.csv
```

Or import manually via REST:
```bash
# import movies
curl -X POST http://localhost:8080/api/import/movies
# import ratings
curl -X POST http://localhost:8080/api/import/ratings
```

## 4) REST API (highlights)
- POST `/api/movies` create movie
- GET `/api/movies?q=title&page=0&size=20` search movies
- GET `/api/movies/{id}` get details
- POST `/api/movies/{id}/reviews` add review
- GET `/api/movies/{id}/reviews` list reviews
- POST `/api/movies/{id}/ratings` add rating `{ userId, score }`
- GET `/api/top/year?year=2020` top by year
- GET `/api/top/genres?genres=ACTION&genres=DRAMA` top by genres

## Notes
- Database connection is configured in `src/main/resources/application.yml`.
- Hibernate `ddl-auto: update` is enabled for convenience (not for production).