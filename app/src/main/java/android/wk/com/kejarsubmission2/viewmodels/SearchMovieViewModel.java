package android.wk.com.kejarsubmission2.viewmodels;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;
import android.wk.com.kejarsubmission2.BuildConfig;
import android.wk.com.kejarsubmission2.interfaces.searchmovie.RetrofitSearchMovieAPI;
import android.wk.com.kejarsubmission2.interfaces.searchmovie.RetrofitSearchTVShowsAPI;
import android.wk.com.kejarsubmission2.models.MovieModel;
import android.wk.com.kejarsubmission2.models.MovieSuperclass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.os.ConfigurationCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchMovieViewModel extends AndroidViewModel {
    private static final String LOCALE_ID = "id-ID";
    private static final String LOCALE_US = "en-US";
    private static final String URL = " https://api.themoviedb.org/3/search/";

    private MutableLiveData<ArrayList<MovieModel>> listMovies = new MutableLiveData<>();
    private Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);

    public SearchMovieViewModel(@NonNull Application application) {
        super(application);
    }

    public void setMovies(String INPUT, int type){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<MovieSuperclass> call = new Call<MovieSuperclass>() {
            @Override
            public Response<MovieSuperclass> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(Callback<MovieSuperclass> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<MovieSuperclass> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };

        if (type==0){
            RetrofitSearchMovieAPI retrofitSearchMovieAPI = retrofit.create(RetrofitSearchMovieAPI.class);
            call = retrofitSearchMovieAPI.getSearchedMovies(BuildConfig.API_KEY, LOCALE_US, INPUT);
            if (locale.getLanguage().equals("en")){
                call = retrofitSearchMovieAPI.getSearchedMovies(BuildConfig.API_KEY,LOCALE_US, INPUT);
            }else if (locale.getLanguage().equals("in")){
                call = retrofitSearchMovieAPI.getSearchedMovies(BuildConfig.API_KEY,LOCALE_ID, INPUT);
            }
        }else if (type==1){
            RetrofitSearchTVShowsAPI retrofitSearchTVShowsAPI = retrofit.create(RetrofitSearchTVShowsAPI.class);
            call = retrofitSearchTVShowsAPI.getSearchedShows(BuildConfig.API_KEY, LOCALE_US, INPUT);
            if (locale.getLanguage().equals("en")){
                call = retrofitSearchTVShowsAPI.getSearchedShows(BuildConfig.API_KEY,LOCALE_US, INPUT);
            }else if (locale.getLanguage().equals("in")){
                call = retrofitSearchTVShowsAPI.getSearchedShows(BuildConfig.API_KEY,LOCALE_ID, INPUT);
            }
        }

        call.enqueue(new Callback<MovieSuperclass>() {
            @Override
            public void onResponse(Call<MovieSuperclass> call, Response<MovieSuperclass> response) {
                if (!response.isSuccessful()){
                    Log.d("searchRetro", Integer.toString(response.code()));
                }
                MovieSuperclass movieSuperclass = response.body();
                listMovies.postValue(movieSuperclass.getResults());
            }

            @Override
            public void onFailure(Call<MovieSuperclass> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<MovieModel>> getMovies(){return listMovies;}

}
