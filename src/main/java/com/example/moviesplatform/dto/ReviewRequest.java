package com.example.moviesplatform.dto;

import jakarta.validation.constraints.NotBlank;

public class ReviewRequest {
    @NotBlank
    public String author;
    @NotBlank
    public String title;
    @NotBlank
    public String content;
}

