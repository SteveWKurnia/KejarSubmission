package android.wk.com.kejarsubmission2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import android.wk.com.kejarsubmission2.adapter.MovieAdapter;
import android.wk.com.kejarsubmission2.models.MovieModel;
import android.wk.com.kejarsubmission2.viewmodels.SearchMovieViewModel;

import java.util.ArrayList;

import static android.wk.com.kejarsubmission2.MainActivity.CURRENT_FRAGMENT;

public class SearchActivity extends AppCompatActivity {

    private ImageView backButton;
    private androidx.appcompat.widget.SearchView searchView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        final SearchMovieViewModel searchMovieViewModel = ViewModelProviders.of(SearchActivity.this).get(SearchMovieViewModel.class);
        final SearchMovieViewModel searchMovieViewModel = new ViewModelProvider(this).get(SearchMovieViewModel.class);

        final int fragment_type = getIntent().getIntExtra(CURRENT_FRAGMENT,0);
        recyclerView = findViewById(R.id.recycler_search);

        movieAdapter = new MovieAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);

        progressBar = findViewById(R.id.progress_bar_search);
        progressBar.setVisibility(View.INVISIBLE);

        backButton = findViewById(R.id.back_button_search);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchView = findViewById(R.id.search_view);
        searchView.setIconified(false);
        if (fragment_type==0){
            searchView.setQueryHint("Search movie here...");
        }else if(fragment_type==1){
            searchView.setQueryHint("Search tv shows here...");
        }
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    searchMovieViewModel.getMovies().observe(SearchActivity.this,getMovies);
                    if (fragment_type==0){
//                        SearchMovieViewModel searchMovieViewModel = ViewModelProviders.of(SearchActivity.this).get(SearchMovieViewModel.class);
                        searchMovieViewModel.setMovies(newText, 0);
                    }else if(fragment_type==1){
//                        SearchMovieViewModel searchMovieViewModel = ViewModelProviders.of(SearchActivity.this).get(SearchMovieViewModel.class);
                        searchMovieViewModel.setMovies(newText, 1);
                    }
                }
                return false;
            }
        });


    }

    private Observer<ArrayList<MovieModel>> getMovies = new Observer<ArrayList<MovieModel>>() {
        @Override
        public void onChanged(ArrayList<MovieModel> movieModels) {
            movieAdapter.setData(movieModels);
            progressBar.setVisibility(View.GONE);
        }
    };
}
