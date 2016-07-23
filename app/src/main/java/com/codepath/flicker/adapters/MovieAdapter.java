package com.codepath.flicker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flicker.R;
import com.codepath.flicker.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sam on 7/22/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie>{

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the data item for position

        Movie movie = getItem(position);

        //check the existing view being reused
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        //find the image view
        ImageView ivImageView = (ImageView) convertView.findViewById(R.id.ivMovieImage);
        //clear out image from convertView
        ivImageView.setImageResource(0);
        Picasso.with(getContext()).load(movie.getPosterPath()).into(ivImageView);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvOverView = (TextView) convertView.findViewById(R.id.txOverView);

        //populate data
        tvTitle.setText(movie.getOriginalTitle());
        tvOverView.setText(movie.getOverView());

        return convertView;
    }
}
