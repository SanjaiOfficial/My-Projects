package com.imdb.movies.controller;

import com.imdb.movies.dto.response.ApiResponse;
import com.imdb.movies.service.CsvImportService;
import com.opencsv.exceptions.CsvException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/import")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "CSV Import", description = "APIs for importing data from CSV files")
public class CsvImportController {
    
    private final CsvImportService csvImportService;
    
    @PostMapping("/movies/file")
    @Operation(summary = "Import movies from CSV file", description = "Imports movie data from uploaded CSV file")
    public ResponseEntity<ApiResponse<String>> importMoviesFromFile(
            @RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("File is empty"));
        }
        
        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("File must be a CSV file"));
        }
        
        try {
            log.info("Starting movie import from uploaded file: {}", file.getOriginalFilename());
            CsvImportService.ImportResult result = csvImportService.importMoviesFromCsv(file);
            
            String message = String.format("Import completed. Success: %d, Errors: %d. %s", 
                    result.successCount, result.errorCount, result.message);
            
            ApiResponse<String> response = ApiResponse.success("Movies imported successfully", message);
            return ResponseEntity.ok(response);
            
        } catch (IOException | CsvException e) {
            log.error("Error importing movies from CSV file", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error processing CSV file: " + e.getMessage()));
        }
    }
    
    @PostMapping("/ratings/file")
    @Operation(summary = "Import ratings from CSV file", description = "Imports rating data from uploaded CSV file")
    public ResponseEntity<ApiResponse<String>> importRatingsFromFile(
            @RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("File is empty"));
        }
        
        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("File must be a CSV file"));
        }
        
        try {
            log.info("Starting rating import from uploaded file: {}", file.getOriginalFilename());
            CsvImportService.ImportResult result = csvImportService.importRatingsFromCsv(file);
            
            String message = String.format("Import completed. Success: %d, Errors: %d. %s", 
                    result.successCount, result.errorCount, result.message);
            
            ApiResponse<String> response = ApiResponse.success("Ratings imported successfully", message);
            return ResponseEntity.ok(response);
            
        } catch (IOException | CsvException e) {
            log.error("Error importing ratings from CSV file", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error processing CSV file: " + e.getMessage()));
        }
    }
    
    @PostMapping("/movies/resource")
    @Operation(summary = "Import movies from classpath", description = "Imports movie data from classpath CSV resource")
    public ResponseEntity<ApiResponse<String>> importMoviesFromResource() {
        
        try {
            log.info("Starting movie import from classpath resource");
            CsvImportService.ImportResult result = csvImportService.importMoviesFromResource();
            
            String message = String.format("Import completed. Success: %d, Errors: %d. %s", 
                    result.successCount, result.errorCount, result.message);
            
            ApiResponse<String> response = ApiResponse.success("Movies imported successfully", message);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error importing movies from resource", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error processing CSV resource: " + e.getMessage()));
        }
    }
    
    @PostMapping("/ratings/resource")
    @Operation(summary = "Import ratings from classpath", description = "Imports rating data from classpath CSV resource")
    public ResponseEntity<ApiResponse<String>> importRatingsFromResource() {
        
        try {
            log.info("Starting rating import from classpath resource");
            CsvImportService.ImportResult result = csvImportService.importRatingsFromResource();
            
            String message = String.format("Import completed. Success: %d, Errors: %d. %s", 
                    result.successCount, result.errorCount, result.message);
            
            ApiResponse<String> response = ApiResponse.success("Ratings imported successfully", message);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error importing ratings from resource", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error processing CSV resource: " + e.getMessage()));
        }
    }
    
    @PostMapping("/all")
    @Operation(summary = "Import all data", description = "Imports both movies and ratings from classpath resources")
    public ResponseEntity<ApiResponse<String>> importAllData() {
        
        try {
            log.info("Starting full data import from classpath resources");
            
            // Import movies first
            CsvImportService.ImportResult movieResult = csvImportService.importMoviesFromResource();
            log.info("Movie import result: {}", movieResult);
            
            // Then import ratings
            CsvImportService.ImportResult ratingResult = csvImportService.importRatingsFromResource();
            log.info("Rating import result: {}", ratingResult);
            
            String message = String.format(
                    "Full import completed. Movies - Success: %d, Errors: %d. Ratings - Success: %d, Errors: %d", 
                    movieResult.successCount, movieResult.errorCount,
                    ratingResult.successCount, ratingResult.errorCount);
            
            ApiResponse<String> response = ApiResponse.success("All data imported successfully", message);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error importing all data", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error processing data import: " + e.getMessage()));
        }
    }
}