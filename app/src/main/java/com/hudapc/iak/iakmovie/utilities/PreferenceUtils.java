package com.hudapc.iak.iakmovie.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HUDA on 27/05/2017.
 */

public class PreferenceUtils
{
    public static final String PREFS_NAME = "SETING_PREFS";
    public static final String SHORT_KEY = "short";

    public void save(Context context, int shortInt) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        // 0 : popular
        // 1 : top_rated
        editor.putInt(SHORT_KEY, shortInt); //3

        editor.commit(); //4
    }

    public int getShort(Context context) {
        SharedPreferences settings;
        int shortInt;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        shortInt = settings.getInt(SHORT_KEY, 0);
        return shortInt;
    }
}
