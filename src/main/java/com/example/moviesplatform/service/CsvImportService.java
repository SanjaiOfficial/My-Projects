package com.example.moviesplatform.service;

import com.example.moviesplatform.domain.Genre;
import com.example.moviesplatform.domain.Movie;
import com.example.moviesplatform.repository.MovieRepository;
import com.example.moviesplatform.repository.RatingRepository;
import com.example.moviesplatform.domain.Rating;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CsvImportService {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    @Value("${app.csv.movie-file:}")
    private String movieCsvPath;

    @Value("${app.csv.rating-file:}")
    private String ratingCsvPath;

    public CsvImportService(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public int importMoviesCsv(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            return 0;
        }
        try (Reader reader = new FileReader(filePath.toFile());
             CSVParser parser = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setTrim(true)
                     .build()
                     .parse(reader)) {
            int count = 0;
            for (CSVRecord record : parser) {
                String imdbId = record.get("imdb_id");
                String title = record.get("title");
                Integer year = parseInteger(record.get("year"));
                Integer runtime = parseInteger(record.get("runtime_minutes"));
                Set<Genre> genres = parseGenres(record.get("genres"));

                Movie movie = movieRepository.findByImdbId(imdbId).orElse(new Movie());
                movie.setImdbId(imdbId);
                movie.setTitle(title);
                movie.setYear(year);
                movie.setRuntimeMinutes(runtime);
                movie.setGenres(genres);
                movieRepository.save(movie);
                count++;
            }
            return count;
        }
    }

    private Integer parseInteger(String value) {
        try {
            if (value == null || value.isBlank() || value.equals("\\N")) return null;
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Set<Genre> parseGenres(String value) {
        if (value == null || value.isBlank() || value.equals("\\N")) return new HashSet<>();
        String[] parts = value.split(",");
        return java.util.Arrays.stream(parts)
                .map(Genre::fromStringSafe)
                .collect(Collectors.toSet());
    }

    @Transactional
    public int importRatingsCsv(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            return 0;
        }
        try (Reader reader = new FileReader(filePath.toFile());
             CSVParser parser = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setTrim(true)
                     .build()
                     .parse(reader)) {
            int count = 0;
            Map<String, Movie> imdbToMovieCache = new HashMap<>();
            for (CSVRecord record : parser) {
                String imdbId = record.get("imdb_id");
                String userId = record.get("user_id");
                Integer score = parseInteger(record.get("score"));
                if (score == null) continue;

                Movie movie = imdbToMovieCache.computeIfAbsent(imdbId,
                        id -> movieRepository.findByImdbId(id).orElse(null));
                if (movie == null) continue; // skip ratings without known movie

                if (ratingRepository.findByMovieAndUserId(movie, userId).isPresent()) {
                    continue; // skip duplicates
                }
                Rating rating = new Rating(movie, userId, score);
                ratingRepository.save(rating);
                movie.applyNewRating(score);
                movieRepository.save(movie);
                count++;
            }
            return count;
        }
    }
}

