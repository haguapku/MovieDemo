package com.example.moviedemo.api;

/**
 * Author: created by MarkYoung on 18/01/2019 11:25
 */
public class ApiResponse {

    public MovieLoadResponse movieLoadResponse;
    public String errorMessage;

    public ApiResponse(MovieLoadResponse movieLoadResponse, String errorMessage) {
        this.movieLoadResponse = movieLoadResponse;
        this.errorMessage = errorMessage;
    }
}
