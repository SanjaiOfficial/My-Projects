package com.example.moviesplatform.service;

import com.example.moviesplatform.domain.Genre;
import com.example.moviesplatform.domain.Movie;
import com.example.moviesplatform.domain.Rating;
import com.example.moviesplatform.domain.Review;
import com.example.moviesplatform.dto.MovieCreateRequest;
import com.example.moviesplatform.dto.ReviewRequest;
import com.example.moviesplatform.mapper.MovieMapper;
import com.example.moviesplatform.repository.MovieRepository;
import com.example.moviesplatform.repository.RatingRepository;
import com.example.moviesplatform.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;

    public MovieService(MovieRepository movieRepository,
                        RatingRepository ratingRepository,
                        ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Movie create(MovieCreateRequest request) {
        Movie movie = MovieMapper.fromCreate(request);
        return movieRepository.save(movie);
    }

    @Transactional(readOnly = true)
    public Page<Movie> search(String title, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, size == null ? 20 : size);
        if (title == null || title.isBlank()) {
            return movieRepository.findAll(pageable);
        }
        return movieRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Transactional(readOnly = true)
    public Movie getById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
    }

    @Transactional
    public Review addReview(Long movieId, ReviewRequest reviewRequest) {
        Movie movie = getById(movieId);
        Review review = new Review(movie, reviewRequest.author, reviewRequest.title, reviewRequest.content);
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviews(Long movieId) {
        Movie movie = getById(movieId);
        return reviewRepository.findByMovie(movie);
    }

    @Transactional(readOnly = true)
    public Page<Movie> topByYear(Integer year, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, size == null ? 20 : size);
        return movieRepository.findTopByYear(year, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Movie> topByGenres(Set<String> genreStrings, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, size == null ? 20 : size);
        List<Genre> genres = Optional.ofNullable(genreStrings).orElse(Set.of()).stream()
                .map(Genre::fromStringSafe).collect(Collectors.toList());
        return movieRepository.findTopByGenres(genres, pageable);
    }
}

