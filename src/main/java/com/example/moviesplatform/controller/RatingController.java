package com.example.moviesplatform.controller;

import com.example.moviesplatform.domain.Rating;
import com.example.moviesplatform.dto.RatingRequest;
import com.example.moviesplatform.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies/{movieId}/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Long> add(@PathVariable Long movieId, @Valid @RequestBody RatingRequest request) {
        Rating rating = ratingService.addRating(movieId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating.getId());
    }
}

