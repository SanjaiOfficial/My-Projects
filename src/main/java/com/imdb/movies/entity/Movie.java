package com.imdb.movies.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends BaseEntity {
    
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    @Column(nullable = false)
    private String title;
    
    @Column(name = "original_title")
    private String originalTitle;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "release_date")
    private LocalDate releaseDate;
    
    @Min(value = 1, message = "Runtime must be at least 1 minute")
    @Column(name = "runtime_minutes")
    private Integer runtimeMinutes;
    
    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre")
    @Builder.Default
    private List<String> genres = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "movie_directors", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "director")
    @Builder.Default
    private List<String> directors = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "movie_actors", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "actor")
    @Builder.Default
    private List<String> actors = new ArrayList<>();
    
    @Column(name = "poster_url")
    private String posterUrl;
    
    @Column(name = "imdb_id", unique = true)
    private String imdbId;
    
    @Column(name = "tmdb_id")
    private String tmdbId;
    
    @DecimalMin(value = "0.0", message = "Average rating cannot be negative")
    @DecimalMax(value = "10.0", message = "Average rating cannot exceed 10.0")
    @Column(name = "average_rating", precision = 3, scale = 1)
    private Double averageRating;
    
    @Min(value = 0, message = "Rating count cannot be negative")
    @Column(name = "rating_count")
    @Builder.Default
    private Long ratingCount = 0L;
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Rating> ratings = new ArrayList<>();
    
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
    
    // Helper method to update average rating
    public void updateAverageRating() {
        if (ratings.isEmpty()) {
            this.averageRating = null;
            this.ratingCount = 0L;
        } else {
            this.averageRating = ratings.stream()
                    .mapToDouble(Rating::getRating)
                    .average()
                    .orElse(0.0);
            this.ratingCount = (long) ratings.size();
        }
    }
}