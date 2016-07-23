package com.codepath.flicker.Controllers;

import android.os.Bundle;
import android.util.Log;

import com.codepath.flicker.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class YouTubePlayerActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_you_tube_player);
        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.youtube_player);

        final String movieId = getIntent().getStringExtra("moive_id");
        final String videosUrl =  String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=3309f85c1bc4a853c7f706df1d947972", movieId);
        Log.d("DEBUG", "videosUrl:" + videosUrl);

        youTubePlayerView.initialize(getResources().getString(R.string.youtube_player_api_key),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(videosUrl, new JsonHttpResponseHandler(){

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                String firstVideoKey = null;
                                try {
                                    firstVideoKey = response.getJSONArray("results").getJSONObject(0).getString("key");
                                    Log.d("DEBUG", "firstVideoKey:" + firstVideoKey);
                                    //youTubePlayer.cueVideo(firstVideoKey);
                                    youTubePlayer.loadVideo(firstVideoKey);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                            }
                        });
                        // do any work here to cue video, play video, etc.
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d("DEBUG", "failed:" + youTubeInitializationResult.toString());
                    }
                });
    }
}
