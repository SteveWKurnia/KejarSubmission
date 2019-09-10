package android.wk.com.kejarsubmission2.interfaces.searchmovie;

import android.wk.com.kejarsubmission2.models.MovieSuperclass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitSearchMovieAPI {

    @GET("movie")
    Call<MovieSuperclass> getSearchedMovies(@Query("api_key")String API_KEY,
                                            @Query("language")String LOCALE,
                                            @Query("query")String INPUT);

}
