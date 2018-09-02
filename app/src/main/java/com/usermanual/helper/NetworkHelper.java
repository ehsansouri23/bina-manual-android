package com.usermanual.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class NetworkHelper {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    static HttpURLConnection openConnection(String method, String url) throws IOException {
        Log.d("NetworkHelper", "openConnection() method = [" + method + "], url = [" + url + "]");
        HttpURLConnection connection;
        int responseCode;
        CookieManager cookieManager = new CookieManager();
        do {
            if (!url.startsWith("http") && !url.startsWith("https"))
                throw new UrlNotSupportedException("non http destination");
            String cookies = getCookiesHeaderForUrl(cookieManager, url);
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5 * 1000);
            connection.setReadTimeout(10 * 1000);
            connection.setInstanceFollowRedirects(true); //does not follow redirects from a protocol to another (e.g. http to https and vice versa)
            connection.setRequestMethod(method);
            if (!TextUtils.isEmpty(cookies))
                connection.setRequestProperty("Cookie", cookies);
            responseCode = connection.getResponseCode();
            addCookiesToCookieManager(cookieManager, url, connection.getHeaderFields().get("Set-Cookie"));
            url = connection.getHeaderField("Location");
        } while (responseCode / 100 == 3 && !TextUtils.isEmpty(url));
        return connection;
    }

    private static String getCookiesHeaderForUrl(CookieManager cookieManager, String url) {
        String s = "";
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException ignored) {
        }
        if (uri != null) {
            for (HttpCookie cookie : cookieManager.getCookieStore().get(uri))
                s += cookie.getName() + "=" + cookie.getValue() + ";";
        }
        if (TextUtils.isEmpty(s))
            return null;
        else
            return s.substring(0, s.length() - 1);
    }

    private static void addCookiesToCookieManager(CookieManager cookieManager, String url, List<String> cookies) {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException ignored) {
        }
        if (cookies != null)
            for (String cookie : cookies)
                try {
                    cookieManager.getCookieStore().add(uri, HttpCookie.parse(cookie).get(0));
                } catch (Exception ignored) {
                }
    }

    static class UrlNotSupportedException extends IllegalStateException {
        UrlNotSupportedException(String s) {
            super(s);
        }
    }
}
