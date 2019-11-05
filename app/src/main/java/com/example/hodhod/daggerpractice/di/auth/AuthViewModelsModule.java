package com.example.hodhod.daggerpractice.di.auth;

import com.example.hodhod.daggerpractice.di.ViewModelKey;
import com.example.hodhod.daggerpractice.ui.auth.AuthViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);

}
