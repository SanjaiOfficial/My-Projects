package com.example.moviesplatform.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import java.time.Instant;

@Entity
@Table(name = "ratings", indexes = {
        @Index(name = "idx_ratings_movie_id", columnList = "movie_id"),
        @Index(name = "idx_ratings_user_id", columnList = "user_id")
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "user_id", length = 128)
    private String userId;

    @Column(nullable = false)
    private Integer score; // 1-10

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public Rating() {
    }

    public Rating(Movie movie, String userId, Integer score) {
        this.movie = movie;
        this.userId = userId;
        this.score = score;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

