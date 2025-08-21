package com.imdb.movies.repository;

import com.imdb.movies.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    Page<User> findByIsActiveTrue(Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.isActive = true AND " +
           "(LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<User> findActiveUsersBySearch(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT COUNT(r) FROM User u JOIN u.ratings r WHERE u.id = :userId AND r.isActive = true")
    Long countActiveRatingsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(r) FROM User u JOIN u.reviews r WHERE u.id = :userId AND r.isActive = true")
    Long countActiveReviewsByUserId(@Param("userId") Long userId);
}