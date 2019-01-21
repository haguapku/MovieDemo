package com.example.moviedemo.data;

import com.example.moviedemo.api.MovieLoadResponse;
import com.example.moviedemo.api.MovieService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Author: created by MarkYoung on 18/01/2019 10:48
 */
@Singleton
public class MoviesRepository {

    private MovieService movieService;

    @Inject
    public MoviesRepository(MovieService movieService) {
        this.movieService = movieService;
    }

    public Single<MovieLoadResponse> loadFromServer(String page){
        return movieService.getMovieList(page);
    }
}
