package com.hudapc.iak.iakmovie.setting;

/**
 * Created by HUDA on 25/05/2017.
 */

public class APIConfig
{
    public static final String HOST_URL = "https://api.themoviedb.org/3/";

    public static final String PAGE_CONFIGURATION = "configuration";

    public static final String PAGE_MOVIE = "movie";

    public static final String PAGE_MOVIE_POPULAR = "popular";

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

    public static String getMoviePosterURL(String dim, String file)
    {
        return  "http://image.tmdb.org/t/p/" + dim + file;
    }
}
