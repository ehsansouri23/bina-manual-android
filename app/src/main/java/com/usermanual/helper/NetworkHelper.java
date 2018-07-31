package com.usermanual.helper;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkHelper {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

//    public static boolean isInternetAvailable(Context context) {
//        final boolean[] available = {false};
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.anywebsiteyouthinkwillnotbedown.com").openConnection());
//                    urlc.setConnectTimeout(5000);
//                    urlc.setReadTimeout(5000);
//                    try {
//                        urlc.connect();
//                        available[0] =  urlc.getResponseCode() == 200;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//    }
}
