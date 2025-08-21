package com.imdb.movies.service;

import com.imdb.movies.dto.request.ReviewCreateRequest;
import com.imdb.movies.dto.request.ReviewUpdateRequest;
import com.imdb.movies.dto.response.PageResponse;
import com.imdb.movies.dto.response.ReviewResponse;
import com.imdb.movies.entity.Movie;
import com.imdb.movies.entity.Review;
import com.imdb.movies.entity.User;
import com.imdb.movies.exception.DuplicateResourceException;
import com.imdb.movies.exception.ResourceNotFoundException;
import com.imdb.movies.mapper.ReviewMapper;
import com.imdb.movies.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final MovieService movieService;
    private final ReviewMapper reviewMapper;
    
    public ReviewResponse createReview(ReviewCreateRequest request) {
        log.debug("Creating review for user {} and movie {}", request.getUserId(), request.getMovieId());
        
        if (reviewRepository.existsByUserIdAndMovieId(request.getUserId(), request.getMovieId())) {
            throw new DuplicateResourceException("Review already exists for this user and movie");
        }
        
        User user = userService.findEntityById(request.getUserId());
        Movie movie = movieService.findEntityById(request.getMovieId());
        
        Review review = reviewMapper.toEntity(request);
        review.setUser(user);
        review.setMovie(movie);
        
        Review savedReview = reviewRepository.save(review);
        
        log.info("Created review with ID: {} for user {} and movie {}", 
                savedReview.getId(), request.getUserId(), request.getMovieId());
        return reviewMapper.toResponse(savedReview);
    }
    
    @Transactional(readOnly = true)
    public ReviewResponse getReviewById(Long id) {
        log.debug("Fetching review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        
        return reviewMapper.toResponse(review);
    }
    
    @Transactional(readOnly = true)
    public ReviewResponse getReviewByUserAndMovie(Long userId, Long movieId) {
        log.debug("Fetching review for user {} and movie {}", userId, movieId);
        
        Review review = reviewRepository.findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found for user and movie"));
        
        return reviewMapper.toResponse(review);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getReviewsByUser(Long userId, Pageable pageable) {
        log.debug("Fetching reviews for user {} with pagination: {}", userId, pageable);
        
        // Verify user exists
        userService.findEntityById(userId);
        
        Page<Review> reviewPage = reviewRepository.findByUserIdAndIsActiveTrue(userId, pageable);
        return buildPageResponse(reviewPage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getReviewsByMovie(Long movieId, Pageable pageable) {
        log.debug("Fetching reviews for movie {} with pagination: {}", movieId, pageable);
        
        // Verify movie exists
        movieService.findEntityById(movieId);
        
        Page<Review> reviewPage = reviewRepository.findByMovieIdAndIsActiveTrue(movieId, pageable);
        return buildPageResponse(reviewPage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getMostHelpfulReviewsByMovie(Long movieId, Pageable pageable) {
        log.debug("Fetching most helpful reviews for movie {} with pagination: {}", movieId, pageable);
        
        // Verify movie exists
        movieService.findEntityById(movieId);
        
        Page<Review> reviewPage = reviewRepository.findMostHelpfulReviewsByMovieId(movieId, pageable);
        return buildPageResponse(reviewPage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getRecentReviewsByMovie(Long movieId, Pageable pageable) {
        log.debug("Fetching recent reviews for movie {} with pagination: {}", movieId, pageable);
        
        // Verify movie exists
        movieService.findEntityById(movieId);
        
        Page<Review> reviewPage = reviewRepository.findRecentReviewsByMovieId(movieId, pageable);
        return buildPageResponse(reviewPage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> searchReviews(String search, Pageable pageable) {
        log.debug("Searching reviews with term: {} and pagination: {}", search, pageable);
        
        Page<Review> reviewPage = reviewRepository.findActiveReviewsBySearch(search, pageable);
        return buildPageResponse(reviewPage);
    }
    
    public ReviewResponse updateReview(Long id, ReviewUpdateRequest request) {
        log.debug("Updating review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        
        reviewMapper.updateEntity(review, request);
        Review savedReview = reviewRepository.save(review);
        
        log.info("Updated review with ID: {}", savedReview.getId());
        return reviewMapper.toResponse(savedReview);
    }
    
    public ReviewResponse markReviewHelpful(Long id) {
        log.debug("Marking review {} as helpful", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        
        review.setHelpfulCount(review.getHelpfulCount() + 1);
        Review savedReview = reviewRepository.save(review);
        
        log.info("Marked review {} as helpful, new count: {}", id, savedReview.getHelpfulCount());
        return reviewMapper.toResponse(savedReview);
    }
    
    public void deleteReview(Long id) {
        log.debug("Deleting review with ID: {}", id);
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        
        review.setIsActive(false);
        reviewRepository.save(review);
        
        log.info("Soft deleted review with ID: {}", id);
    }
    
    private PageResponse<ReviewResponse> buildPageResponse(Page<Review> reviewPage) {
        return PageResponse.<ReviewResponse>builder()
                .content(reviewPage.getContent().stream()
                        .map(reviewMapper::toResponse)
                        .toList())
                .page(reviewPage.getNumber())
                .size(reviewPage.getSize())
                .totalElements(reviewPage.getTotalElements())
                .totalPages(reviewPage.getTotalPages())
                .first(reviewPage.isFirst())
                .last(reviewPage.isLast())
                .hasNext(reviewPage.hasNext())
                .hasPrevious(reviewPage.hasPrevious())
                .build();
    }
}