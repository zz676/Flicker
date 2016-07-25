package com.codepath.flicker.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sam on 7/22/16.
 */
public class Movie implements Parcelable {

    public String getMovieId() {
        return movieId;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w185/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w500/%s", backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public String getAverageReview() {
        return averageReview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getmPopularity() {
        return mPopularity;
    }

    String movieId;
    String posterPath;
    String backdropPath;
    String originalTitle;
    String overView;
    String averageReview;
    String releaseDate;
    String mPopularity;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.movieId = jsonObject.getString("id");
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overView = jsonObject.getString("overview");
        this.averageReview = jsonObject.getString("vote_average");
        this.releaseDate = jsonObject.getString("release_date");
        this.mPopularity = jsonObject.getString("popularity");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) throws JSONException {
        ArrayList<Movie> results = new ArrayList<>();
        for (int x = 0; x < array.length(); x++) {
            results.add(new Movie(array.getJSONObject(x)));
        }
        return results;
    }

    public Movie() {
        // Normal actions performed by class, since this is still a normal object!
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(movieId);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(originalTitle);
        dest.writeString(overView);
        dest.writeString(averageReview);
        dest.writeString(releaseDate);
        dest.writeString(mPopularity);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        movieId = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        originalTitle = in.readString();
        overView = in.readString();
        averageReview = in.readString();
        releaseDate = in.readString();
        mPopularity = in.readString();
    }
}
