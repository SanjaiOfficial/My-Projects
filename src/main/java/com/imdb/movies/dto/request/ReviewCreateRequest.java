package com.imdb.movies.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {
    
    @NotBlank(message = "Review title is required")
    @Size(max = 255, message = "Review title cannot exceed 255 characters")
    private String title;
    
    @NotBlank(message = "Review content is required")
    @Size(min = 10, max = 5000, message = "Review content must be between 10 and 5000 characters")
    private String content;
    
    private Boolean isSpoiler = false;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Movie ID is required")
    private Long movieId;
}