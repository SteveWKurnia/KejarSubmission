package android.wk.com.kejarsubmission2.room.dao;

import androidx.lifecycle.LiveData;
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
    void insert(MovieEntity movieEntity);

    @Delete
    void delete(MovieEntity movieEntity);

    @Query("SELECT * FROM favorite_table")
    LiveData<List<MovieEntity>> getAllData();


}
