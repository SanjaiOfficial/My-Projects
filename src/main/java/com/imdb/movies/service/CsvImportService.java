package com.imdb.movies.service;

import com.imdb.movies.entity.Movie;
import com.imdb.movies.entity.Rating;
import com.imdb.movies.entity.User;
import com.imdb.movies.repository.MovieRepository;
import com.imdb.movies.repository.RatingRepository;
import com.imdb.movies.repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvImportService {
    
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ResourceLoader resourceLoader;
    
    @Value("${app.csv.import.batch-size:1000}")
    private int batchSize;
    
    @Transactional
    public ImportResult importMoviesFromCsv(MultipartFile file) throws IOException, CsvException {
        log.info("Starting movie import from CSV file: {}", file.getOriginalFilename());
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> records = reader.readAll();
            
            if (records.isEmpty()) {
                return new ImportResult(0, 0, "CSV file is empty");
            }
            
            // Skip header row
            List<String[]> dataRows = records.subList(1, records.size());
            return processMovieRecords(dataRows);
        }
    }
    
    @Transactional
    public ImportResult importRatingsFromCsv(MultipartFile file) throws IOException, CsvException {
        log.info("Starting ratings import from CSV file: {}", file.getOriginalFilename());
        
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> records = reader.readAll();
            
            if (records.isEmpty()) {
                return new ImportResult(0, 0, "CSV file is empty");
            }
            
            // Skip header row
            List<String[]> dataRows = records.subList(1, records.size());
            return processRatingRecords(dataRows);
        }
    }
    
    @Transactional
    public ImportResult importMoviesFromResource() {
        log.info("Starting movie import from classpath resource");
        
        try {
            Resource resource = resourceLoader.getResource("classpath:data/movies.csv");
            if (!resource.exists()) {
                return new ImportResult(0, 0, "Movies CSV file not found in classpath");
            }
            
            try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
                List<String[]> records = reader.readAll();
                
                if (records.isEmpty()) {
                    return new ImportResult(0, 0, "CSV file is empty");
                }
                
                // Skip header row
                List<String[]> dataRows = records.subList(1, records.size());
                return processMovieRecords(dataRows);
            }
        } catch (IOException | CsvException e) {
            log.error("Error importing movies from resource", e);
            return new ImportResult(0, 0, "Error reading movies CSV: " + e.getMessage());
        }
    }
    
    @Transactional
    public ImportResult importRatingsFromResource() {
        log.info("Starting ratings import from classpath resource");
        
        try {
            Resource resource = resourceLoader.getResource("classpath:data/ratings.csv");
            if (!resource.exists()) {
                return new ImportResult(0, 0, "Ratings CSV file not found in classpath");
            }
            
            try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
                List<String[]> records = reader.readAll();
                
                if (records.isEmpty()) {
                    return new ImportResult(0, 0, "CSV file is empty");
                }
                
                // Skip header row
                List<String[]> dataRows = records.subList(1, records.size());
                return processRatingRecords(dataRows);
            }
        } catch (IOException | CsvException e) {
            log.error("Error importing ratings from resource", e);
            return new ImportResult(0, 0, "Error reading ratings CSV: " + e.getMessage());
        }
    }
    
    private ImportResult processMovieRecords(List<String[]> records) {
        int successCount = 0;
        int errorCount = 0;
        List<String> errors = new ArrayList<>();
        
        List<Movie> moviesBuffer = new ArrayList<>();
        
        for (int i = 0; i < records.size(); i++) {
            String[] record = records.get(i);
            
            try {
                Movie movie = parseMovieRecord(record);
                if (movie != null) {
                    moviesBuffer.add(movie);
                    successCount++;
                } else {
                    errorCount++;
                    errors.add("Row " + (i + 2) + ": Invalid movie data");
                }
                
                // Process in batches
                if (moviesBuffer.size() >= batchSize || i == records.size() - 1) {
                    movieRepository.saveAll(moviesBuffer);
                    log.debug("Saved batch of {} movies", moviesBuffer.size());
                    moviesBuffer.clear();
                }
                
            } catch (Exception e) {
                errorCount++;
                errors.add("Row " + (i + 2) + ": " + e.getMessage());
                log.warn("Error processing movie record at row {}: {}", i + 2, e.getMessage());
            }
        }
        
        log.info("Movie import completed. Success: {}, Errors: {}", successCount, errorCount);
        return new ImportResult(successCount, errorCount, 
                errors.isEmpty() ? "Import completed successfully" : 
                String.join("; ", errors.subList(0, Math.min(errors.size(), 10))));
    }
    
    private ImportResult processRatingRecords(List<String[]> records) {
        int successCount = 0;
        int errorCount = 0;
        List<String> errors = new ArrayList<>();
        
        List<Rating> ratingsBuffer = new ArrayList<>();
        
        // Create a default user for imported ratings if needed
        User defaultUser = getOrCreateDefaultUser();
        
        for (int i = 0; i < records.size(); i++) {
            String[] record = records.get(i);
            
            try {
                Rating rating = parseRatingRecord(record, defaultUser);
                if (rating != null) {
                    ratingsBuffer.add(rating);
                    successCount++;
                } else {
                    errorCount++;
                    errors.add("Row " + (i + 2) + ": Invalid rating data");
                }
                
                // Process in batches
                if (ratingsBuffer.size() >= batchSize || i == records.size() - 1) {
                    ratingRepository.saveAll(ratingsBuffer);
                    log.debug("Saved batch of {} ratings", ratingsBuffer.size());
                    ratingsBuffer.clear();
                }
                
            } catch (Exception e) {
                errorCount++;
                errors.add("Row " + (i + 2) + ": " + e.getMessage());
                log.warn("Error processing rating record at row {}: {}", i + 2, e.getMessage());
            }
        }
        
        // Update movie average ratings
        updateAllMovieRatings();
        
        log.info("Rating import completed. Success: {}, Errors: {}", successCount, errorCount);
        return new ImportResult(successCount, errorCount, 
                errors.isEmpty() ? "Import completed successfully" : 
                String.join("; ", errors.subList(0, Math.min(errors.size(), 10))));
    }
    
    private Movie parseMovieRecord(String[] record) {
        if (record.length < 3) {
            return null;
        }
        
        try {
            Movie movie = Movie.builder()
                    .title(getStringValue(record, 0))
                    .originalTitle(getStringValue(record, 1))
                    .description(getStringValue(record, 2))
                    .releaseDate(parseDate(getStringValue(record, 3)))
                    .runtimeMinutes(parseInteger(getStringValue(record, 4)))
                    .genres(parseList(getStringValue(record, 5)))
                    .directors(parseList(getStringValue(record, 6)))
                    .actors(parseList(getStringValue(record, 7)))
                    .posterUrl(getStringValue(record, 8))
                    .imdbId(getStringValue(record, 9))
                    .tmdbId(getStringValue(record, 10))
                    .isActive(true)
                    .ratingCount(0L)
                    .build();
            
            // Check for duplicate IMDB ID
            if (movie.getImdbId() != null && movieRepository.existsByImdbId(movie.getImdbId())) {
                log.warn("Movie with IMDB ID {} already exists, skipping", movie.getImdbId());
                return null;
            }
            
            return movie;
        } catch (Exception e) {
            log.warn("Error parsing movie record: {}", Arrays.toString(record), e);
            return null;
        }
    }
    
    private Rating parseRatingRecord(String[] record, User defaultUser) {
        if (record.length < 3) {
            return null;
        }
        
        try {
            String imdbId = getStringValue(record, 0);
            Double ratingValue = parseDouble(getStringValue(record, 1));
            
            if (imdbId == null || ratingValue == null) {
                return null;
            }
            
            // Find movie by IMDB ID
            Optional<Movie> movieOpt = movieRepository.findByImdbId(imdbId);
            if (movieOpt.isEmpty()) {
                log.warn("Movie with IMDB ID {} not found for rating", imdbId);
                return null;
            }
            
            Movie movie = movieOpt.get();
            
            // Check if rating already exists for this user and movie
            if (ratingRepository.existsByUserIdAndMovieId(defaultUser.getId(), movie.getId())) {
                return null; // Skip duplicate ratings
            }
            
            return Rating.builder()
                    .rating(ratingValue)
                    .user(defaultUser)
                    .movie(movie)
                    .isActive(true)
                    .build();
                    
        } catch (Exception e) {
            log.warn("Error parsing rating record: {}", Arrays.toString(record), e);
            return null;
        }
    }
    
    private User getOrCreateDefaultUser() {
        return userRepository.findByUsername("csv_import_user")
                .orElseGet(() -> {
                    User user = User.builder()
                            .username("csv_import_user")
                            .email("csv_import@movies.com")
                            .firstName("CSV")
                            .lastName("Import")
                            .isActive(true)
                            .build();
                    return userRepository.save(user);
                });
    }
    
    private void updateAllMovieRatings() {
        log.info("Updating average ratings for all movies");
        List<Movie> movies = movieRepository.findAll();
        
        for (Movie movie : movies) {
            List<Rating> activeRatings = ratingRepository.findByMovieIdAndIsActiveTrue(movie.getId());
            
            if (activeRatings.isEmpty()) {
                movie.setAverageRating(null);
                movie.setRatingCount(0L);
            } else {
                double average = activeRatings.stream()
                        .mapToDouble(Rating::getRating)
                        .average()
                        .orElse(0.0);
                movie.setAverageRating(Math.round(average * 10.0) / 10.0);
                movie.setRatingCount((long) activeRatings.size());
            }
        }
        
        movieRepository.saveAll(movies);
        log.info("Updated average ratings for {} movies", movies.size());
    }
    
    private String getStringValue(String[] record, int index) {
        if (index >= record.length) {
            return null;
        }
        String value = record[index].trim();
        return value.isEmpty() || "NULL".equalsIgnoreCase(value) ? null : value;
    }
    
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        
        try {
            // Try different date formats
            DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("yyyy")
            };
            
            for (DateTimeFormatter formatter : formatters) {
                try {
                    if (formatter.toString().contains("yyyy") && !formatter.toString().contains("MM")) {
                        // For year-only format
                        return LocalDate.of(Integer.parseInt(dateStr), 1, 1);
                    }
                    return LocalDate.parse(dateStr, formatter);
                } catch (DateTimeParseException ignored) {
                    // Try next format
                }
            }
            
            return null;
        } catch (Exception e) {
            log.warn("Could not parse date: {}", dateStr);
            return null;
        }
    }
    
    private Integer parseInteger(String intStr) {
        if (intStr == null || intStr.isEmpty()) {
            return null;
        }
        
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            log.warn("Could not parse integer: {}", intStr);
            return null;
        }
    }
    
    private Double parseDouble(String doubleStr) {
        if (doubleStr == null || doubleStr.isEmpty()) {
            return null;
        }
        
        try {
            return Double.parseDouble(doubleStr);
        } catch (NumberFormatException e) {
            log.warn("Could not parse double: {}", doubleStr);
            return null;
        }
    }
    
    private List<String> parseList(String listStr) {
        if (listStr == null || listStr.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Split by comma, semicolon, or pipe and clean up
        return Arrays.stream(listStr.split("[,;|]"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
    
    public static class ImportResult {
        public final int successCount;
        public final int errorCount;
        public final String message;
        
        public ImportResult(int successCount, int errorCount, String message) {
            this.successCount = successCount;
            this.errorCount = errorCount;
            this.message = message;
        }
        
        @Override
        public String toString() {
            return String.format("ImportResult{success=%d, errors=%d, message='%s'}", 
                    successCount, errorCount, message);
        }
    }
}