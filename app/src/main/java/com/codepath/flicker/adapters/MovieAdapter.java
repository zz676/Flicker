package com.codepath.flicker.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flicker.Controllers.YouTubePlayerActivity;
import com.codepath.flicker.R;
import com.codepath.flicker.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sam on 7/22/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private ArrayList<Movie> movies = null;
    private Context context = null;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (Float.parseFloat(movies.get(position).getAverageReview()) > 5) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int viewType = this.getItemViewType(position);
        final Movie movie = movies.get(position);
        switch (viewType) {
            case 1:
                final HighViewHolder highViewHolder;
                View highConvertView = convertView;
                if (highConvertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    highConvertView = inflater.inflate(R.layout.item_high_reviews_movie, parent, false);
                    highViewHolder = new HighViewHolder();
                    highViewHolder.ivImageView = (ImageView) highConvertView.findViewById(R.id.ivMovieImage);
                    highViewHolder.progressBarPicasso = (ProgressBar) highConvertView.findViewById(R.id.progressBarPicasso);
                    highViewHolder.ivYouTubePlayer = (ImageView) highConvertView.findViewById(R.id.ivYoutubePlayerImage);
                    highViewHolder.ivYouTubePlayer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Intent intent = new Intent(getContext(), YouTubePlayerActivity.class);
                            intent.putExtra("moive_id", movie.getMovieId());
                            getContext().startActivity(intent);
                        }
                    });
                    highConvertView.setTag(highViewHolder);
                } else {
                    highViewHolder = (HighViewHolder) highConvertView.getTag();
                }

                highViewHolder.ivYouTubePlayer.setVisibility(View.INVISIBLE);
                highViewHolder.progressBarPicasso.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(movie.getBackdropPath())
                        .into(highViewHolder.ivImageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                if (highViewHolder.progressBarPicasso != null) {
                                    highViewHolder.progressBarPicasso.setVisibility(View.GONE);
                                    highViewHolder.ivYouTubePlayer.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });

                return highConvertView;
            case 0:
                final LowViewHolder lowViewHolder;
                View lowConvertView = convertView;
                String imageUrl = "";
                if (lowConvertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    lowConvertView = inflater.inflate(R.layout.item_low_reviews_movie, parent, false);
                    lowViewHolder = new LowViewHolder();
                    lowViewHolder.tvDate = (TextView) lowConvertView.findViewById(R.id.tvDate);
                    lowViewHolder.tvTitle = (TextView) lowConvertView.findViewById(R.id.tvTitle);
                    lowViewHolder.rbMovieVotes = (RatingBar) lowConvertView.findViewById(R.id.rbMovieVotes);
                    lowViewHolder.tvAverageVote = (TextView) lowConvertView.findViewById(R.id.tvAverageVote);
                    lowViewHolder.ivImageView = (ImageView) lowConvertView.findViewById(R.id.ivMovieImage);
                    lowViewHolder.progressBarPicasso = (ProgressBar) lowConvertView.findViewById(R.id.progressBarPicasso);
                    lowViewHolder.ivYouTubePlayer = (ImageView) lowConvertView.findViewById(R.id.ivYoutubePlayerImage);
                    lowViewHolder.ivYouTubePlayer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Intent intent = new Intent(getContext(), YouTubePlayerActivity.class);
                            intent.putExtra("moive_id", movie.getMovieId());
                            getContext().startActivity(intent);
                        }
                    });
                    lowConvertView.setTag(lowViewHolder);
                } else {
                    lowViewHolder = (LowViewHolder) lowConvertView.getTag();
                }

                lowViewHolder.ivImageView.setImageResource(0);
                int orientation = getContext().getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    imageUrl = movie.getPosterPath();
                    //Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.ivImageView);
                } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    imageUrl = movie.getBackdropPath();
                    //Picasso.with(getContext()).load(movie.getBackdropPath()).into(viewHolder.ivImageView);
                }
                lowViewHolder.ivYouTubePlayer.setVisibility(View.INVISIBLE);
                lowViewHolder.progressBarPicasso.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(imageUrl)
                        .into(lowViewHolder.ivImageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                if (lowViewHolder.progressBarPicasso != null) {
                                    lowViewHolder.progressBarPicasso.setVisibility(View.GONE);
                                    lowViewHolder.ivYouTubePlayer.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });

                lowViewHolder.tvDate.setText(movie.getReleaseDate());
                lowViewHolder.tvTitle.setText(movie.getOriginalTitle());
                lowViewHolder.tvAverageVote.setText(movie.getAverageReview() + "/10");
                Drawable drawable = lowViewHolder.rbMovieVotes.getProgressDrawable();
                drawable.setColorFilter(Color.parseColor("#118C4E"), PorterDuff.Mode.SRC_ATOP);
                lowViewHolder.rbMovieVotes.setRating(Float.parseFloat(movie.getAverageReview()) / 2);
                return lowConvertView;
            default:
                return null;
        }
    }

    private static class LowViewHolder {
        ImageView ivImageView;
        ProgressBar progressBarPicasso;
        ImageView ivYouTubePlayer;
        TextView tvTitle;
        RatingBar rbMovieVotes;
        TextView tvAverageVote;
        TextView tvDate;
    }

    private static class HighViewHolder {
        ImageView ivImageView;
        ProgressBar progressBarPicasso;
        ImageView ivYouTubePlayer;
    }

}
