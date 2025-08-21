package com.example.moviesplatform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RatingRequest {
    @NotBlank
    public String userId;

    @Min(1)
    @Max(10)
    public Integer score;
}

