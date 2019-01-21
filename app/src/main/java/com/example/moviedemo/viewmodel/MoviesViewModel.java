package com.example.moviedemo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.moviedemo.api.ApiResponse;
import com.example.moviedemo.api.MovieLoadResponse;
import com.example.moviedemo.data.MoviesRepository;
import com.example.moviedemo.util.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Author: created by MarkYoung on 18/01/2019 11:22
 */
@Singleton
public class MoviesViewModel extends ViewModel {

    public MutableLiveData<ApiResponse> apiResponseLiveData = new MutableLiveData<>();

    private MoviesRepository moviesRepository;

    private CompositeDisposable disposables;

    private SchedulerProvider appSchedulerProvider;

    @Inject
    public MoviesViewModel(MoviesRepository moviesRepository,SchedulerProvider appSchedulerProvider) {
        this.moviesRepository = moviesRepository;
        this.appSchedulerProvider = appSchedulerProvider;
        disposables = new CompositeDisposable();
    }

    public LiveData<ApiResponse> getApiResponseLiveData(){
        return apiResponseLiveData;
    }

    public void loadMovies(final String page){

        disposables.add(moviesRepository.loadFromServer(page)
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
                .subscribeWith(new DisposableSingleObserver<MovieLoadResponse>() {
                    @Override
                    public void onSuccess(MovieLoadResponse movieLoadResponse) {
                        apiResponseLiveData.setValue(new ApiResponse(movieLoadResponse,null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        apiResponseLiveData.setValue(new ApiResponse(null,e.getMessage()));
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
