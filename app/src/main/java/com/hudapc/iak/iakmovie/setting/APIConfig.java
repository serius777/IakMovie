package com.hudapc.iak.iakmovie.setting;

import com.hudapc.iak.iakmovie.utilities.PreferenceUtils;

/**
 * Created by HUDA on 25/05/2017.
 */

public class APIConfig
{
    public static final String HOST_URL = "https://api.themoviedb.org/3/";

    public static final String PAGE_CONFIGURATION = "configuration";

    public static final String PAGE_MOVIE = "movie";

    public static final String PAGE_MOVIE_POPULAR = "popular";

    public static final String PAGE_MOVIE_TOPRATED = "top_rated";

    private static final String PAGE_GENRE = "genre/movie/list";

    public static String getConfigurationURL()
    {
        return HOST_URL + PAGE_CONFIGURATION;
    }

    public static String getGenreURL()
    {
        return HOST_URL + PAGE_GENRE;
    }

    public static String getPageMoviePopularURL()
    {
        return HOST_URL + PAGE_MOVIE + "/" + PAGE_MOVIE_POPULAR;
    }

    public static String getPageMovieTopRatedURL()
    {
        return HOST_URL + PAGE_MOVIE + "/" + PAGE_MOVIE_TOPRATED;
    }

    public static String getPageMovieByPreference(int shortInt)
    {
        if(shortInt == 0)
            return getPageMoviePopularURL();

        return getPageMovieTopRatedURL();
    }

    public static String getMoviePosterURL(String dim, String file)
    {
        return  "http://image.tmdb.org/t/p/" + dim + file;
    }
}
