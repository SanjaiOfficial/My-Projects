package com.example.moviesplatform.controller;

import com.example.moviesplatform.service.CsvImportService;
import java.io.IOException;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    private final CsvImportService importService;

    @Value("${app.csv.movie-file:}")
    private String movieCsvPath;
    @Value("${app.csv.rating-file:}")
    private String ratingCsvPath;

    public ImportController(CsvImportService importService) {
        this.importService = importService;
    }

    @PostMapping("/movies")
    public ResponseEntity<String> importMovies() throws IOException {
        int count = importService.importMoviesCsv(Path.of(movieCsvPath));
        return ResponseEntity.ok("Imported movies: " + count);
    }

    @PostMapping("/ratings")
    public ResponseEntity<String> importRatings() throws IOException {
        int count = importService.importRatingsCsv(Path.of(ratingCsvPath));
        return ResponseEntity.ok("Imported ratings: " + count);
    }
}

