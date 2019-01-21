package com.example.moviedemo.api;

import android.arch.lifecycle.LiveData;

import com.example.moviedemo.BuildConfig;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: created by MarkYoung on 18/01/2019 10:46
 */

public interface MovieService {

    @GET("/3/movie/popular?api_key=" + BuildConfig.API_KEY)
    Single<MovieLoadResponse> getMovieList(@Query("page")String page);
}
