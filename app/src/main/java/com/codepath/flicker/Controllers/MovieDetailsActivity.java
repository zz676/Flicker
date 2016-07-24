package com.codepath.flicker.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flicker.R;
import com.codepath.flicker.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView ivImageView;
    ProgressBar progressBarPicasso;
    ImageView ivYouTubePlayer;
    TextView tvTitle;
    TextView tvOverView;
    RatingBar rbMovieVotes;
    TextView tvAverageVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ivImageView = (ImageView) findViewById(R.id.ivMovieImage);
        progressBarPicasso = (ProgressBar) findViewById(R.id.progressBarPicasso);
        ivYouTubePlayer = (ImageView) findViewById(R.id.ivYoutubePlayerImage);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverView = (TextView) findViewById(R.id.txOverView);
        rbMovieVotes = (RatingBar) findViewById(R.id.rbMovieVotes);
        tvAverageVote = (TextView) findViewById(R.id.tvAverageVote);

        final Movie movie = (Movie) getIntent().getParcelableExtra("movieDetails");
        if (movie != null) {
            ivYouTubePlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent = new Intent(getApplicationContext(), YouTubePlayerActivity.class);
                    intent.putExtra("moive_id", movie.getMovieId());
                    startActivity(intent);
                }
            });
            ivImageView.setImageResource(0);
            progressBarPicasso.setVisibility(View.VISIBLE);
            Picasso.with(this).load(movie.getBackdropPath().replace("w780", "w1280"))
                    .into(ivImageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (progressBarPicasso != null) {
                                progressBarPicasso.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });

            //populate data
            tvTitle.setText(movie.getOriginalTitle());
            tvOverView.setText(movie.getOverView());
            tvAverageVote.setText(movie.getAverageReview() + "/10");
            rbMovieVotes.setRating(Float.parseFloat(movie.getAverageReview()));
        }
    }
}
