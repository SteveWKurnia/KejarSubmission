package android.wk.com.kejarsubmission2.room.repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.wk.com.kejarsubmission2.room.dao.MovieDao;
import android.wk.com.kejarsubmission2.room.entities.MovieEntity;
import android.wk.com.kejarsubmission2.room.roomDatabase.MovieDatabase;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private MovieDao movieDao;
    private LiveData<List<MovieEntity>> allMovies;

    public MovieRepository(Application application){
        MovieDatabase movieDatabase = MovieDatabase.getInstance(application);
        movieDao = movieDatabase.movieDao();
        allMovies = movieDao.getAllData();
    }

    public void insert(MovieEntity movieEntity){
        new AsyncInsert(movieDao).execute(movieEntity);
    }
    public void delete(MovieEntity movieEntity){
        new AsyncDelete(movieDao).execute(movieEntity);
    }
    public LiveData<List<MovieEntity>> getAllMovies(){
        return allMovies;
    }

    private static class AsyncInsert extends AsyncTask<MovieEntity, Void, Void>{
        private MovieDao movieDao;

        private AsyncInsert(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            movieDao.insert(movieEntities[0]);
            return null;
        }
    }
    private static class AsyncDelete extends AsyncTask<MovieEntity, Void, Void>{
        private MovieDao movieDao;
        private AsyncDelete(MovieDao movieDao){
            this.movieDao = movieDao;
        }
        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            movieDao.delete(movieEntities[0]);
            return null;
        }
    }
}
