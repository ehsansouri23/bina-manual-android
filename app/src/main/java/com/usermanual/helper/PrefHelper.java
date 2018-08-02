package com.usermanual.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefHelper {

    private static final String PREFS = "prefs";
    public static final String MEDIA_LIST_KEY = "medias";
    public static final String MEDIA_KEY = "media";
    public static final String BASE_URL = "https://s10.ghiasi.me/api/getdata";

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
}
