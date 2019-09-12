package android.wk.com.kejarsubmission2.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.wk.com.kejarsubmission2.room.entities.MovieEntity;
import android.wk.com.kejarsubmission2.room.roomDatabase.MovieDatabase;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.wk.com.kejarsubmission2.room.entities.MovieEntity.SCHEME;
import static android.wk.com.kejarsubmission2.room.entities.MovieEntity.TABLE_NAME;

public class MyContentProvider extends ContentProvider{

    public static final String AUTHORITY = "android.wk.com.kejarsubmission2";
    public static final Uri CONTENT_URI = new Uri.Builder().
            scheme(SCHEME).
            authority(AUTHORITY).
            appendPath(TABLE_NAME).
            build();
    public static final int CODE_ALL_MOVIES = 1;
    public static final int CODE_ID_MOVIE = 2;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final Context context = getContext();
    private MovieDatabase database;

    static {
        matcher.addURI(AUTHORITY, MovieEntity.TABLE_NAME,CODE_ALL_MOVIES);
        matcher.addURI(AUTHORITY,MovieEntity.TABLE_NAME + "/#",CODE_ID_MOVIE);
    }

    @Override
    public boolean onCreate() {
        Log.d("ContentProviderWK","onCreate");
        database = MovieDatabase.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final Cursor cursor;
        final int code = matcher.match(uri);
        Log.d("WKContentProv",Integer.toString(code));
        switch (matcher.match(uri)){
            case CODE_ALL_MOVIES:
                cursor = database.movieDao().getCursorData();
                break;
            case CODE_ID_MOVIE:
                cursor = null;
                break;
            default:
                throw new IllegalArgumentException("I don't know this uri: " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id;
        switch (matcher.match(uri)){
            case CODE_ALL_MOVIES:
                if (context==null){
                    return null;
                }
                id = database.movieDao().insert(MovieEntity.fromContentValues(values));
                context.getContentResolver().notifyChange(uri,null);
                break;
                default:
                    id=0;
                    break;
        }
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (matcher.match(uri)){
            case CODE_ALL_MOVIES:
                throw new IllegalArgumentException("Invalid uri, cannot update without id" + uri);
            case CODE_ID_MOVIE:
                if (context==null){
                    return 0;
                }
                deleted = database.movieDao().deleteById(uri.getLastPathSegment());
                context.getContentResolver().notifyChange(uri, null);
                break;
                default:
                    deleted = 0;
        }
        Log.d("WKContentProv",Integer.toString(deleted));
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
