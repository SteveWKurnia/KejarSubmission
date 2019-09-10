package android.wk.com.kejarsubmission2.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.wk.com.kejarsubmission2.MovieDetailActivity;
import android.wk.com.kejarsubmission2.R;
import android.wk.com.kejarsubmission2.models.MovieModel;
import android.wk.com.kejarsubmission2.utilities.ImageSupport;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<MovieModel> allMovies = new ArrayList<>();

    public void setData(ArrayList<MovieModel> movieModels){
        allMovies.clear();
        allMovies.addAll(movieModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_api_recycler,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        holder.bind(allMovies.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MovieDetailActivity.class);
                intent.putExtra("parcelledAPIData", allMovies.get(holder.getAdapterPosition()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView rate;
        TextView description;
        TextView release_date;
        ImageView poster;
        CardView cardView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_out);
            title = itemView.findViewById(R.id.title_recycler_api);
            description = itemView.findViewById(R.id.synopsis_api);
            release_date = itemView.findViewById(R.id.release_date_recycler_api);
            poster = itemView.findViewById(R.id.poster_recycler_api);
            rate = itemView.findViewById(R.id.rating_recycler_api);
        }

        void bind(MovieModel movieModel){

            title.setText(movieModel.getTitle());
            title.setSelected(true);
            description.setText(movieModel.getOverview());
            release_date.setText(movieModel.getRelease_date());

            String rating = Double.toString(movieModel.getVote_average());
            rate.setText(rating);

            ImageSupport imageSupport = new ImageSupport();
            Picasso.get().load(imageSupport.getPosterFile() + imageSupport.getPosterSize() + movieModel.getPoster_path()).into(poster);
        }
    }

}
