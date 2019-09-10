package android.wk.com.kejarsubmission2.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import android.appwidget.AppWidgetManager;
import android.wk.com.kejarsubmission2.room.entities.MovieEntity;
import android.wk.com.kejarsubmission2.room.repositories.MovieRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private MovieRepository repository;
    private LiveData<List<MovieEntity>> allMovies;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        allMovies = repository.getAllMovies();
    }

    public void delete(MovieEntity movieEntity){
        repository.delete(movieEntity);
    }
    public LiveData<List<MovieEntity>> getAllMovies(){
        return allMovies;
    }
}
