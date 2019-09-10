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
import android.wk.com.kejarsubmission2.viewmodels.TVShowsRetrofitViewModel;

import java.util.ArrayList;

public class TVShowsAPIMenuFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tvshows_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.tvshows_progressbar);

//        TVShowsRetrofitViewModel TVShowsRetrofitViewModel = ViewModelProviders.of(this).get(TVShowsRetrofitViewModel.class);
        TVShowsRetrofitViewModel TVShowsRetrofitViewModel = new ViewModelProvider(this).get(TVShowsRetrofitViewModel.class);
        TVShowsRetrofitViewModel.getShows().observe(this, getShows);

        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();

        RecyclerView recyclerView = view.findViewById(R.id.tvshows_recycler);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        TVShowsRetrofitViewModel.setShows();

    }

    private Observer<ArrayList<MovieModel>> getShows = new Observer<ArrayList<MovieModel>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MovieModel> movieModels) {
            if (movieModels!=null){
                movieAdapter.setData(movieModels);
                progressBar.setVisibility(View.GONE);
            }
        }
    };

}
