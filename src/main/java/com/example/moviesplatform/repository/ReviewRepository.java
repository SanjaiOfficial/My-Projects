package com.example.moviesplatform.repository;

import com.example.moviesplatform.domain.Movie;
import com.example.moviesplatform.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovie(Movie movie);
}

