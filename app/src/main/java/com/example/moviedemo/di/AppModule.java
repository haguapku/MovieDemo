package com.example.moviedemo.di;

import com.example.moviedemo.api.MovieService;
import com.example.moviedemo.util.AppSchedulerProvider;
import com.example.moviedemo.util.SchedulerProvider;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author: created by MarkYoung on 18/01/2019 11:45
 */
@Module
public class AppModule {

    @Singleton
    @Provides
    public MovieService provideMovieService(){
        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(MovieService.class);
    }

    @Singleton
    @Provides
    public SchedulerProvider provideAppSchedulerProvider(){
        return new AppSchedulerProvider();
    }
}
