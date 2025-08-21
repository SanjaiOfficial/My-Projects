package com.imdb.movies.controller;

import com.imdb.movies.dto.request.MovieCreateRequest;
import com.imdb.movies.dto.request.MovieUpdateRequest;
import com.imdb.movies.dto.response.ApiResponse;
import com.imdb.movies.dto.response.MovieResponse;
import com.imdb.movies.dto.response.PageResponse;
import com.imdb.movies.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Movie Management", description = "APIs for managing movies")
public class MovieController {
    
    private final MovieService movieService;
    
    @PostMapping
    @Operation(summary = "Create a new movie", description = "Creates a new movie entry")
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(
            @Valid @RequestBody MovieCreateRequest request) {
        log.info("Creating movie with title: {}", request.getTitle());
        
        MovieResponse movie = movieService.createMovie(request);
        ApiResponse<MovieResponse> response = ApiResponse.success("Movie created successfully", movie);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get movie by ID", description = "Retrieves a movie by its ID")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovieById(
            @Parameter(description = "Movie ID") @PathVariable Long id) {
        log.info("Fetching movie with ID: {}", id);
        
        MovieResponse movie = movieService.getMovieById(id);
        ApiResponse<MovieResponse> response = ApiResponse.success(movie);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/imdb/{imdbId}")
    @Operation(summary = "Get movie by IMDB ID", description = "Retrieves a movie by its IMDB ID")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovieByImdbId(
            @Parameter(description = "IMDB ID") @PathVariable String imdbId) {
        log.info("Fetching movie with IMDB ID: {}", imdbId);
        
        MovieResponse movie = movieService.getMovieByImdbId(imdbId);
        ApiResponse<MovieResponse> response = ApiResponse.success(movie);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all movies", description = "Retrieves all active movies with pagination")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getAllMovies(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Fetching all movies with pagination: page={}, size={}, sortBy={}, sortDir={}", 
                page, size, sortBy, sortDir);
        
        PageResponse<MovieResponse> movies = movieService.getAllMovies(pageable);
        ApiResponse<PageResponse<MovieResponse>> response = ApiResponse.success(movies);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search movies", description = "Searches movies by title, original title, or description")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> searchMovies(
            @Parameter(description = "Search term") @RequestParam String q,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Searching movies with term: {} and pagination: page={}, size={}, sortBy={}, sortDir={}", 
                q, page, size, sortBy, sortDir);
        
        PageResponse<MovieResponse> movies = movieService.searchMovies(q, pageable);
        ApiResponse<PageResponse<MovieResponse>> response = ApiResponse.success(movies);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/genre/{genre}")
    @Operation(summary = "Get movies by genre", description = "Retrieves movies by genre")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getMoviesByGenre(
            @Parameter(description = "Genre") @PathVariable String genre,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "averageRating") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Fetching movies by genre: {} with pagination: page={}, size={}, sortBy={}, sortDir={}", 
                genre, page, size, sortBy, sortDir);
        
        PageResponse<MovieResponse> movies = movieService.getMoviesByGenre(genre, pageable);
        ApiResponse<PageResponse<MovieResponse>> response = ApiResponse.success(movies);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/director/{director}")
    @Operation(summary = "Get movies by director", description = "Retrieves movies by director")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getMoviesByDirector(
            @Parameter(description = "Director name") @PathVariable String director,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "releaseDate") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Fetching movies by director: {} with pagination: page={}, size={}, sortBy={}, sortDir={}", 
                director, page, size, sortBy, sortDir);
        
        PageResponse<MovieResponse> movies = movieService.getMoviesByDirector(director, pageable);
        ApiResponse<PageResponse<MovieResponse>> response = ApiResponse.success(movies);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/actor/{actor}")
    @Operation(summary = "Get movies by actor", description = "Retrieves movies by actor")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getMoviesByActor(
            @Parameter(description = "Actor name") @PathVariable String actor,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "releaseDate") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Fetching movies by actor: {} with pagination: page={}, size={}, sortBy={}, sortDir={}", 
                actor, page, size, sortBy, sortDir);
        
        PageResponse<MovieResponse> movies = movieService.getMoviesByActor(actor, pageable);
        ApiResponse<PageResponse<MovieResponse>> response = ApiResponse.success(movies);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/release-date")
    @Operation(summary = "Get movies by release date range", description = "Retrieves movies within a date range")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getMoviesByReleaseDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date (yyyy-MM-dd)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "releaseDate") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Fetching movies between {} and {} with pagination: page={}, size={}, sortBy={}, sortDir={}", 
                startDate, endDate, page, size, sortBy, sortDir);
        
        PageResponse<MovieResponse> movies = movieService.getMoviesByReleaseDateRange(startDate, endDate, pageable);
        ApiResponse<PageResponse<MovieResponse>> response = ApiResponse.success(movies);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/top-rated")
    @Operation(summary = "Get top rated movies", description = "Retrieves movies ordered by rating")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getTopRatedMovies(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        log.info("Fetching top rated movies with pagination: page={}, size={}", page, size);
        
        PageResponse<MovieResponse> movies = movieService.getTopRatedMovies(pageable);
        ApiResponse<PageResponse<MovieResponse>> response = ApiResponse.success(movies);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/recent")
    @Operation(summary = "Get recently added movies", description = "Retrieves recently added movies")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getRecentlyAddedMovies(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        log.info("Fetching recently added movies with pagination: page={}, size={}", page, size);
        
        PageResponse<MovieResponse> movies = movieService.getRecentlyAddedMovies(pageable);
        ApiResponse<PageResponse<MovieResponse>> response = ApiResponse.success(movies);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/most-rated")
    @Operation(summary = "Get most rated movies", description = "Retrieves movies with the most ratings")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getMostRatedMovies(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        log.info("Fetching most rated movies with pagination: page={}, size={}", page, size);
        
        PageResponse<MovieResponse> movies = movieService.getMostRatedMovies(pageable);
        ApiResponse<PageResponse<MovieResponse>> response = ApiResponse.success(movies);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/rating/{minRating}")
    @Operation(summary = "Get movies by minimum rating", description = "Retrieves movies with rating above threshold")
    public ResponseEntity<ApiResponse<PageResponse<MovieResponse>>> getMoviesByMinRating(
            @Parameter(description = "Minimum rating") @PathVariable Double minRating,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        log.info("Fetching movies with minimum rating: {} and pagination: page={}, size={}", minRating, page, size);
        
        PageResponse<MovieResponse> movies = movieService.getMoviesByMinRating(minRating, pageable);
        ApiResponse<PageResponse<MovieResponse>> response = ApiResponse.success(movies);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/genres")
    @Operation(summary = "Get all genres", description = "Retrieves all available movie genres")
    public ResponseEntity<ApiResponse<List<String>>> getAllGenres() {
        log.info("Fetching all genres");
        
        List<String> genres = movieService.getAllGenres();
        ApiResponse<List<String>> response = ApiResponse.success(genres);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/directors")
    @Operation(summary = "Get all directors", description = "Retrieves all available movie directors")
    public ResponseEntity<ApiResponse<List<String>>> getAllDirectors() {
        log.info("Fetching all directors");
        
        List<String> directors = movieService.getAllDirectors();
        ApiResponse<List<String>> response = ApiResponse.success(directors);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/actors")
    @Operation(summary = "Get all actors", description = "Retrieves all available movie actors")
    public ResponseEntity<ApiResponse<List<String>>> getAllActors() {
        log.info("Fetching all actors");
        
        List<String> actors = movieService.getAllActors();
        ApiResponse<List<String>> response = ApiResponse.success(actors);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update movie", description = "Updates an existing movie")
    public ResponseEntity<ApiResponse<MovieResponse>> updateMovie(
            @Parameter(description = "Movie ID") @PathVariable Long id,
            @Valid @RequestBody MovieUpdateRequest request) {
        log.info("Updating movie with ID: {}", id);
        
        MovieResponse movie = movieService.updateMovie(id, request);
        ApiResponse<MovieResponse> response = ApiResponse.success("Movie updated successfully", movie);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete movie", description = "Soft deletes a movie (marks as inactive)")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(
            @Parameter(description = "Movie ID") @PathVariable Long id) {
        log.info("Deleting movie with ID: {}", id);
        
        movieService.deleteMovie(id);
        ApiResponse<Void> response = ApiResponse.success("Movie deleted successfully", null);
        
        return ResponseEntity.ok(response);
    }
}