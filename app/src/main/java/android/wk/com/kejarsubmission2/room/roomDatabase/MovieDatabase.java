package android.wk.com.kejarsubmission2.room.roomDatabase;

import android.content.Context;
import android.wk.com.kejarsubmission2.room.dao.MovieDao;
import android.wk.com.kejarsubmission2.room.entities.MovieEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = MovieEntity.class, version =5)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance;

    public abstract MovieDao movieDao();

    public static synchronized MovieDatabase getInstance(Context context){
         if (instance==null){
             instance = Room.databaseBuilder(
                     context.getApplicationContext(),
                     MovieDatabase.class,
                     "favorite_database")
                     .fallbackToDestructiveMigration()
                     .build();
         }
         return instance;
    }
}
