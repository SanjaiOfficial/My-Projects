package com.example.moviesplatform.controller;

import com.example.moviesplatform.dto.MovieDTO;
import com.example.moviesplatform.mapper.MovieMapper;
import com.example.moviesplatform.service.MovieService;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopController {

    private final MovieService movieService;

    public TopController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/api/top/year")
    public Page<MovieDTO> topByYear(@RequestParam(required = false) Integer year,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer size) {
        return movieService.topByYear(year, page, size).map(MovieMapper::toDto);
    }

    @GetMapping("/api/top/genres")
    public Page<MovieDTO> topByGenres(@RequestParam Set<String> genres,
                                      @RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer size) {
        return movieService.topByGenres(genres, page, size).map(MovieMapper::toDto);
    }
}

