package com.example.moviedemo.di;

import com.example.moviedemo.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Author: created by MarkYoung on 18/01/2019 11:49
 */
@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity contributeMainActivity();
}
