package com.usermanual.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.usermanual.activities.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class DownloadFile extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "DownloadFile";

    List<String> urls;
    Context context;
    MainActivity.DownloadingMediaDelegate delegate;

    public DownloadFile(List<String> urls, Context context, MainActivity.DownloadingMediaDelegate delegate) {
        this.urls = urls;
        this.context = context;
        this.delegate = delegate;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean result = true;
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            delegate.update(i + "");
            result = downloadFile(url, i);
            Log.e(TAG, "doInBackground: result: " + result);
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        delegate.finished();
        File baseFile = context.getFilesDir();
        String[] names = baseFile.list();
        for (int i = 0; i < names.length; i++) {
            Log.e(TAG, "onPostExecute: downloaded name: " + names[i]);
        }
    }

    private boolean downloadFile(String url, int i) {
        Log.e(TAG, "downloadFile: downloading file " + url);
        HttpURLConnection connection = null;
        InputStream is = null;
        FileOutputStream fos = null;
        File file = null;
        File tempFile = null;
        try {
            file = StorageHelper.getFile(context, i + ".mp4");
            connection = NetworkHelper.openConnection("GET", url);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false; // expect HTTP 200 OK, so we don't mistakenly save error report instead of the file
            }

            is = connection.getInputStream();
            fos = new FileOutputStream(file);

            byte data[] = new byte[4096];
            int count;
            while ((count = is.read(data)) != -1) {
                fos.write(data, 0, count);
            }
            fos.flush();
            fos.close();
            is.close();
            if (connection != null)
                connection.disconnect();
        } catch (Exception ignored) {
        }
        return true;
    }
}
