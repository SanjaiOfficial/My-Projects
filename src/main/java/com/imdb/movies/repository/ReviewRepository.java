package com.imdb.movies.repository;

import com.imdb.movies.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    Optional<Review> findByUserIdAndMovieId(Long userId, Long movieId);
    
    Page<Review> findByUserIdAndIsActiveTrue(Long userId, Pageable pageable);
    
    Page<Review> findByMovieIdAndIsActiveTrue(Long movieId, Pageable pageable);
    
    @Query("SELECT r FROM Review r WHERE r.movie.id = :movieId AND r.isActive = true ORDER BY r.helpfulCount DESC")
    Page<Review> findMostHelpfulReviewsByMovieId(@Param("movieId") Long movieId, Pageable pageable);
    
    @Query("SELECT r FROM Review r WHERE r.movie.id = :movieId AND r.isActive = true ORDER BY r.createdAt DESC")
    Page<Review> findRecentReviewsByMovieId(@Param("movieId") Long movieId, Pageable pageable);
    
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.isActive = true ORDER BY r.createdAt DESC")
    Page<Review> findRecentReviewsByUserId(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT r FROM Review r WHERE r.isActive = true AND " +
           "(LOWER(r.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(r.content) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Review> findActiveReviewsBySearch(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.movie.id = :movieId AND r.isActive = true")
    Long countActiveReviewsByMovieId(@Param("movieId") Long movieId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.user.id = :userId AND r.isActive = true")
    Long countActiveReviewsByUserId(@Param("userId") Long userId);
    
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
}