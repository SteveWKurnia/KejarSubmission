package android.wk.com.kejarsubmission2.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieModel implements Parcelable {
    @SerializedName(value = "title", alternate = {"name"})
    private String title;

    @SerializedName(value = "release_date", alternate = {"first_air_date"})
    private String release_date;

    @SerializedName(value = "poster_path")
    private String poster_path;

    @SerializedName(value = "vote_average")
    private double vote_average;

    @SerializedName(value = "overview")
    private String overview;

    public MovieModel(String title, String release_date, String poster_path, double vote_average, String overview) {
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getRelease_date() {
        return release_date;
    }
    public String getPoster_path() {
        return poster_path;
    }
    public double getVote_average() {
        return vote_average;
    }
    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.poster_path);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.overview);
    }
    protected MovieModel(Parcel in) {
        this.title = in.readString();
        this.release_date = in.readString();
        this.poster_path = in.readString();
        this.vote_average = in.readDouble();
        this.overview = in.readString();
    }
    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
