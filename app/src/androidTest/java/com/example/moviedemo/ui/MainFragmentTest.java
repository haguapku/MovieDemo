package com.example.moviedemo.ui;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.moviedemo.R;
import com.example.moviedemo.api.ApiResponse;
import com.example.moviedemo.api.MovieLoadResponse;
import com.example.moviedemo.data.model.Movie;
import com.example.moviedemo.testing.SingleFragmentActivity;
import com.example.moviedemo.util.RecyclerViewMatcher;
import com.example.moviedemo.util.TestUtil;
import com.example.moviedemo.util.ViewModelUtil;
import com.example.moviedemo.viewmodel.MovieViewModelFactory;
import com.example.moviedemo.viewmodel.MoviesViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Author: created by MarkYoung on 18/01/2019 17:05
 */
@RunWith(AndroidJUnit4.class)
public class MainFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule =
            new ActivityTestRule<>(SingleFragmentActivity.class, true, true);

    private MoviesViewModel moviesViewModel;
    private MutableLiveData<ApiResponse> movies = new MutableLiveData<>();

    @Before
    public void setUp() throws Exception {
        MainFragment mainFragment = new MainFragment();
        moviesViewModel = mock(MoviesViewModel.class);

        when(moviesViewModel.getApiResponseLiveData()).thenReturn(movies);
        mainFragment.factory = ViewModelUtil.createFor(moviesViewModel);
        activityRule.getActivity().setFragment(mainFragment);
    }

    @Test
    public void loadMovies() {

        Movie movie = TestUtil.createMovie("Hero",32.33f,"a/b");
        MovieLoadResponse movieLoadResponse = new MovieLoadResponse(1,900, Arrays.asList(movie));
        movies.postValue(new ApiResponse(movieLoadResponse,null));
        onView(listMatcher().atPosition(0)).check(matches(hasDescendant(withText("Hero"))));
    }

    private RecyclerViewMatcher listMatcher(){
        return new RecyclerViewMatcher(R.id.movie_list);
    }
}