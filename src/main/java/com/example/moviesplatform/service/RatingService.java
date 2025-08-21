package com.example.moviesplatform.service;

import com.example.moviesplatform.domain.Movie;
import com.example.moviesplatform.domain.Rating;
import com.example.moviesplatform.dto.RatingRequest;
import com.example.moviesplatform.repository.MovieRepository;
import com.example.moviesplatform.repository.RatingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingService {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    public RatingService(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public Rating addRating(Long movieId, RatingRequest request) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        ratingRepository.findByMovieAndUserId(movie, request.userId).ifPresent(existing -> {
            throw new IllegalArgumentException("User has already rated this movie");
        });

        Rating rating = new Rating(movie, request.userId, request.score);
        Rating saved = ratingRepository.save(rating);
        movie.applyNewRating(request.score);
        movieRepository.save(movie);
        return saved;
    }
}

