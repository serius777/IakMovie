package com.hudapc.iak.iakmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
import com.hudapc.iak.iakmovie.utilities.PreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ClickListenerRecycler
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        rvMovie = (RecyclerView) findViewById(R.id.rv_movie);
        list = new ArrayList<>();
        adapter = new MovieAdapter(list);
        adapter.setListener(this);
        gson = new Gson();

        //populateDataDummy();
        populateData();
        setupRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.setGroupCheckable(R.id.sub1, true, true);

        PreferenceUtils pref = new PreferenceUtils();
        int shortInt = pref.getShort(getApplicationContext());
        if(shortInt == 0)
        {
            menu.findItem(R.id.short0).setChecked(true);
        }
        else
        {
            menu.findItem(R.id.short1).setChecked(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.short0:
            case R.id.short1:
                if(!item.isChecked())
                {
                    int shortInt = (item.getItemId() == R.id.short0)? 0 : 1;
                    item.setChecked(true);
                    PreferenceUtils pref = new PreferenceUtils();
                    pref.save(getApplicationContext(),
                             shortInt);
                    list.clear();
                    populateData();
                    return true;
                }
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Movie data)
    {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("json", gson.toJson(data));
        startActivity(intent);
    }

    private void setupRecycler()
    {
        rvMovie.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                2));
        rvMovie.setAdapter(adapter);
    }

    private void populateData()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("api_key",
                BuildConfig.APP_API_KEY);
        int shortInt = new PreferenceUtils().getShort(getApplicationContext());
        NetworkUtils networkUtils = new NetworkUtils(this);
        networkUtils.doRequest(APIConfig.getPageMovieByPreference(shortInt),
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
