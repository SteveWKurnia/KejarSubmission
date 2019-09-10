package android.wk.com.kejarsubmission2.interfaces.moviedatas;

import android.wk.com.kejarsubmission2.models.MovieSuperclass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewMoviesAPI {

    @GET("movie")
    Call<MovieSuperclass> getNewMovie(@Query("api_key")String API_KEY,
                                      @Query("primary_release_date.gte")String RELEASE_DATE,
                                      @Query("primary_release_date.lte")String PRIMARY_RELEASE_DATE);

}
