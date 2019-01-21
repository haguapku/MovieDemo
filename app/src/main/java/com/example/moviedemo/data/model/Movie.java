package com.example.moviedemo.data.model;

/**
 * Author: created by MarkYoung on 18/01/2019 10:48
 */
public class Movie {

    public String title;
    public float popularity;
    public String poster_path;

    public Movie(String title, float popularity, String poster_path) {
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
    }
}
