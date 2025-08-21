package com.example.moviesplatform.dto;

import java.util.Set;

public class MovieDTO {
    public Long id;
    public String imdbId;
    public String title;
    public Integer year;
    public Integer runtimeMinutes;
    public Set<String> genres;
    public Double averageRating;
    public Long numVotes;
}

