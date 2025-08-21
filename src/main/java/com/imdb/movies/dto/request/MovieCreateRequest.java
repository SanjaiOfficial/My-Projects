package com.imdb.movies.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCreateRequest {
    
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;
    
    private String originalTitle;
    
    private String description;
    
    private LocalDate releaseDate;
    
    @Min(value = 1, message = "Runtime must be at least 1 minute")
    private Integer runtimeMinutes;
    
    private List<String> genres;
    
    private List<String> directors;
    
    private List<String> actors;
    
    private String posterUrl;
    
    private String imdbId;
    
    private String tmdbId;
}