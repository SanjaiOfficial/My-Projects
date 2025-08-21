package com.imdb.movies.service;

import com.imdb.movies.dto.request.MovieCreateRequest;
import com.imdb.movies.dto.request.MovieUpdateRequest;
import com.imdb.movies.dto.response.MovieResponse;
import com.imdb.movies.dto.response.PageResponse;
import com.imdb.movies.entity.Movie;
import com.imdb.movies.exception.DuplicateResourceException;
import com.imdb.movies.exception.ResourceNotFoundException;
import com.imdb.movies.mapper.MovieMapper;
import com.imdb.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MovieService {
    
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    
    public MovieResponse createMovie(MovieCreateRequest request) {
        log.debug("Creating movie with title: {}", request.getTitle());
        
        if (request.getImdbId() != null && movieRepository.existsByImdbId(request.getImdbId())) {
            throw new DuplicateResourceException("Movie", "imdbId", request.getImdbId());
        }
        
        Movie movie = movieMapper.toEntity(request);
        Movie savedMovie = movieRepository.save(movie);
        
        log.info("Created movie with ID: {} and title: {}", savedMovie.getId(), savedMovie.getTitle());
        return movieMapper.toResponse(savedMovie);
    }
    
    @Transactional(readOnly = true)
    public MovieResponse getMovieById(Long id) {
        log.debug("Fetching movie with ID: {}", id);
        
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        
        return movieMapper.toResponse(movie);
    }
    
    @Transactional(readOnly = true)
    public MovieResponse getMovieByImdbId(String imdbId) {
        log.debug("Fetching movie with IMDB ID: {}", imdbId);
        
        Movie movie = movieRepository.findByImdbId(imdbId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "imdbId", imdbId));
        
        return movieMapper.toResponse(movie);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getAllMovies(Pageable pageable) {
        log.debug("Fetching all active movies with pagination: {}", pageable);
        
        Page<Movie> moviePage = movieRepository.findByIsActiveTrue(pageable);
        return buildPageResponse(moviePage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> searchMovies(String search, Pageable pageable) {
        log.debug("Searching movies with term: {} and pagination: {}", search, pageable);
        
        Page<Movie> moviePage = movieRepository.findActiveMoviesBySearch(search, pageable);
        return buildPageResponse(moviePage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getMoviesByGenre(String genre, Pageable pageable) {
        log.debug("Fetching movies by genre: {} with pagination: {}", genre, pageable);
        
        Page<Movie> moviePage = movieRepository.findActiveMoviesByGenre(genre, pageable);
        return buildPageResponse(moviePage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getMoviesByDirector(String director, Pageable pageable) {
        log.debug("Fetching movies by director: {} with pagination: {}", director, pageable);
        
        Page<Movie> moviePage = movieRepository.findActiveMoviesByDirector(director, pageable);
        return buildPageResponse(moviePage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getMoviesByActor(String actor, Pageable pageable) {
        log.debug("Fetching movies by actor: {} with pagination: {}", actor, pageable);
        
        Page<Movie> moviePage = movieRepository.findActiveMoviesByActor(actor, pageable);
        return buildPageResponse(moviePage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getMoviesByReleaseDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        log.debug("Fetching movies between {} and {} with pagination: {}", startDate, endDate, pageable);
        
        Page<Movie> moviePage = movieRepository.findActiveMoviesByReleaseDateBetween(startDate, endDate, pageable);
        return buildPageResponse(moviePage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getTopRatedMovies(Pageable pageable) {
        log.debug("Fetching top rated movies with pagination: {}", pageable);
        
        Page<Movie> moviePage = movieRepository.findTopRatedActiveMovies(pageable);
        return buildPageResponse(moviePage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getRecentlyAddedMovies(Pageable pageable) {
        log.debug("Fetching recently added movies with pagination: {}", pageable);
        
        Page<Movie> moviePage = movieRepository.findRecentlyAddedActiveMovies(pageable);
        return buildPageResponse(moviePage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getMostRatedMovies(Pageable pageable) {
        log.debug("Fetching most rated movies with pagination: {}", pageable);
        
        Page<Movie> moviePage = movieRepository.findMostRatedActiveMovies(pageable);
        return buildPageResponse(moviePage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getMoviesByMinRating(Double minRating, Pageable pageable) {
        log.debug("Fetching movies with minimum rating: {} and pagination: {}", minRating, pageable);
        
        Page<Movie> moviePage = movieRepository.findActiveMoviesByMinRating(minRating, pageable);
        return buildPageResponse(moviePage);
    }
    
    @Transactional(readOnly = true)
    public List<String> getAllGenres() {
        log.debug("Fetching all active genres");
        return movieRepository.findAllActiveGenres();
    }
    
    @Transactional(readOnly = true)
    public List<String> getAllDirectors() {
        log.debug("Fetching all active directors");
        return movieRepository.findAllActiveDirectors();
    }
    
    @Transactional(readOnly = true)
    public List<String> getAllActors() {
        log.debug("Fetching all active actors");
        return movieRepository.findAllActiveActors();
    }
    
    public MovieResponse updateMovie(Long id, MovieUpdateRequest request) {
        log.debug("Updating movie with ID: {}", id);
        
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        
        movieMapper.updateEntity(movie, request);
        Movie savedMovie = movieRepository.save(movie);
        
        log.info("Updated movie with ID: {}", savedMovie.getId());
        return movieMapper.toResponse(savedMovie);
    }
    
    public void deleteMovie(Long id) {
        log.debug("Deleting movie with ID: {}", id);
        
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        
        movie.setIsActive(false);
        movieRepository.save(movie);
        
        log.info("Soft deleted movie with ID: {}", id);
    }
    
    @Transactional(readOnly = true)
    public Movie findEntityById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
    }
    
    private PageResponse<MovieResponse> buildPageResponse(Page<Movie> moviePage) {
        return PageResponse.<MovieResponse>builder()
                .content(moviePage.getContent().stream()
                        .map(movieMapper::toResponse)
                        .toList())
                .page(moviePage.getNumber())
                .size(moviePage.getSize())
                .totalElements(moviePage.getTotalElements())
                .totalPages(moviePage.getTotalPages())
                .first(moviePage.isFirst())
                .last(moviePage.isLast())
                .hasNext(moviePage.hasNext())
                .hasPrevious(moviePage.hasPrevious())
                .build();
    }
}