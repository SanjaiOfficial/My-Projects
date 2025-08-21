#!/bin/bash

echo "🎬 Starting IMDb-Style Movies Platform..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Start PostgreSQL database
echo "🐘 Starting PostgreSQL database..."
docker-compose up -d postgres

# Wait for database to be ready
echo "⏳ Waiting for database to be ready..."
sleep 10

# Check if database is ready
until docker-compose exec postgres pg_isready -U movies_user -d movies_db > /dev/null 2>&1; do
    echo "⏳ Database is not ready yet, waiting..."
    sleep 5
done

echo "✅ Database is ready!"

# Build the application
echo "🔨 Building the application..."
mvn clean compile -q

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi

echo "✅ Build successful!"

# Start the Spring Boot application
echo "🚀 Starting the application..."
echo "📍 Application will be available at: http://localhost:8090/api"
echo "📖 API Documentation: http://localhost:8090/api/swagger-ui/index.html"
echo "🐘 PgAdmin: http://localhost:8080 (admin@movies.com / admin123)"
echo ""
echo "🛑 Press Ctrl+C to stop the application"
echo ""

mvn spring-boot:run