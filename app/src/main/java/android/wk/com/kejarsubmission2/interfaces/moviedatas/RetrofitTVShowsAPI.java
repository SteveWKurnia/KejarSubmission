package android.wk.com.kejarsubmission2.interfaces.moviedatas;

import android.wk.com.kejarsubmission2.models.MovieSuperclass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitTVShowsAPI {

    @GET("tv")
    Call<MovieSuperclass> getShows(@Query("api_key") String API_KEY,
                                   @Query("language") String LOCALE);
}
