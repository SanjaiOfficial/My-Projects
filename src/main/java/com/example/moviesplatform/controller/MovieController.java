package com.example.moviesplatform.controller;

import com.example.moviesplatform.domain.Movie;
import com.example.moviesplatform.domain.Review;
import com.example.moviesplatform.dto.MovieCreateRequest;
import com.example.moviesplatform.dto.MovieDTO;
import com.example.moviesplatform.dto.ReviewDTO;
import com.example.moviesplatform.dto.ReviewRequest;
import com.example.moviesplatform.mapper.MovieMapper;
import com.example.moviesplatform.service.MovieService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieDTO> create(@Valid @RequestBody MovieCreateRequest request) {
        Movie saved = movieService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MovieMapper.toDto(saved));
    }

    @GetMapping
    public Page<MovieDTO> search(@RequestParam(required = false) String q,
                                 @RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size) {
        return movieService.search(q, page, size).map(MovieMapper::toDto);
    }

    @GetMapping("/{id}")
    public MovieDTO get(@PathVariable Long id) {
        return MovieMapper.toDto(movieService.getById(id));
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable Long id, @Valid @RequestBody ReviewRequest request) {
        Review review = movieService.addReview(id, request);
        ReviewDTO dto = new ReviewDTO();
        dto.id = review.getId();
        dto.author = review.getAuthor();
        dto.title = review.getTitle();
        dto.content = review.getContent();
        dto.createdAt = review.getCreatedAt();
        dto.updatedAt = review.getUpdatedAt();
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}/reviews")
    public List<ReviewDTO> listReviews(@PathVariable Long id) {
        return movieService.getReviews(id).stream().map(r -> {
            ReviewDTO dto = new ReviewDTO();
            dto.id = r.getId();
            dto.author = r.getAuthor();
            dto.title = r.getTitle();
            dto.content = r.getContent();
            dto.createdAt = r.getCreatedAt();
            dto.updatedAt = r.getUpdatedAt();
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/top/year")
    public Page<MovieDTO> topByYear(@RequestParam(required = false) Integer year,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer size) {
        return movieService.topByYear(year, page, size).map(MovieMapper::toDto);
    }

    @GetMapping("/top/genres")
    public Page<MovieDTO> topByGenres(@RequestParam Set<String> genres,
                                      @RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer size) {
        return movieService.topByGenres(genres, page, size).map(MovieMapper::toDto);
    }
}

