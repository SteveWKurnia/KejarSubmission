package android.wk.com.kejarsubmission2.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.wk.com.kejarsubmission2.R;
import android.wk.com.kejarsubmission2.room.entities.MovieEntity;
import android.wk.com.kejarsubmission2.room.roomDatabase.MovieDatabase;
import android.wk.com.kejarsubmission2.utilities.ImageSupport;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;

    private List<MovieEntity> movieEntities = new ArrayList<>();
    private MovieDatabase database;

    private ImageSupport imageSupport = new ImageSupport();

    public StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        database = MovieDatabase.getInstance(context);
    }

    @Override
    public void onDataSetChanged() {
        Log.d("widgetFav","InService! OnDataSetChanged");
        long identityToken = Binder.clearCallingIdentity();
        movieEntities.clear();
        movieEntities = database.movieDao().getListData();
        Log.d("widgetFav","InService! OnDataSetChanged Size: " + Integer.toString(movieEntities.size()));
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d("widgetFav","InService! Size: " + Integer.toString(movieEntities.size()));
        return movieEntities.size();
    }

    @Override
    public RemoteViews getViewAt(final int position) {

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_content);

        try{
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(imageSupport.getPosterFile() + imageSupport.getPosterSize() + movieEntities.get(position).getPoster_path())
                    .submit(512, 512)
                    .get();

            rv.setImageViewBitmap(R.id.widget_image, bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.d("widgetFav", "getViewAt: " + imageSupport.getPosterFile() + imageSupport.getPosterSize() + movieEntities.get(position).getPoster_path() + " " + movieEntities.get(position).getTitle());

        Bundle extras = new Bundle();
        extras.putString(FavoriteWidget.EXTRA_TITLE, movieEntities.get(position).getTitle());
//        extras.putParcelable(FavoriteWidget.EXTRA_MODEL,
//                new MovieModel(
//                        movieEntities.get(position).getTitle(),
//                        movieEntities.get(position).getRelease_date(),
//                        movieEntities.get(position).getPoster_path(),
//                        movieEntities.get(position).getVote_average(),
//                        movieEntities.get(position).getOverview()
//                ));
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.widget_image, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
