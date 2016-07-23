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
public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the data item for position

        Movie movie = getItem(position);

        ViewHolder viewHolder;

        //check the existing view being reused
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.ivImageView = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverView = (TextView) convertView.findViewById(R.id.txOverView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //clear out image from convertView
        viewHolder.ivImageView.setImageResource(0);
        Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.ivImageView);

        //populate data
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverView.setText(movie.getOverView());

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivImageView;
        TextView tvTitle;
        TextView tvOverView;
    }

}
