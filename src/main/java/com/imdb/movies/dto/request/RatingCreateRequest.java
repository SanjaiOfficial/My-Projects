package com.imdb.movies.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingCreateRequest {
    
    @NotNull(message = "Rating value is required")
    @DecimalMin(value = "0.5", message = "Rating must be at least 0.5")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10.0")
    private Double rating;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Movie ID is required")
    private Long movieId;
}