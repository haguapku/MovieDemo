package com.example.moviedemo.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Author: created by MarkYoung on 18/01/2019 11:59
 */
@Singleton
public class MovieViewModelFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public MovieViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        Provider<ViewModel> creator = creators.get(modelClass);
        if(creator == null){
            for(Map.Entry<Class<? extends ViewModel>,Provider<ViewModel>> entry:creators.entrySet()){
                if(modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if(creator == null)
            throw new IllegalArgumentException("Unknown ViewModel class");
        return (T)creator.get();
    }

    /*private MoviesRepository moviesRepository;

    private SchedulerProvider schedulerProvider;

    @Inject
    public MovieViewModelFactory(MoviesRepository moviesRepository, SchedulerProvider schedulerProvider) {
        this.moviesRepository = moviesRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(MoviesViewModel.class))
            return (T)new MoviesViewModel(moviesRepository,schedulerProvider);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }*/
}
