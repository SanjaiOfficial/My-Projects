package com.imdb.movies.mapper;

import com.imdb.movies.dto.request.MovieCreateRequest;
import com.imdb.movies.dto.request.MovieUpdateRequest;
import com.imdb.movies.dto.response.MovieResponse;
import com.imdb.movies.entity.Movie;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "ratingCount", constant = "0L")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Movie toEntity(MovieCreateRequest request);
    
    MovieResponse toResponse(Movie movie);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "averageRating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "imdbId", ignore = true) // Don't allow updating IMDB ID
    void updateEntity(@MappingTarget Movie movie, MovieUpdateRequest request);
}