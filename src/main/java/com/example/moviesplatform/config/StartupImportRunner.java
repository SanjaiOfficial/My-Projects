package com.example.moviesplatform.config;

import com.example.moviesplatform.service.CsvImportService;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupImportRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupImportRunner.class);

    @Bean
    public CommandLineRunner autoImport(@Value("${app.import.auto:false}") boolean auto,
                                        @Value("${app.csv.movie-file:}") String movieCsv,
                                        @Value("${app.csv.rating-file:}") String ratingCsv,
                                        CsvImportService importService) {
        return args -> {
            if (!auto) return;
            try {
                int movies = importService.importMoviesCsv(Path.of(movieCsv));
                if (movies > 0) {
                    log.info("Auto-imported {} movies from {}", movies, movieCsv);
                }
                int ratings = importService.importRatingsCsv(Path.of(ratingCsv));
                if (ratings > 0) {
                    log.info("Auto-imported {} ratings from {}", ratings, ratingCsv);
                }
            } catch (Exception e) {
                log.warn("Failed auto-import: {}", e.getMessage());
            }
        };
    }
}

