package com.hudapc.iak.iakmovie;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hudapc.iak.iakmovie.model.Movie;
import com.hudapc.iak.iakmovie.setting.APIConfig;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DetailActivity extends AppCompatActivity
{
    private static final String TAG = DetailActivity.class.getSimpleName();

    ImageView ivBackdrop;
    FloatingActionButton fabFavorite;
    TextView tvTitle;
    ImageView ivPoster;
    TextView tvYear;
    TextView tvDuration;
    TextView tvVote;
    TextView tvOverView;

    private Movie data;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        ivBackdrop = (ImageView) findViewById(R.id.iv_backdrop);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivPoster = (ImageView) findViewById(R.id.iv_poster);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        tvVote = (TextView) findViewById(R.id.tv_vote);
        tvOverView = (TextView) findViewById(R.id.tv_overview);

        gson = new Gson();

        Intent intent = getIntent();
        Log.d(TAG, "onCreate: " + intent.getStringExtra("json"));
        data = gson.fromJson(intent.getStringExtra("json"), Movie.class);

        bind();
    }

    private void bind()
    {
        tvTitle.setText(data.getOriginal_title());
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        Date date;
        Calendar calendar = new GregorianCalendar();
        try
        {
            //date = sdf.parse(data.getRelease_date());
            calendar.setTime(sdf.parse(data.getRelease_date()));
            String stringYear = String.valueOf(calendar.get(Calendar.YEAR));
            tvYear.setText(stringYear);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        //final String durationString = String(getString(R.string.format_duration_minute, data.get))
        final String voteSTring = String.valueOf(data.getVote_average());
        tvVote.setText(voteSTring);
        tvOverView.setText(data.getOverview());

        Picasso.with(getApplicationContext())
                .load(APIConfig.getMoviePosterURL("w300",
                        data.getBackdrop_path()))
                .into(ivBackdrop);
        Picasso.with(getApplicationContext())
                .load(APIConfig.getMoviePosterURL("w185",
                        data.getPoster_path()))
                .into(ivPoster);
    }
}
