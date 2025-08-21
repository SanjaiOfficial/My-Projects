package com.imdb.movies.service;

import com.imdb.movies.dto.request.UserCreateRequest;
import com.imdb.movies.dto.request.UserUpdateRequest;
import com.imdb.movies.dto.response.PageResponse;
import com.imdb.movies.dto.response.UserResponse;
import com.imdb.movies.entity.User;
import com.imdb.movies.exception.DuplicateResourceException;
import com.imdb.movies.exception.ResourceNotFoundException;
import com.imdb.movies.mapper.UserMapper;
import com.imdb.movies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public UserResponse createUser(UserCreateRequest request) {
        log.debug("Creating user with username: {}", request.getUsername());
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("User", "username", request.getUsername());
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User", "email", request.getEmail());
        }
        
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        
        log.info("Created user with ID: {} and username: {}", savedUser.getId(), savedUser.getUsername());
        return userMapper.toResponse(savedUser);
    }
    
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        log.debug("Fetching user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        return userMapper.toResponse(user);
    }
    
    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        log.debug("Fetching user with username: {}", username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        return userMapper.toResponse(user);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getAllUsers(Pageable pageable) {
        log.debug("Fetching all active users with pagination: {}", pageable);
        
        Page<User> userPage = userRepository.findByIsActiveTrue(pageable);
        return buildPageResponse(userPage);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> searchUsers(String search, Pageable pageable) {
        log.debug("Searching users with term: {} and pagination: {}", search, pageable);
        
        Page<User> userPage = userRepository.findActiveUsersBySearch(search, pageable);
        return buildPageResponse(userPage);
    }
    
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        log.debug("Updating user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        // Check for username uniqueness if it's being updated
        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new DuplicateResourceException("User", "username", request.getUsername());
            }
        }
        
        // Check for email uniqueness if it's being updated
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("User", "email", request.getEmail());
            }
        }
        
        userMapper.updateEntity(user, request);
        User savedUser = userRepository.save(user);
        
        log.info("Updated user with ID: {}", savedUser.getId());
        return userMapper.toResponse(savedUser);
    }
    
    public void deleteUser(Long id) {
        log.debug("Deleting user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        user.setIsActive(false);
        userRepository.save(user);
        
        log.info("Soft deleted user with ID: {}", id);
    }
    
    @Transactional(readOnly = true)
    public User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
    
    private PageResponse<UserResponse> buildPageResponse(Page<User> userPage) {
        return PageResponse.<UserResponse>builder()
                .content(userPage.getContent().stream()
                        .map(userMapper::toResponse)
                        .toList())
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .first(userPage.isFirst())
                .last(userPage.isLast())
                .hasNext(userPage.hasNext())
                .hasPrevious(userPage.hasPrevious())
                .build();
    }
}