package com.imdb.movies.mapper;

import com.imdb.movies.dto.request.RatingCreateRequest;
import com.imdb.movies.dto.response.RatingResponse;
import com.imdb.movies.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, MovieMapper.class})
public interface RatingMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "movie", ignore = true)
    Rating toEntity(RatingCreateRequest request);
    
    RatingResponse toResponse(Rating rating);
}