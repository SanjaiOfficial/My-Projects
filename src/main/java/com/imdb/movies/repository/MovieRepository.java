package com.imdb.movies.repository;

import com.imdb.movies.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    
    Optional<Movie> findByImdbId(String imdbId);
    
    Page<Movie> findByIsActiveTrue(Pageable pageable);
    
    @Query("SELECT m FROM Movie m WHERE m.isActive = true AND " +
           "(LOWER(m.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(m.originalTitle) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(m.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Movie> findActiveMoviesBySearch(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT m FROM Movie m WHERE m.isActive = true AND " +
           "EXISTS (SELECT 1 FROM m.genres g WHERE LOWER(g) LIKE LOWER(CONCAT('%', :genre, '%')))")
    Page<Movie> findActiveMoviesByGenre(@Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT m FROM Movie m WHERE m.isActive = true AND " +
           "EXISTS (SELECT 1 FROM m.directors d WHERE LOWER(d) LIKE LOWER(CONCAT('%', :director, '%')))")
    Page<Movie> findActiveMoviesByDirector(@Param("director") String director, Pageable pageable);
    
    @Query("SELECT m FROM Movie m WHERE m.isActive = true AND " +
           "EXISTS (SELECT 1 FROM m.actors a WHERE LOWER(a) LIKE LOWER(CONCAT('%', :actor, '%')))")
    Page<Movie> findActiveMoviesByActor(@Param("actor") String actor, Pageable pageable);
    
    @Query("SELECT m FROM Movie m WHERE m.isActive = true AND " +
           "m.releaseDate BETWEEN :startDate AND :endDate")
    Page<Movie> findActiveMoviesByReleaseDateBetween(@Param("startDate") LocalDate startDate, 
                                                     @Param("endDate") LocalDate endDate, 
                                                     Pageable pageable);
    
    @Query("SELECT m FROM Movie m WHERE m.isActive = true AND " +
           "m.averageRating >= :minRating ORDER BY m.averageRating DESC")
    Page<Movie> findActiveMoviesByMinRating(@Param("minRating") Double minRating, Pageable pageable);
    
    @Query("SELECT m FROM Movie m WHERE m.isActive = true ORDER BY m.averageRating DESC")
    Page<Movie> findTopRatedActiveMovies(Pageable pageable);
    
    @Query("SELECT m FROM Movie m WHERE m.isActive = true ORDER BY m.createdAt DESC")
    Page<Movie> findRecentlyAddedActiveMovies(Pageable pageable);
    
    @Query("SELECT m FROM Movie m WHERE m.isActive = true ORDER BY m.ratingCount DESC")
    Page<Movie> findMostRatedActiveMovies(Pageable pageable);
    
    @Query("SELECT DISTINCT g FROM Movie m JOIN m.genres g WHERE m.isActive = true ORDER BY g")
    List<String> findAllActiveGenres();
    
    @Query("SELECT DISTINCT d FROM Movie m JOIN m.directors d WHERE m.isActive = true ORDER BY d")
    List<String> findAllActiveDirectors();
    
    @Query("SELECT DISTINCT a FROM Movie m JOIN m.actors a WHERE m.isActive = true ORDER BY a")
    List<String> findAllActiveActors();
    
    boolean existsByImdbId(String imdbId);
}