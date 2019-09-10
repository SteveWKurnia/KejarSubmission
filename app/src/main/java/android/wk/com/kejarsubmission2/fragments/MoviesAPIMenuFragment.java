package android.wk.com.kejarsubmission2.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.wk.com.kejarsubmission2.adapter.MovieAdapter;
import android.wk.com.kejarsubmission2.models.MovieModel;
import android.wk.com.kejarsubmission2.R;
import android.wk.com.kejarsubmission2.viewmodels.MoviesRetrofitViewModel;

import java.util.ArrayList;


public class MoviesAPIMenuFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movies_fragment, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);

        MoviesRetrofitViewModel moviesRetrofitViewModel;
//        moviesRetrofitViewModel = ViewModelProviders.of(this).get(MoviesRetrofitViewModel.class);
        moviesRetrofitViewModel = new ViewModelProvider(this).get(MoviesRetrofitViewModel.class);
        moviesRetrofitViewModel.getMovies().observe(this, getMovies);

        RecyclerView recyclerView = view.findViewById(R.id.movies_recycler);

        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        moviesRetrofitViewModel.setMovie();
    }

    private Observer<ArrayList<MovieModel>> getMovies = new Observer<ArrayList<MovieModel>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MovieModel> movieModelAPIS) {
            if (movieModelAPIS!=null){
                movieAdapter.setData(movieModelAPIS);
                progressBar.setVisibility(View.GONE);
            }
        }
    };

}
