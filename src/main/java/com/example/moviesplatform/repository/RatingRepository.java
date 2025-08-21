package com.example.moviesplatform.repository;

import com.example.moviesplatform.domain.Movie;
import com.example.moviesplatform.domain.Rating;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByMovie(Movie movie);

    Optional<Rating> findByMovieAndUserId(Movie movie, String userId);
}

