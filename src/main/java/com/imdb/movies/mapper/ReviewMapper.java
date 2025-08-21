package com.imdb.movies.mapper;

import com.imdb.movies.dto.request.ReviewCreateRequest;
import com.imdb.movies.dto.request.ReviewUpdateRequest;
import com.imdb.movies.dto.response.ReviewResponse;
import com.imdb.movies.entity.Review;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UserMapper.class, MovieMapper.class})
public interface ReviewMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "helpfulCount", constant = "0L")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "movie", ignore = true)
    Review toEntity(ReviewCreateRequest request);
    
    ReviewResponse toResponse(Review review);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "helpfulCount", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "movie", ignore = true)
    void updateEntity(@MappingTarget Review review, ReviewUpdateRequest request);
}