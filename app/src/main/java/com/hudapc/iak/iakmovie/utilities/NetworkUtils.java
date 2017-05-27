package com.hudapc.iak.iakmovie.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by HUDA on 26/05/2017.
 */

public class NetworkUtils
{
    static final String TAG = NetworkUtils.class.getSimpleName();

    private RequestQueue request;

    public NetworkUtils(Context context) {
        this.request = Volley.newRequestQueue(context);
    }

    public static String buildUri(String page, HashMap<String, String> params)
    {
        Uri.Builder uriBuilder = Uri.parse(page).buildUpon();
        Iterator iterator = params.keySet().iterator();
        while(iterator.hasNext())
        {
            String key = (String) iterator.next();
            String value = params.get(key);
            uriBuilder.appendQueryParameter(key, value);
        }
        return uriBuilder.build().toString();
    }

    public void doRequest(String page,
                          HashMap<String, String> params,
                          Response.Listener<String> onRespone,
                          Response.ErrorListener onError)
    {
        final String url = buildUri(page, params);

        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET,
                url,
                onRespone,
                onError);
        request.add(stringRequest);
    }
}
