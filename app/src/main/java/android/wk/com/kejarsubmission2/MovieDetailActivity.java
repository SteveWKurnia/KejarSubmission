package android.wk.com.kejarsubmission2;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.wk.com.kejarsubmission2.models.MovieModel;
import android.wk.com.kejarsubmission2.room.entities.MovieEntity;
import android.wk.com.kejarsubmission2.room.repositories.MovieRepository;
import android.wk.com.kejarsubmission2.utilities.ImageSupport;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.wk.com.kejarsubmission2.contentprovider.MyContentProvider.CONTENT_URI;

public class MovieDetailActivity extends AppCompatActivity {

    MovieModel movieModel;
    MovieEntity movieEntity;
    ImageView poster, back_button;
    TextView title, synopsis, rating, release_date;
    Button toFav;
    List<MovieEntity> allMovies = new ArrayList<>();
//    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        setData();

        toFav = findViewById(R.id.add_to_fav);
        poster = findViewById(R.id.poster_detail);
        title = findViewById(R.id.title_detail);
        synopsis = findViewById(R.id.synopsis_detail);
        rating = findViewById(R.id.rating_detail);
        release_date = findViewById(R.id.release_date_detail);
        back_button = findViewById(R.id.back_button);

        ImageSupport imageSupport = new ImageSupport();
        Picasso.get().load(imageSupport.getPosterFile() + imageSupport.getPosterSize() + movieModel.getPoster_path()).resize((int) getResources().getDimension(R.dimen.poster_width), (int) getResources().getDimension(R.dimen.poster_height)).into(poster);
        title.setText(movieModel.getTitle());
        synopsis.setText(movieModel.getOverview());
        Double rating_api = movieModel.getVote_average();
        String rating_str = Double.toString(rating_api);
        rating.setText(rating_str);
        release_date.setText(movieModel.getRelease_date());

        movieEntity = new MovieEntity(
                movieModel.getTitle(),
                movieModel.getVote_average(),
                movieModel.getPoster_path(),
                movieModel.getOverview(),
                movieModel.getRelease_date());


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//                RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.favorite_widget);
//                ComponentName component = new ComponentName(context, FavoriteWidget.class);
//                appWidgetManager.updateAppWidget(component, rv);

                MovieRepository repository = new MovieRepository(getApplication());
                boolean isFavorite = false;
                for (int i = 0; i < allMovies.size(); i++) {
                    if (movieEntity.getTitle().equals(allMovies.get(i).getTitle())) {
                        isFavorite = true;
                        Log.d("room", "title: " + allMovies.get(i).getTitle());
                        break;
                    }
                }
                if (isFavorite)
                    Toast.makeText(v.getContext(), "Already added to fav", Toast.LENGTH_SHORT).show();
                else {
                    ContentValues values = new ContentValues();
                    values.put("ID", movieEntity.getId());
                    values.put("TITLE", movieEntity.getTitle());
                    values.put("OVERVIEW", movieEntity.getOverview());
                    values.put("RELEASE_DATE", movieEntity.getRelease_date());
                    values.put("VOTE_AVERAGE", movieEntity.getVote_average());
                    values.put("POSTER_PATH", movieEntity.getPoster_path());
                    getContentResolver().insert(CONTENT_URI, values);

                    repository.insert(movieEntity);
                    Toast.makeText(v.getContext(), R.string.added_to_fav, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData() {
        if (getIntent().hasExtra("parcelledAPIData")) {
            movieModel = getIntent().getParcelableExtra("parcelledAPIData");
        }
    }

}
