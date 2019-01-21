package com.example.moviedemo.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.example.moviedemo.api.ApiResponse;
import com.example.moviedemo.api.MovieLoadResponse;
import com.example.moviedemo.data.MoviesRepository;
import com.example.moviedemo.data.model.Movie;
import com.example.moviedemo.util.RxJavaTestSchedulerRule;
import com.example.moviedemo.util.RxJavaTrampolineConverterRule;
import com.example.moviedemo.util.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import io.reactivex.Single;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Author: created by MarkYoung on 18/01/2019 14:30
 */
@RunWith(MockitoJUnitRunner.class)
public class MoviesViewModelTest {

    @Rule
    public final RxJavaTrampolineConverterRule rule = new RxJavaTrampolineConverterRule();

    @Mock
    private MoviesRepository moviesRepository;

    private MoviesViewModel moviesViewModel;

    private MovieLoadResponse movieLoadResponse;

    private TestSchedulerProvider testSchedulerProvider;

    @Before
    public void setUp() throws Exception {

        /*testSchedulerProvider = new TestSchedulerProvider();
        moviesViewModel = new MoviesViewModel(moviesRepository,testSchedulerProvider);

        movieLoadResponse =
                new MovieLoadResponse(1,90,
                        Arrays.asList(new Movie("Hero",33.2f,"a/b")));
        when(moviesRepository.loadFromServer(anyString())).thenReturn(Single.just(movieLoadResponse));*/

    }

    @Test
    public void loadMoviesTest() {


    }
}