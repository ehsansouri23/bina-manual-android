package com.usermanual.helper;

import android.os.AsyncTask;

import com.usermanual.model.Media;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class FileDownloadHelper {

    private List<Media> mediaList;

    public FileDownloadHelper(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    class Download extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < mediaList.size(); i++) {
                boolean result = downloadFile(mediaList.get(i).getUrl());
                if (!result)
                    StorageHelper.deleteFile(mediaList.get(i).getUrl());
            }
            return null;
        }

        boolean downloadFile(String url) {
            boolean result = true;
            HttpURLConnection c;
            try {
                c = NetworkHelper.getConnection(url, "GET", 5000);
                c.connect();
                File destFile = StorageHelper.getFile(url);
                InputStream inputStream = c.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bufferLength);
                }
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            } catch (Exception e) {
                result = false;
            }
            return result;
        }
    }
}
