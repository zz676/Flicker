package com.codepath.flicker.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the data item for position

        final Movie movie = getItem(position);
        final ViewHolder viewHolder;

        //check the existing view being reused
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.ivImageView = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.progressBarPicasso = (ProgressBar) convertView.findViewById(R.id.progressBarPicasso);
            viewHolder.ivYouTubePlayer = (ImageView) convertView.findViewById(R.id.ivYoutubePlayerImage);
            viewHolder.ivYouTubePlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent = new Intent(getContext(), YouTubePlayerActivity.class);
                    intent.putExtra("moive_id", movie.getMovieId());
                    getContext().startActivity(intent);
                }
            });
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverView = (TextView) convertView.findViewById(R.id.txOverView);
            viewHolder.rbMovieVotes = (RatingBar) convertView.findViewById(R.id.rbMovieVotes);
            viewHolder.tvAverageVote = (TextView) convertView.findViewById(R.id.tvAverageVote);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //clear out image from convertView
        viewHolder.ivImageView.setImageResource(0);
        String imageUrl = "";
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageUrl = movie.getPosterPath();
            //Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.ivImageView);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = movie.getBackdropPath();
            //Picasso.with(getContext()).load(movie.getBackdropPath()).into(viewHolder.ivImageView);
        }

        viewHolder.progressBarPicasso.setVisibility(View.VISIBLE);
        Picasso.with(getContext()).load(imageUrl)
                .into(viewHolder.ivImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (viewHolder.progressBarPicasso != null) {
                            viewHolder.progressBarPicasso.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

        //populate data
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverView.setText(movie.getOverView());
        viewHolder.tvAverageVote.setText(movie.getAverageReview() + "/10");
        viewHolder.rbMovieVotes.setRating(Float.parseFloat(movie.getAverageReview()));
        return convertView;
    }

    private static class ViewHolder {
        ImageView ivImageView;
        ProgressBar progressBarPicasso;
        ImageView ivYouTubePlayer;
        TextView tvTitle;
        TextView tvOverView;
        RatingBar rbMovieVotes;
        TextView tvAverageVote;
    }

}
