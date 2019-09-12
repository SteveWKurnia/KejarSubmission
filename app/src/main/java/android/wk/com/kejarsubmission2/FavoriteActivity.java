package android.wk.com.kejarsubmission2;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.wk.com.kejarsubmission2.adapter.FavoriteAdapter;
import android.wk.com.kejarsubmission2.room.entities.MovieEntity;
import android.wk.com.kejarsubmission2.viewmodels.FavoriteViewModel;

import java.util.List;

import static android.wk.com.kejarsubmission2.contentprovider.MyContentProvider.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {

    FavoriteViewModel favoriteViewModel;
    FavoriteAdapter favoriteAdapter;
    RecyclerView recyclerView;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toast.makeText(this, "Swipe to delete",Toast.LENGTH_LONG).show();

        backButton = findViewById(R.id.back_button_fav);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.favorite_recycler);
        favoriteAdapter = new FavoriteAdapter();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Uri uri = Uri.parse(CONTENT_URI + "/" + favoriteAdapter.getMovie(viewHolder.getAdapterPosition()).getId());
                Log.d("FavActCursor","onSwiped: " + Integer.toString(favoriteAdapter.getMovie(viewHolder.getAdapterPosition()).getId()) + " " + favoriteAdapter.getMovie(viewHolder.getAdapterPosition()).getTitle());
                favoriteViewModel.delete(favoriteAdapter.getMovie(viewHolder.getAdapterPosition()));
                getContentResolver().delete(uri, null,null);
                getContentResolver().notifyChange(CONTENT_URI, null);
            }
        })
                .attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favoriteAdapter);

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
//        favoriteViewModel.getAllMovieCursors().observe(this, new Observer<Cursor>() {
//            @Override
//            public void onChanged(Cursor cursor) {
//                ArrayList<MovieEntity> movieEntities = mapCursorToList(cursor);
//                for (int i =0;i<movieEntities.size();i++){
//                    Log.d("FavActCursor",Integer.toString(movieEntities.get(i).getId()) + " " + movieEntities.get(i).getTitle());
//                }
//                favoriteAdapter.setData(movieEntities);
//            }
//        });
        favoriteViewModel.getAllMovies().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                for (int i =0;i<movieEntities.size();i++){
                    Log.d("FavActCursor",Integer.toString(movieEntities.get(i).getId()) + " " + movieEntities.get(i).getTitle());
                }
                favoriteAdapter.setData(movieEntities);
            }
        });
    }

}
