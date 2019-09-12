package android.wk.com.kejarsubmission2.room.dao;

import androidx.lifecycle.LiveData;

import android.database.Cursor;
import android.wk.com.kejarsubmission2.room.entities.MovieEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorite_table")
    List<MovieEntity> getListData();

    @Insert
    long insert(MovieEntity movieEntity);

    @Delete
    void delete(MovieEntity movieEntity);

    @Query("SELECT * FROM favorite_table")
    LiveData<List<MovieEntity>> getAllData();

    @Query("SELECT * FROM favorite_table")
    Cursor getCursorData();

    @Query("DELETE FROM " + MovieEntity.TABLE_NAME + " WHERE ID = :id" )
    int deleteById(String id);
}
