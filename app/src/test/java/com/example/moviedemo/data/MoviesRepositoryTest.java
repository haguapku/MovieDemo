package com.example.moviedemo.data;

import com.example.moviedemo.api.MovieLoadResponse;
import com.example.moviedemo.api.MovieService;
import com.example.moviedemo.data.model.Movie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Author: created by MarkYoung on 18/01/2019 14:06
 */
@RunWith(MockitoJUnitRunner.class)
public class MoviesRepositoryTest {

    @Mock
    MovieService movieService;

    private MoviesRepository moviesRepository;

    private MovieLoadResponse movieLoadResponse;

    @Before
    public void setUp() throws Exception {

        moviesRepository = new MoviesRepository(movieService);
        movieLoadResponse =
                new MovieLoadResponse(1,90,
                        Arrays.asList(new Movie("Hero",33.2f,"a/b")));
        when(movieService.getMovieList(anyString())).thenReturn(Single.just(movieLoadResponse));
    }

    @Test
    public void loadFromServerTest() {

        TestObserver<MovieLoadResponse> testObserver =
                moviesRepository.loadFromServer(anyString()).test();

        testObserver.assertValueCount(1);
        testObserver.assertComplete();
        testObserver.assertValue(movieLoadResponse);
    }
}