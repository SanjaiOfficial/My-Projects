package com.imdb.movies.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "reviews",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "movie_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {
    
    @NotBlank(message = "Review title is required")
    @Size(max = 255, message = "Review title cannot exceed 255 characters")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Review content is required")
    @Size(min = 10, max = 5000, message = "Review content must be between 10 and 5000 characters")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    
    @Column(name = "is_spoiler")
    @Builder.Default
    private Boolean isSpoiler = false;
    
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @Column(name = "helpful_count")
    @Builder.Default
    private Long helpfulCount = 0L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
}