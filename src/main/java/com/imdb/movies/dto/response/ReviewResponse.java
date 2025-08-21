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
public class ReviewResponse {
    private Long id;
    private String title;
    private String content;
    private Boolean isSpoiler;
    private Boolean isActive;
    private Long helpfulCount;
    private UserResponse user;
    private MovieResponse movie;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}