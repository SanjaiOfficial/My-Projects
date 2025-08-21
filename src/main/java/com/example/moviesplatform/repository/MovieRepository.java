package com.example.moviesplatform.repository;

import com.example.moviesplatform.domain.Genre;
import com.example.moviesplatform.domain.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByImdbId(String imdbId);

    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("select m from Movie m where (:year is null or m.year = :year) order by m.averageRating desc")
    Page<Movie> findTopByYear(@Param("year") Integer year, Pageable pageable);

    @Query("select distinct m from Movie m join m.genres g where g in :genres order by m.averageRating desc")
    Page<Movie> findTopByGenres(@Param("genres") List<Genre> genres, Pageable pageable);
}

