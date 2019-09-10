package android.wk.com.kejarsubmission2.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import androidx.core.os.ConfigurationCompat;
import android.util.Log;
import android.widget.Toast;
import android.wk.com.kejarsubmission2.BuildConfig;
import android.wk.com.kejarsubmission2.interfaces.moviedatas.RetrofitMoviesAPI;
import android.wk.com.kejarsubmission2.models.MovieModel;
import android.wk.com.kejarsubmission2.models.MovieSuperclass;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRetrofitViewModel extends AndroidViewModel {
    private static final String LOCALE_ID = "id-ID";
    private static final String LOCALE_US = "en-US";
    private static final String URL = "https://api.themoviedb.org/3/discover/";
    private Context context;

    private MutableLiveData<ArrayList<MovieModel>> listMovies = new MutableLiveData<>();
    private Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);

    public MoviesRetrofitViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void setMovie(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitMoviesAPI retrofitMoviesAPI = retrofit.create(RetrofitMoviesAPI.class);

        Call<MovieSuperclass> call = retrofitMoviesAPI.getMovies(BuildConfig.API_KEY,LOCALE_US);
            if (locale.getLanguage().equals("en")){
                call = retrofitMoviesAPI.getMovies(BuildConfig.API_KEY,LOCALE_US);
            }else if (locale.getLanguage().equals("in")){
                call = retrofitMoviesAPI.getMovies(BuildConfig.API_KEY,LOCALE_ID);
            }
        call.enqueue(new Callback<MovieSuperclass>() {
            @Override
            public void onResponse(Call<MovieSuperclass> call, Response<MovieSuperclass> response) {
                if (!response.isSuccessful()){
                    Log.d("retro", Integer.toString(response.code()));
                }
                MovieSuperclass movieSuperclass = response.body();
                listMovies.postValue(movieSuperclass.getResults());
            }

            @Override
            public void onFailure(Call<MovieSuperclass> call, Throwable t) {
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<ArrayList<MovieModel>> getMovies(){
        return listMovies;
    }

}
