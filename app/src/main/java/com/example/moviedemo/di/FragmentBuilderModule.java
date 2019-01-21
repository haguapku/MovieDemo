package com.example.moviedemo.di;

import com.example.moviedemo.ui.MainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Author: created by MarkYoung on 18/01/2019 13:41
 */
@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract MainFragment contriutesMainFragment();
}
