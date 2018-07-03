package com.emddi.mymusic.utils;

import android.support.annotation.StringDef;

import com.emddi.mymusic.BuildConfig;
import com.emddi.mymusic.utils.Constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class APIServiceUtils {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ApiGenre.GENRE_ALL_MUSIC,
            ApiGenre.GENRE_ALL_AUDIO,
            ApiGenre.GENRE_ALTERNATIVER_ROCK,
            ApiGenre.GENRE_AMBIENT,
            ApiGenre.GENRE_CLASSICAL,
            ApiGenre.GENRE_COUNTRY})
    public @interface ApiGenre {
        String GENRE_ALL_MUSIC = "all-music";
        String GENRE_ALL_AUDIO = "all-audio";
        String GENRE_ALTERNATIVER_ROCK = "alternativerock";
        String GENRE_AMBIENT = "ambient";
        String GENRE_CLASSICAL = "classical";
        String GENRE_COUNTRY = "country";
        String GENRE_LIMIT_DEFAULT = "20";
        String GENRE_OFFSET_DEFAULT = "20";
    }

    @ApiGenre
    public static List<String> LIST_TRACK_GENRES =
            new LinkedList<>(Arrays.asList(ApiGenre.GENRE_ALL_MUSIC,
                    ApiGenre.GENRE_ALL_AUDIO,
                    ApiGenre.GENRE_ALTERNATIVER_ROCK,
                    ApiGenre.GENRE_AMBIENT,
                    ApiGenre.GENRE_CLASSICAL,
                    ApiGenre.GENRE_COUNTRY));

    public static String createUrl(String genres, String limit, String offset) {
        StringBuilder stringBuilder = new StringBuilder(Constants.BASE_URL)
                .append(Constants.GET_KIND_TOP)
                .append("&genre=soundcloud:genres:")
                .append(genres)
                .append("&client_id=")
                .append(BuildConfig.API_KEY)
                .append("&limit=")
                .append(limit)
                .append("&offset=")
                .append(offset);
        return stringBuilder.toString();
    }
}
