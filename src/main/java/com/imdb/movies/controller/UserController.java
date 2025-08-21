package com.imdb.movies.controller;

import com.imdb.movies.dto.request.UserCreateRequest;
import com.imdb.movies.dto.request.UserUpdateRequest;
import com.imdb.movies.dto.response.ApiResponse;
import com.imdb.movies.dto.response.PageResponse;
import com.imdb.movies.dto.response.UserResponse;
import com.imdb.movies.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {
    
    private final UserService userService;
    
    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user account")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserCreateRequest request) {
        log.info("Creating user with username: {}", request.getUsername());
        
        UserResponse user = userService.createUser(request);
        ApiResponse<UserResponse> response = ApiResponse.success("User created successfully", user);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @Parameter(description = "User ID") @PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        
        UserResponse user = userService.getUserById(id);
        ApiResponse<UserResponse> response = ApiResponse.success(user);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username", description = "Retrieves a user by their username")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(
            @Parameter(description = "Username") @PathVariable String username) {
        log.info("Fetching user with username: {}", username);
        
        UserResponse user = userService.getUserByUsername(username);
        ApiResponse<UserResponse> response = ApiResponse.success(user);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all active users with pagination")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Fetching all users with pagination: page={}, size={}, sortBy={}, sortDir={}", 
                page, size, sortBy, sortDir);
        
        PageResponse<UserResponse> users = userService.getAllUsers(pageable);
        ApiResponse<PageResponse<UserResponse>> response = ApiResponse.success(users);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search users", description = "Searches users by username, first name, or last name")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> searchUsers(
            @Parameter(description = "Search term") @RequestParam String q,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        log.info("Searching users with term: {} and pagination: page={}, size={}, sortBy={}, sortDir={}", 
                q, page, size, sortBy, sortDir);
        
        PageResponse<UserResponse> users = userService.searchUsers(q, pageable);
        ApiResponse<PageResponse<UserResponse>> response = ApiResponse.success(users);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @Parameter(description = "User ID") @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        log.info("Updating user with ID: {}", id);
        
        UserResponse user = userService.updateUser(id, request);
        ApiResponse<UserResponse> response = ApiResponse.success("User updated successfully", user);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Soft deletes a user (marks as inactive)")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "User ID") @PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        
        userService.deleteUser(id);
        ApiResponse<Void> response = ApiResponse.success("User deleted successfully", null);
        
        return ResponseEntity.ok(response);
    }
}