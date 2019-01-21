package com.example.moviedemo.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviedemo.R;
import com.example.moviedemo.api.ApiResponse;
import com.example.moviedemo.data.model.Movie;
import com.example.moviedemo.di.Injectable;
import com.example.moviedemo.viewmodel.MoviesViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Author: created by MarkYoung on 18/01/2019 12:22
 */
public class MainFragment extends Fragment implements Injectable {

    public static final String TAG = "MainFragment";

    private MoviesViewModel moviesViewmodel;

    private MoviesAdapter moviesAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    int lastVisibleItem;
    int page = 1;
    int total_pages = 0;
    boolean isLoading = false;
    boolean isPullUp = false;

    @Inject
    ViewModelProvider.Factory factory;

    /**
     * Get the instance of the MainFragment.
     * @return a instance of MainFragment
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        moviesAdapter = new MoviesAdapter(new ArrayList<Movie>());
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesAdapter.resetData();
                page = 1;
                moviesViewmodel.loadMovies(page+"");
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.movie_list);
        final GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+1 == moviesAdapter.getItemCount()
                        && !isLoading && !isRefreshing){
                    if(!isPullUp){
                        isPullUp = true;
                        moviesAdapter.changeLoadState(MoviesAdapter.PULL_UP_TO_LOAD);
                    }else {
                        isPullUp = false;
                        if(page < total_pages){
                            isLoading = true;
                            page++;
                            moviesViewmodel.loadMovies(page +"");
                            moviesAdapter.changeLoadState(MoviesAdapter.LOADING_MORE);
                        }else{
                            moviesAdapter.changeLoadState(MoviesAdapter.NO_MORE);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = manager.findLastVisibleItemPosition();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moviesViewmodel = ViewModelProviders.of(this,factory).get(MoviesViewModel.class);
        subscribUi(moviesViewmodel);
        moviesViewmodel.loadMovies(page+"");
    }

    private void subscribUi(final MoviesViewModel moviesViewModel){

        moviesViewModel.getApiResponseLiveData().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                if(apiResponse.movieLoadResponse != null){
                    //Hide refresh icon when loading finished
                    swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                    page = apiResponse.movieLoadResponse.page;
                    total_pages= apiResponse.movieLoadResponse.total_pages;
                    moviesAdapter.addData(apiResponse.movieLoadResponse.results);
                    moviesAdapter.changeLoadState(MoviesAdapter.PULL_UP_TO_LOAD);
                    isLoading = false;
                }else if(apiResponse.errorMessage != null){
                    //Hide refresh icon when error happens
                    swipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                    moviesAdapter.changeLoadState(MoviesAdapter.PULL_UP_TO_LOAD);
                    isLoading = false;
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
