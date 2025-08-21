package com.imdb.movies.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String originalTitle;
    private String description;
    private LocalDate releaseDate;
    private Integer runtimeMinutes;
    private List<String> genres;
    private List<String> directors;
    private List<String> actors;
    private String posterUrl;
    private String imdbId;
    private String tmdbId;
    private Double averageRating;
    private Long ratingCount;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}