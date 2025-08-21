package com.imdb.movies.controller;

import com.imdb.movies.dto.request.ReviewCreateRequest;
import com.imdb.movies.dto.request.ReviewUpdateRequest;
import com.imdb.movies.dto.response.ApiResponse;
import com.imdb.movies.dto.response.PageResponse;
import com.imdb.movies.dto.response.ReviewResponse;
import com.imdb.movies.service.ReviewService;
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

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Review Management", description = "APIs for managing movie reviews")
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @PostMapping
    @Operation(summary = "Create a new review", description = "Creates a new movie review by a user")
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @Valid @RequestBody ReviewCreateRequest request) {
        log.info("Creating review for user {} and movie {}", request.getUserId(), request.getMovieId());
        
        ReviewResponse review = reviewService.createReview(request);
        ApiResponse<ReviewResponse> response = ApiResponse.success("Review created successfully", review);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID", description = "Retrieves a review by its ID")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReviewById(
            @Parameter(description = "Review ID") @PathVariable Long id) {
        log.info("Fetching review with ID: {}", id);
        
        ReviewResponse review = reviewService.getReviewById(id);
        ApiResponse<ReviewResponse> response = ApiResponse.success(review);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user/{userId}/movie/{movieId}")
    @Operation(summary = "Get review by user and movie", description = "Retrieves a review for a specific user and movie")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReviewByUserAndMovie(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Movie ID") @PathVariable Long movieId) {
        log.info("Fetching review for user {} and movie {}", userId, movieId);
        
        ReviewResponse review = reviewService.getReviewByUserAndMovie(userId, movieId);
        ApiResponse<ReviewResponse> response = ApiResponse.success(review);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get reviews by user", description = "Retrieves all reviews by a specific user")
    public ResponseEntity<ApiResponse<PageResponse<ReviewResponse>>> getReviewsByUser(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Fetching reviews for user {} with pagination: page={}, size={}, sortBy={}, sortDir={}", 
                userId, page, size, sortBy, sortDir);
        
        PageResponse<ReviewResponse> reviews = reviewService.getReviewsByUser(userId, pageable);
        ApiResponse<PageResponse<ReviewResponse>> response = ApiResponse.success(reviews);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Get reviews by movie", description = "Retrieves all reviews for a specific movie")
    public ResponseEntity<ApiResponse<PageResponse<ReviewResponse>>> getReviewsByMovie(
            @Parameter(description = "Movie ID") @PathVariable Long movieId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Fetching reviews for movie {} with pagination: page={}, size={}, sortBy={}, sortDir={}", 
                movieId, page, size, sortBy, sortDir);
        
        PageResponse<ReviewResponse> reviews = reviewService.getReviewsByMovie(movieId, pageable);
        ApiResponse<PageResponse<ReviewResponse>> response = ApiResponse.success(reviews);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}/helpful")
    @Operation(summary = "Get most helpful reviews", description = "Retrieves most helpful reviews for a movie")
    public ResponseEntity<ApiResponse<PageResponse<ReviewResponse>>> getMostHelpfulReviewsByMovie(
            @Parameter(description = "Movie ID") @PathVariable Long movieId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        log.info("Fetching most helpful reviews for movie {} with pagination: page={}, size={}", movieId, page, size);
        
        PageResponse<ReviewResponse> reviews = reviewService.getMostHelpfulReviewsByMovie(movieId, pageable);
        ApiResponse<PageResponse<ReviewResponse>> response = ApiResponse.success(reviews);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}/recent")
    @Operation(summary = "Get recent reviews", description = "Retrieves recent reviews for a movie")
    public ResponseEntity<ApiResponse<PageResponse<ReviewResponse>>> getRecentReviewsByMovie(
            @Parameter(description = "Movie ID") @PathVariable Long movieId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        
        log.info("Fetching recent reviews for movie {} with pagination: page={}, size={}", movieId, page, size);
        
        PageResponse<ReviewResponse> reviews = reviewService.getRecentReviewsByMovie(movieId, pageable);
        ApiResponse<PageResponse<ReviewResponse>> response = ApiResponse.success(reviews);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search reviews", description = "Searches reviews by title or content")
    public ResponseEntity<ApiResponse<PageResponse<ReviewResponse>>> searchReviews(
            @Parameter(description = "Search term") @RequestParam String q,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Searching reviews with term: {} and pagination: page={}, size={}, sortBy={}, sortDir={}", 
                q, page, size, sortBy, sortDir);
        
        PageResponse<ReviewResponse> reviews = reviewService.searchReviews(q, pageable);
        ApiResponse<PageResponse<ReviewResponse>> response = ApiResponse.success(reviews);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update review", description = "Updates an existing review")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @Parameter(description = "Review ID") @PathVariable Long id,
            @Valid @RequestBody ReviewUpdateRequest request) {
        log.info("Updating review with ID: {}", id);
        
        ReviewResponse review = reviewService.updateReview(id, request);
        ApiResponse<ReviewResponse> response = ApiResponse.success("Review updated successfully", review);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/helpful")
    @Operation(summary = "Mark review as helpful", description = "Increments the helpful count for a review")
    public ResponseEntity<ApiResponse<ReviewResponse>> markReviewHelpful(
            @Parameter(description = "Review ID") @PathVariable Long id) {
        log.info("Marking review {} as helpful", id);
        
        ReviewResponse review = reviewService.markReviewHelpful(id);
        ApiResponse<ReviewResponse> response = ApiResponse.success("Review marked as helpful", review);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review", description = "Soft deletes a review (marks as inactive)")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @Parameter(description = "Review ID") @PathVariable Long id) {
        log.info("Deleting review with ID: {}", id);
        
        reviewService.deleteReview(id);
        ApiResponse<Void> response = ApiResponse.success("Review deleted successfully", null);
        
        return ResponseEntity.ok(response);
    }
}