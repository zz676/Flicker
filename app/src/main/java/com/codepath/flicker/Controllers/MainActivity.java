package com.codepath.flicker.Controllers;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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

    private ArrayList<Movie> movies;
    private MovieAdapter movieAdapter;
    private ListView lvItems;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        lvItems = (ListView) findViewById(R.id.movies_lv);
        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        fetchMovies();
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchMovies();
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void fetchMovies() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=3309f85c1bc4a853c7f706df1d947972";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray moviesResults = null;
                try {
                    movieAdapter.clear();
                    moviesResults = response.getJSONArray("results");
                    movieAdapter.addAll(Movie.fromJSONArray(moviesResults));
                    //movies.addAll(Movie.fromJSONArray(moviesResults));
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("DEBUG", "Fetch movies error: " + throwable.toString());
            }
        });
    }
}
