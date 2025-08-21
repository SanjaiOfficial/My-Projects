package com.imdb.movies.service;

import com.imdb.movies.dto.request.RatingCreateRequest;
import com.imdb.movies.dto.request.RatingUpdateRequest;
import com.imdb.movies.dto.response.PageResponse;
import com.imdb.movies.dto.response.RatingResponse;
import com.imdb.movies.entity.Movie;
import com.imdb.movies.entity.Rating;
import com.imdb.movies.entity.User;
import com.imdb.movies.exception.DuplicateResourceException;
import com.imdb.movies.exception.ResourceNotFoundException;
import com.imdb.movies.mapper.RatingMapper;
import com.imdb.movies.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RatingService {
    
    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final MovieService movieService;
    private final RatingMapper ratingMapper;
    
    public RatingResponse createRating(RatingCreateRequest request) {
        log.debug("Creating rating for user {} and movie {}", request.getUserId(), request.getMovieId());
        
        if (ratingRepository.existsByUserIdAndMovieId(request.getUserId(), request.getMovieId())) {
            throw new DuplicateResourceException("Rating already exists for this user and movie");
        }
        
        User user = userService.findEntityById(request.getUserId());
        Movie movie = movieService.findEntityById(request.getMovieId());
        
        Rating rating = ratingMapper.toEntity(request);
        rating.setUser(user);
        rating.setMovie(movie);
        
        Rating savedRating = ratingRepository.save(rating);
        
        // Update movie's average rating
        updateMovieAverageRating(movie);
        
        log.info("Created rating with ID: {} for user {} and movie {}", 
                savedRating.getId(), request.getUserId(), request.getMovieId());
        return ratingMapper.toResponse(savedRating);
    }
    
    @Transactional(readOnly = true)
    public RatingResponse getRatingById(Long id) {
        log.debug("Fetching rating with ID: {}", id);
        
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "id", id));
        
        return ratingMapper.toResponse(rating);
    }
    
    @Transactional(readOnly = true)
    public RatingResponse getRatingByUserAndMovie(Long userId, Long movieId) {
        log.debug("Fetching rating for user {} and movie {}", userId, movieId);
        
        Rating rating = ratingRepository.findByUserIdAndMovieId(userId, movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for user and movie"));
        
        return ratingMapper.toResponse(rating);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<RatingResponse> getRatingsByUser(Long userId, Pageable pageable) {
        log.debug("Fetching ratings for user {} with pagination: {}", userId, pageable);
        
        // Verify user exists
        userService.findEntityById(userId);
        
        Page<Rating> ratingPage = ratingRepository.findByUserIdAndIsActiveTrue(userId, pageable);
        return buildPageResponse(ratingPage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<RatingResponse> getRatingsByMovie(Long movieId, Pageable pageable) {
        log.debug("Fetching ratings for movie {} with pagination: {}", movieId, pageable);
        
        // Verify movie exists
        movieService.findEntityById(movieId);
        
        Page<Rating> ratingPage = ratingRepository.findByMovieIdAndIsActiveTrue(movieId, pageable);
        return buildPageResponse(ratingPage);
    }
    
    @Transactional(readOnly = true)
    public Map<Double, Long> getRatingDistributionByMovie(Long movieId) {
        log.debug("Fetching rating distribution for movie {}", movieId);
        
        // Verify movie exists
        movieService.findEntityById(movieId);
        
        List<Object[]> distribution = ratingRepository.findRatingDistributionByMovieId(movieId);
        return distribution.stream()
                .collect(Collectors.toMap(
                        result -> (Double) result[0],
                        result -> (Long) result[1]
                ));
    }
    
    public RatingResponse updateRating(Long id, RatingUpdateRequest request) {
        log.debug("Updating rating with ID: {}", id);
        
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "id", id));
        
        rating.setRating(request.getRating());
        Rating savedRating = ratingRepository.save(rating);
        
        // Update movie's average rating
        updateMovieAverageRating(savedRating.getMovie());
        
        log.info("Updated rating with ID: {}", savedRating.getId());
        return ratingMapper.toResponse(savedRating);
    }
    
    public void deleteRating(Long id) {
        log.debug("Deleting rating with ID: {}", id);
        
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "id", id));
        
        Movie movie = rating.getMovie();
        rating.setIsActive(false);
        ratingRepository.save(rating);
        
        // Update movie's average rating
        updateMovieAverageRating(movie);
        
        log.info("Soft deleted rating with ID: {}", id);
    }
    
    private void updateMovieAverageRating(Movie movie) {
        List<Rating> activeRatings = ratingRepository.findByMovieIdAndIsActiveTrue(movie.getId());
        
        if (activeRatings.isEmpty()) {
            movie.setAverageRating(null);
            movie.setRatingCount(0L);
        } else {
            double average = activeRatings.stream()
                    .mapToDouble(Rating::getRating)
                    .average()
                    .orElse(0.0);
            movie.setAverageRating(Math.round(average * 10.0) / 10.0); // Round to 1 decimal place
            movie.setRatingCount((long) activeRatings.size());
        }
        
        // Note: Movie will be saved automatically due to the transactional context
        log.debug("Updated average rating for movie {} to {}", movie.getId(), movie.getAverageRating());
    }
    
    private PageResponse<RatingResponse> buildPageResponse(Page<Rating> ratingPage) {
        return PageResponse.<RatingResponse>builder()
                .content(ratingPage.getContent().stream()
                        .map(ratingMapper::toResponse)
                        .toList())
                .page(ratingPage.getNumber())
                .size(ratingPage.getSize())
                .totalElements(ratingPage.getTotalElements())
                .totalPages(ratingPage.getTotalPages())
                .first(ratingPage.isFirst())
                .last(ratingPage.isLast())
                .hasNext(ratingPage.hasNext())
                .hasPrevious(ratingPage.hasPrevious())
                .build();
    }
}