package com.example.moviedemo.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.moviedemo.viewmodel.MovieViewModelFactory;

/**
 * Author: created by MarkYoung on 18/01/2019 17:20
 */
public class ViewModelUtil {

    private ViewModelUtil(){}

    public static <T extends ViewModel> ViewModelProvider.Factory createFor(final T model){

        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if(modelClass.isAssignableFrom(model.getClass()))
                    return (T)model;
                throw new IllegalArgumentException("unexpected model class " + modelClass);
            }
        };
    }
}
