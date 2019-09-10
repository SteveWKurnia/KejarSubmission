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
import android.wk.com.kejarsubmission2.interfaces.moviedatas.RetrofitTVShowsAPI;
import android.wk.com.kejarsubmission2.models.MovieModel;
import android.wk.com.kejarsubmission2.models.MovieSuperclass;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TVShowsRetrofitViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<MovieModel>> movieModelsLiveData = new MutableLiveData<>();
    private Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
    private Context context;

    public TVShowsRetrofitViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void setShows(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/discover/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitTVShowsAPI retrofitTVShowsAPI = retrofit.create(RetrofitTVShowsAPI.class);

        Call<MovieSuperclass> call = retrofitTVShowsAPI.getShows(BuildConfig.API_KEY,"en-US");
        if (locale.getLanguage().equals("en")){
            call = retrofitTVShowsAPI.getShows(BuildConfig.API_KEY,"en-US");
        }else if(locale.getLanguage().equals("in")){
            call = retrofitTVShowsAPI.getShows(BuildConfig.API_KEY,"id-ID");
        }

        call.enqueue(new Callback<MovieSuperclass>() {
            @Override
            public void onResponse(Call<MovieSuperclass> call, Response<MovieSuperclass> response) {
                if (!response.isSuccessful()){
                    Log.d("ErrorResponse",Integer.toString(response.code()));
                }
                MovieSuperclass movieSuperclass = response.body();
                movieModelsLiveData.postValue(movieSuperclass.getResults());
            }

            @Override
            public void onFailure(Call<MovieSuperclass> call, Throwable t) {
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<ArrayList<MovieModel>> getShows(){
        return movieModelsLiveData;
    }




}
