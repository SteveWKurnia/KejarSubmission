package android.wk.com.kejarsubmission2.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.wk.com.kejarsubmission2.R;
import android.wk.com.kejarsubmission2.room.entities.MovieEntity;
import android.wk.com.kejarsubmission2.utilities.ImageSupport;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<MovieEntity> allMovies = new ArrayList<>();

    public void setData(List<MovieEntity> movieEntities){
        allMovies.clear();
        allMovies.addAll(movieEntities);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_api_recycler,viewGroup,false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int i) {
        favoriteViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return allMovies.size();
    }

    public MovieEntity getMovie(int position){
        return allMovies.get(position);
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        ImageView movie_poster;
        TextView movie_title, movie_desc, movie_release, movie_vote;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_poster = itemView.findViewById(R.id.poster_recycler_api);
            movie_title = itemView.findViewById(R.id.title_recycler_api);
            movie_desc = itemView.findViewById(R.id.synopsis_api);
            movie_release = itemView.findViewById(R.id.release_date_recycler_api);
            movie_vote = itemView.findViewById(R.id.rating_recycler_api);
        }

        void bind(int position){
            ImageSupport imageSupport = new ImageSupport();
            Picasso.get().load(imageSupport.getPosterFile() + imageSupport.getPosterSize() + allMovies.get(position).getPoster_path()).into(movie_poster);
            movie_title.setText(allMovies.get(position).getTitle());
            movie_desc.setText(allMovies.get(position).getOverview());
            movie_release.setText(allMovies.get(position).getRelease_date());
            movie_vote.setText(String.format("%s",allMovies.get(position).getVote_average()));
        }

    }

}
