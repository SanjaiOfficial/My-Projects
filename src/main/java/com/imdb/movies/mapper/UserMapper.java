package com.imdb.movies.mapper;

import com.imdb.movies.dto.request.UserCreateRequest;
import com.imdb.movies.dto.request.UserUpdateRequest;
import com.imdb.movies.dto.response.UserResponse;
import com.imdb.movies.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    User toEntity(UserCreateRequest request);
    
    @Mapping(target = "ratingsCount", source = "ratings", qualifiedByName = "countActiveRatings")
    @Mapping(target = "reviewsCount", source = "reviews", qualifiedByName = "countActiveReviews")
    UserResponse toResponse(User user);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateEntity(@MappingTarget User user, UserUpdateRequest request);
    
    @Named("countActiveRatings")
    default Long countActiveRatings(java.util.List<com.imdb.movies.entity.Rating> ratings) {
        return ratings == null ? 0L : ratings.stream()
                .filter(rating -> Boolean.TRUE.equals(rating.getIsActive()))
                .count();
    }
    
    @Named("countActiveReviews")
    default Long countActiveReviews(java.util.List<com.imdb.movies.entity.Review> reviews) {
        return reviews == null ? 0L : reviews.stream()
                .filter(review -> Boolean.TRUE.equals(review.getIsActive()))
                .count();
    }
}