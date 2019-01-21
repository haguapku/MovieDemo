package com.example.moviedemo;

import android.app.Activity;
import android.app.Application;

import com.example.moviedemo.di.AppInjector;
import com.example.moviedemo.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Author: created by MarkYoung on 18/01/2019 10:43
 */
public class MovieApplication extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static MovieApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        AppInjector.init(this);
    }

    public static MovieApplication getInstance(){
        return instance;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
