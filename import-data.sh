#!/bin/bash

echo "📊 Importing sample data to Movies Platform..."

BASE_URL="http://localhost:8090/api"

# Function to check if application is running
check_app() {
    curl -s "$BASE_URL/actuator/health" > /dev/null 2>&1
    return $?
}

# Wait for application to be ready
echo "⏳ Waiting for application to be ready..."
while ! check_app; do
    echo "⏳ Application is not ready yet, waiting..."
    sleep 5
done

echo "✅ Application is ready!"

# Import movies
echo "🎬 Importing movies..."
MOVIE_RESPONSE=$(curl -s -X POST "$BASE_URL/admin/import/movies/resource")
echo "Movies import response: $MOVIE_RESPONSE"

# Wait a moment
sleep 2

# Import ratings
echo "⭐ Importing ratings..."
RATING_RESPONSE=$(curl -s -X POST "$BASE_URL/admin/import/ratings/resource")
echo "Ratings import response: $RATING_RESPONSE"

echo ""
echo "✅ Data import completed!"
echo "📖 You can now explore the API at: $BASE_URL/swagger-ui/index.html"
echo "🎯 Try these endpoints:"
echo "   - GET $BASE_URL/movies (get all movies)"
echo "   - GET $BASE_URL/movies/top-rated (get top-rated movies)"
echo "   - GET $BASE_URL/users (get all users)"
echo "   - GET $BASE_URL/ratings/movie/1 (get ratings for movie 1)"