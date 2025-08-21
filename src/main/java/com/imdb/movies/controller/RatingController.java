package com.imdb.movies.controller;

import com.imdb.movies.dto.request.RatingCreateRequest;
import com.imdb.movies.dto.request.RatingUpdateRequest;
import com.imdb.movies.dto.response.ApiResponse;
import com.imdb.movies.dto.response.PageResponse;
import com.imdb.movies.dto.response.RatingResponse;
import com.imdb.movies.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Rating Management", description = "APIs for managing movie ratings")
public class RatingController {
    
    private final RatingService ratingService;
    
    @PostMapping
    @Operation(summary = "Create a new rating", description = "Creates a new movie rating by a user")
    public ResponseEntity<ApiResponse<RatingResponse>> createRating(
            @Valid @RequestBody RatingCreateRequest request) {
        log.info("Creating rating for user {} and movie {}", request.getUserId(), request.getMovieId());
        
        RatingResponse rating = ratingService.createRating(request);
        ApiResponse<RatingResponse> response = ApiResponse.success("Rating created successfully", rating);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get rating by ID", description = "Retrieves a rating by its ID")
    public ResponseEntity<ApiResponse<RatingResponse>> getRatingById(
            @Parameter(description = "Rating ID") @PathVariable Long id) {
        log.info("Fetching rating with ID: {}", id);
        
        RatingResponse rating = ratingService.getRatingById(id);
        ApiResponse<RatingResponse> response = ApiResponse.success(rating);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user/{userId}/movie/{movieId}")
    @Operation(summary = "Get rating by user and movie", description = "Retrieves a rating for a specific user and movie")
    public ResponseEntity<ApiResponse<RatingResponse>> getRatingByUserAndMovie(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Movie ID") @PathVariable Long movieId) {
        log.info("Fetching rating for user {} and movie {}", userId, movieId);
        
        RatingResponse rating = ratingService.getRatingByUserAndMovie(userId, movieId);
        ApiResponse<RatingResponse> response = ApiResponse.success(rating);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get ratings by user", description = "Retrieves all ratings by a specific user")
    public ResponseEntity<ApiResponse<PageResponse<RatingResponse>>> getRatingsByUser(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Fetching ratings for user {} with pagination: page={}, size={}, sortBy={}, sortDir={}", 
                userId, page, size, sortBy, sortDir);
        
        PageResponse<RatingResponse> ratings = ratingService.getRatingsByUser(userId, pageable);
        ApiResponse<PageResponse<RatingResponse>> response = ApiResponse.success(ratings);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Get ratings by movie", description = "Retrieves all ratings for a specific movie")
    public ResponseEntity<ApiResponse<PageResponse<RatingResponse>>> getRatingsByMovie(
            @Parameter(description = "Movie ID") @PathVariable Long movieId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Fetching ratings for movie {} with pagination: page={}, size={}, sortBy={}, sortDir={}", 
                movieId, page, size, sortBy, sortDir);
        
        PageResponse<RatingResponse> ratings = ratingService.getRatingsByMovie(movieId, pageable);
        ApiResponse<PageResponse<RatingResponse>> response = ApiResponse.success(ratings);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}/distribution")
    @Operation(summary = "Get rating distribution", description = "Retrieves rating distribution for a movie")
    public ResponseEntity<ApiResponse<Map<Double, Long>>> getRatingDistributionByMovie(
            @Parameter(description = "Movie ID") @PathVariable Long movieId) {
        log.info("Fetching rating distribution for movie {}", movieId);
        
        Map<Double, Long> distribution = ratingService.getRatingDistributionByMovie(movieId);
        ApiResponse<Map<Double, Long>> response = ApiResponse.success(distribution);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update rating", description = "Updates an existing rating")
    public ResponseEntity<ApiResponse<RatingResponse>> updateRating(
            @Parameter(description = "Rating ID") @PathVariable Long id,
            @Valid @RequestBody RatingUpdateRequest request) {
        log.info("Updating rating with ID: {}", id);
        
        RatingResponse rating = ratingService.updateRating(id, request);
        ApiResponse<RatingResponse> response = ApiResponse.success("Rating updated successfully", rating);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete rating", description = "Soft deletes a rating (marks as inactive)")
    public ResponseEntity<ApiResponse<Void>> deleteRating(
            @Parameter(description = "Rating ID") @PathVariable Long id) {
        log.info("Deleting rating with ID: {}", id);
        
        ratingService.deleteRating(id);
        ApiResponse<Void> response = ApiResponse.success("Rating deleted successfully", null);
        
        return ResponseEntity.ok(response);
    }
}