package com.imdb.movies.repository;

import com.imdb.movies.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    
    Optional<Rating> findByUserIdAndMovieId(Long userId, Long movieId);
    
    Page<Rating> findByUserIdAndIsActiveTrue(Long userId, Pageable pageable);
    
    Page<Rating> findByMovieIdAndIsActiveTrue(Long movieId, Pageable pageable);
    
    List<Rating> findByMovieIdAndIsActiveTrue(Long movieId);
    
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.movie.id = :movieId AND r.isActive = true")
    Optional<Double> findAverageRatingByMovieId(@Param("movieId") Long movieId);
    
    @Query("SELECT COUNT(r) FROM Rating r WHERE r.movie.id = :movieId AND r.isActive = true")
    Long countActiveRatingsByMovieId(@Param("movieId") Long movieId);
    
    @Query("SELECT r FROM Rating r WHERE r.user.id = :userId AND r.isActive = true ORDER BY r.createdAt DESC")
    Page<Rating> findRecentRatingsByUserId(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT r FROM Rating r WHERE r.movie.id = :movieId AND r.isActive = true ORDER BY r.createdAt DESC")
    Page<Rating> findRecentRatingsByMovieId(@Param("movieId") Long movieId, Pageable pageable);
    
    @Query("SELECT r.rating, COUNT(r) FROM Rating r WHERE r.movie.id = :movieId AND r.isActive = true GROUP BY r.rating ORDER BY r.rating")
    List<Object[]> findRatingDistributionByMovieId(@Param("movieId") Long movieId);
    
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
}