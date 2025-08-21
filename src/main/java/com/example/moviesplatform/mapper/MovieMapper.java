package com.example.moviesplatform.mapper;

import com.example.moviesplatform.domain.Genre;
import com.example.moviesplatform.domain.Movie;
import com.example.moviesplatform.dto.MovieCreateRequest;
import com.example.moviesplatform.dto.MovieDTO;
import java.util.Set;
import java.util.stream.Collectors;

public final class MovieMapper {

    private MovieMapper() {}

    public static MovieDTO toDto(Movie entity) {
        MovieDTO dto = new MovieDTO();
        dto.id = entity.getId();
        dto.imdbId = entity.getImdbId();
        dto.title = entity.getTitle();
        dto.year = entity.getYear();
        dto.runtimeMinutes = entity.getRuntimeMinutes();
        dto.genres = entity.getGenres().stream().map(Enum::name).collect(Collectors.toSet());
        dto.averageRating = entity.getAverageRating();
        dto.numVotes = entity.getNumVotes();
        return dto;
    }

    public static Movie fromCreate(MovieCreateRequest req) {
        Set<Genre> genres = req.genres == null ? Set.of() : req.genres.stream().map(Genre::fromStringSafe).collect(Collectors.toSet());
        Movie m = new Movie(req.imdbId, req.title, req.year, req.runtimeMinutes, genres);
        return m;
    }
}

