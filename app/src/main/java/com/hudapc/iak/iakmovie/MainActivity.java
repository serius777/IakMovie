package com.hudapc.iak.iakmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hudapc.iak.iakmovie.adapter.MovieAdapter;
import com.hudapc.iak.iakmovie.model.Movie;
import com.hudapc.iak.iakmovie.setting.APIConfig;
import com.hudapc.iak.iakmovie.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    static String TAG = MainActivity.class.getSimpleName();

    RecyclerView rvMovie;
    List<Movie> list;
    MovieAdapter adapter;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovie = (RecyclerView) findViewById(R.id.rv_movie);
        list = new ArrayList<>();
        adapter = new MovieAdapter(list);
        gson = new Gson();

        //populateDataDummy();
        populateData();
        setupRecycler();
    }

    private void setupRecycler()
    {
        rvMovie.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                2));
        rvMovie.setAdapter(adapter);
    }

    private void populateDataDummy()
    {
        for(int i = 0; i < 5; i++)
        {
            list.add(new Movie(i));
        }
        adapter.notifyDataSetChanged();
    }

    private void populateData()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("api_key",
                BuildConfig.APP_API_KEY);
        NetworkUtils networkUtils = new NetworkUtils(this);
        networkUtils.doRequest(APIConfig.getPageMoviePopularURL(),
                params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d(TAG, "onResponse: " + response);

                        JsonParser parser = new JsonParser();
                        JsonObject element = (JsonObject)parser.parse(response);

                        JsonElement responseWrapper = element.get("results");

                        list.addAll((ArrayList<Movie>) gson.fromJson(responseWrapper,
                                new TypeToken<ArrayList<Movie>>(){}.getType()));

                        Log.d(TAG, "onResponse: " + list.size());
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null)
                        {
                            Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        }
                        else
                        {
                            Log.d(TAG, "onErrorResponse: Something wrong");
                        }
                    }
                }
        );

    }
}
