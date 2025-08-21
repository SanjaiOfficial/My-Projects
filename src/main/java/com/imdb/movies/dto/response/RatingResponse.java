package com.imdb.movies.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {
    private Long id;
    private Double rating;
    private Boolean isActive;
    private UserResponse user;
    private MovieResponse movie;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}