package com.hudapc.iak.iakmovie.adapter;

import android.graphics.Bitmap;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hudapc.iak.iakmovie.BuildConfig;
import com.hudapc.iak.iakmovie.R;
import com.hudapc.iak.iakmovie.model.Movie;
import com.hudapc.iak.iakmovie.setting.APIConfig;
import com.hudapc.iak.iakmovie.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by HUDA on 26/05/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter <MovieAdapter.MovieViewHolder>
{
    List<Movie> list;

    public MovieAdapter(List<Movie> list)
    {
        this.list = list;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)
    {
        Movie data = list.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imPoster;

        public MovieViewHolder(View itemView)
        {
            super(itemView);
            imPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
        }

        public void bind(Movie data)
        {
            Picasso.with(itemView.getContext()).
                    load(APIConfig.getMoviePosterURL("w342",
                            data.getPoster_path())).
                    into(imPoster);

//            imPoster.setImageDrawable(
//                    ResourcesCompat.getDrawable(itemView.getResources(),
//                            R.drawable.dummy_poster,
//                            null)
//            );
        }
    }
}
