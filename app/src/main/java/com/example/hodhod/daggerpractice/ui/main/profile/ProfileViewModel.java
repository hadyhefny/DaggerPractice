package com.example.hodhod.daggerpractice.ui.main.profile;

import android.util.Log;

import com.example.hodhod.daggerpractice.SessionManager;
import com.example.hodhod.daggerpractice.models.User;
import com.example.hodhod.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        Log.d(TAG, "ProfileViewModel: viewmodel is ready");

        this.sessionManager = sessionManager;
        
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser(){
        return sessionManager.getAuthUser();
    }
}
