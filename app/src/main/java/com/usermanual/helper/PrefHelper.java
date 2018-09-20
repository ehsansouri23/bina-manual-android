package com.usermanual.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefHelper {

    private static final String PREFS = "prefs";

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