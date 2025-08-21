package com.example.moviesplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public class MovieCreateRequest {
    @NotBlank
    public String title;
    public String imdbId;
    public Integer year;
    public Integer runtimeMinutes;
    @NotNull
    public Set<String> genres;
}

