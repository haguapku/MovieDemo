package com.example.moviedemo.api;

import com.example.moviedemo.data.model.Movie;

import java.util.List;

/**
 * Author: created by MarkYoung on 18/01/2019 10:50
 */
public class MovieLoadResponse {

    public int page;
    public int total_pages;
    public List<Movie> results;

    public MovieLoadResponse(int page, int total_pages, List<Movie> results) {
        this.page = page;
        this.total_pages = total_pages;
        this.results = results;
    }
}
