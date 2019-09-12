package android.wk.com.kejarsubmission2.room.entities;

import android.content.ContentValues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = MovieEntity.TABLE_NAME)
public class MovieEntity {

    public static final String SCHEME = "content";
    public static final String TABLE_NAME = "favorite_table";

    @ColumnInfo(index = true,name = "ID")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "TITLE")
    private String title;
    @ColumnInfo(name =  "VOTE_AVERAGE")
    private double vote_average;
    @ColumnInfo(name = "POSTER_PATH")
    private String poster_path;
    @ColumnInfo(name = "OVERVIEW")
    private String overview;
    @ColumnInfo(name = "RELEASE_DATE")
    private String release_date;

    public MovieEntity(){}

    public MovieEntity(String title, double vote_average, String poster_path, String overview, String release_date) {
        this.title = title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public MovieEntity(int id, String title, double vote_average, String poster_path, String overview, String release_date) {
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public static MovieEntity fromContentValues(ContentValues values){
        MovieEntity movieEntity = new MovieEntity();
        if (values.containsKey("ID"))movieEntity.setId(values.getAsInteger("ID"));
        if (values.containsKey("TITLE"))movieEntity.setTitle(values.getAsString("TITLE"));
        if (values.containsKey("VOTE_AVERAGE"))movieEntity.setVote_average(values.getAsDouble("VOTE_AVERAGE"));
        if (values.containsKey("POSTER_PATH"))movieEntity.setPoster_path(values.getAsString("POSTER_PATH"));
        if (values.containsKey("OVERVIEW"))movieEntity.setOverview(values.getAsString("OVERVIEW"));
        if (values.containsKey("RELEASE_DATE"))movieEntity.setRelease_date(values.getAsString("RELEASE_DATE"));

        return movieEntity;
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

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
