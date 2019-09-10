package android.wk.com.kejarsubmission2.room.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private double vote_average;
    private String poster_path;
    private String overview;
    private String release_date;

    public MovieEntity(String title, double vote_average, String poster_path, String overview, String release_date) {
        this.title = title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

}
