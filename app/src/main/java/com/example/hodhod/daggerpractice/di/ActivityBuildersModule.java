package com.example.hodhod.daggerpractice.di;

import com.example.hodhod.daggerpractice.di.auth.AuthModule;
import com.example.hodhod.daggerpractice.di.auth.AuthScope;
import com.example.hodhod.daggerpractice.di.auth.AuthViewModelsModule;
import com.example.hodhod.daggerpractice.di.main.MainFragmentBuildersModule;
import com.example.hodhod.daggerpractice.di.main.MainModule;
import com.example.hodhod.daggerpractice.di.main.MainScope;
import com.example.hodhod.daggerpractice.di.main.MainViewModelsModule;
import com.example.hodhod.daggerpractice.ui.auth.AuthActivity;
import com.example.hodhod.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class}
    )
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class
            , MainModule.class}
    )
    abstract MainActivity contributeMainActivity();


}
