package com.example.hodhod.daggerpractice.ui.main.posts;

import android.util.Log;

import com.example.hodhod.daggerpractice.SessionManager;
import com.example.hodhod.daggerpractice.models.Post;
import com.example.hodhod.daggerpractice.models.User;
import com.example.hodhod.daggerpractice.network.main.MainApi;
import com.example.hodhod.daggerpractice.ui.auth.AuthResource;
import com.example.hodhod.daggerpractice.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {

    private static final String TAG = "PostsViewModel";

    private MediatorLiveData<Resource<List<Post>>> posts;

    private final SessionManager sessionManager;
    private final MainApi mainApi;

    @Inject
    public PostsViewModel(MainApi mainApi, SessionManager sessionManager) {
        this.mainApi = mainApi;
        this.sessionManager = sessionManager;
        Log.d(TAG, "PostsViewModel: viewmodel is working");
    }

    public LiveData<Resource<List<Post>>> observePosts() {
        if (posts == null) {
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading((List<Post>) null));

            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getPostsFromUser(sessionManager.getAuthUser().getValue().data.getId())
                            .onErrorReturn(new Function<Throwable, List<Post>>() {
                                @Override
                                public List<Post> apply(Throwable throwable) throws Exception {
                                    Log.d(TAG, "apply: " + throwable);
                                    Post post = new Post();
                                    post.setId(-1);
                                    ArrayList<Post> posts = new ArrayList<>();
                                    posts.add(post);
                                    return posts;
                                }
                            })
                            .map(new Function<List<Post>, Resource<List<Post>>>() {
                                @Override
                                public Resource<List<Post>> apply(List<Post> posts) throws Exception {
                                    if (posts.size() > 0) {
                                        if (posts.get(0).getId() == -1) {
                                            return Resource.error("something went wrong", null);
                                        }
                                    }
                                    return Resource.success(posts);
                                }
                            })
                            .subscribeOn(Schedulers.io())
            );

            posts.addSource(source, new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                    posts.setValue(listResource);
                    posts.removeSource(source);
                }
            });
        }
        return posts;
    }


}
