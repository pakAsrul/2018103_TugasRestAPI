package com.example.TugasRestAPI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.TugasRestAPI.R;
import com.example.TugasRestAPI.adapters.MovieRecycleView;
import com.example.TugasRestAPI.adapters.OnMovieListener;
import com.example.TugasRestAPI.models.MovieModel;
import com.example.TugasRestAPI.models.viewmodels.PopularMovieListViewModel;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    private PopularMovieListViewModel popularMovieListViewModel;
    private RecyclerView recyclerView;
    private MovieRecycleView recycleViewAdapter;

    private boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        recyclerView = findViewById(R.id.recycleView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        popularMovieListViewModel = new ViewModelProvider(this).get(PopularMovieListViewModel.class);


        // data observer
        ObservasingAnyChangesPopularMovie();

        // show popular movies
        popularMovieListViewModel.getPopularMovie(1);

        // configuring recycleview
        configureRecycleView();
    }


    // init recycleView and adding data to it
    private void configureRecycleView() {
        recycleViewAdapter = new MovieRecycleView( this);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // pagination & loading nect results
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                    if (isPopular) {
                        popularMovieListViewModel.popularMovieNextPage();
                    }
                }

        });
    }

    private void ObservasingAnyChangesPopularMovie() {
        popularMovieListViewModel.getPopularMovie().observe(this, movieModels -> {
            // observing any data changes
            if (movieModels != null) {
                for (MovieModel model : movieModels) {
                    // get data
                    recycleViewAdapter.setMovie(movieModels);
                }
            }
        });
    }

    @Override
    public void onMovieClick(int pos) {
        // here is going to detail movie that has clicked
        Intent intent = new Intent(this,DetailMovieActivity.class);
        intent.putExtra("movie",recycleViewAdapter.getSelectedMovie(pos));
        startActivity(intent);
    }
}