package com.codepath.flicker.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.flicker.R;
import com.codepath.flicker.adapters.MovieAdapter;
import com.codepath.flicker.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieAdapter movieAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();
        lvItems = (ListView) findViewById(R.id.movies_lv);
        movieAdapter = new MovieAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=3309f85c1bc4a853c7f706df1d947972";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray moviesResults = null;
                try {
                    moviesResults = response.getJSONArray("results");
                    movies.addAll( Movie.fromJSONArray(moviesResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", moviesResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
}
