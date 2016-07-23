package com.codepath.flicker.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sam on 7/22/16.
 */
public class Movie {

    public String getMovieId() {
        return movieId;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    String movieId;
    String posterPath;
    String originalTitle;
    String overView;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overView = jsonObject.getString("overview");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) throws JSONException {
        ArrayList<Movie> results = new ArrayList<>();
        for (int x = 0; x < array.length(); x++) {
            results.add(new Movie(array.getJSONObject(x)));
        }

        return results;
    }
}
