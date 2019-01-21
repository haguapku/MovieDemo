package com.example.moviedemo.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import io.reactivex.Single;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

/**
 * Author: created by MarkYoung on 18/01/2019 10:54
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MovieService movieService;

    private MockWebServer mockWebServer;

    @Before
    public void setUp() throws Exception {

        mockWebServer = new MockWebServer();
        movieService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(MovieService.class);
    }

    @Test
    public void load() throws IOException {

        enqueueResponse("page1.json");

        Single<MovieLoadResponse> responseSingle = movieService.getMovieList("1");
        MovieLoadResponse response = responseSingle.blockingGet();

        assertThat(response.page, Is.is(1));
        assertThat(response.total_pages,Is.is(990));
    }

    private void enqueueResponse(String fileName) throws IOException {

        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-resource/"+fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)));
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}