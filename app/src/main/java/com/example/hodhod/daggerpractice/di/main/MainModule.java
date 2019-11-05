package com.example.hodhod.daggerpractice.di.main;

import com.example.hodhod.daggerpractice.network.main.MainApi;
import com.example.hodhod.daggerpractice.ui.main.posts.PostsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static PostsRecyclerAdapter provideAdapter(){
        return new PostsRecyclerAdapter();
    }

    @MainScope
    @Provides
    static MainApi provideRetrofit(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }
}
