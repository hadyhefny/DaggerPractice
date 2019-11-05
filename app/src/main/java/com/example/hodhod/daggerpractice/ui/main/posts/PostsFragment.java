package com.example.hodhod.daggerpractice.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hodhod.daggerpractice.R;
import com.example.hodhod.daggerpractice.models.Post;
import com.example.hodhod.daggerpractice.ui.main.MainActivity;
import com.example.hodhod.daggerpractice.ui.main.Resource;
import com.example.hodhod.daggerpractice.util.VerticalSpacingItemDecoration;
import com.example.hodhod.daggerpractice.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragment";

    private PostsViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    PostsRecyclerAdapter postsAdapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recycler_view);

        viewModel = ViewModelProviders.of(this,providerFactory).get(PostsViewModel.class);
        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers(){

        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
        viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if(listResource!= null){
                    switch (listResource.status){

                        case LOADING:{
                            Log.d(TAG, "onChanged: Loading");
                            break;
                        }
                        case SUCCESS:{
                            Log.d(TAG, "onChanged: got the posts");
                            postsAdapter.setPosts(listResource.data);
                            break;
                        }
                        case ERROR:{
                            Log.e(TAG, "onChanged: ERROR: " + listResource.message );
                            break;
                        }

                    }
                }
            }
        });

    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(postsAdapter);
    }
}
