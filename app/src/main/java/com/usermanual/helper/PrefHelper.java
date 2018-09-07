package com.usermanual.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefHelper {

    private static final String PREFS = "prefs";
    public static final String MEDIA_LIST_KEY = "medias";
    public static final String MEDIA_KEY = "media";
    public static final String SEARCH_QUERY = "search_query";
    public static final String TITLES_URL = "http://s8.ghiasi.me/api/titles";
    public static final String SUBTITLES_URL = "http://s8.ghiasi.me/api/subtitle/";
    public static final String MEDIAS_URL = "http://s8.ghiasi.me/api/media";
    public static final String FULL_NEWS_URL = "http://s8.ghiasi.me/api/news/";

    public static final String PREF_FONT_SIZE = "pref_font_size";
    public static final String PREF_ANIMATIONS = "pref_animations";

    public static final String PREF_NEWS_ID = "pref_news_id";

    public static final String PREF_STATE = "state";
    public static final String PREF_TITLE_ID = "title";
    public static final String PREF_SUBTITLE_ID = "subtitle_id";

    public static final String PREF_FAV_SUBTITLES = "fav_subtitles";

    private static SharedPreferences prefs;

    private static SharedPreferences getPrefs(Context context) {
        if (prefs == null)
            prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs;
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String def) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getString(key, def);
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key, int def) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getInt(key, def);
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key, boolean def) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getBoolean(key, def);
    }
}
