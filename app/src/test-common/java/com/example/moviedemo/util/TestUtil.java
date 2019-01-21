package com.example.moviedemo.util;

import com.example.moviedemo.data.model.Movie;

/**
 * Author: created by MarkYoung on 18/01/2019 17:33
 */
public class TestUtil {

    public static Movie createMovie(String title, float popularity, String url){

        return new Movie(title,popularity,url);
    }
}
