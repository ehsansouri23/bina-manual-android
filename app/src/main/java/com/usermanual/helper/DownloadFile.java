package com.usermanual.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.usermanual.R;
import com.usermanual.activities.MainActivity;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadFile extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "DownloadFile";

    List<String> urls;
    List<String> fileNames;
    Context context;
    ProgressDialog progressDialog;
    MainActivity.DownFilesDelegate delegate;

    public DownloadFile(List<String> urls, List<String> fileNames, Context context, MainActivity.DownFilesDelegate downFilesDelegate) {
        this.urls = urls;
        this.fileNames = fileNames;
        this.context = context;
        this.delegate = downFilesDelegate;
        this.progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setMessage(context.getResources().getString(R.string.receiving_data));
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        final boolean[] success = {true};
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        for (int i = 0; i < urls.size(); i++) {
            Call<ResponseBody> call = data.downloadFile(urls.get(i));
            final int finalI = i;
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    success[0] = writeResponseBodyToDisk(response.body(), fileNames.get(finalI));
                    if (finalI == urls.size() - 1) {
                        progressDialog.dismiss();
                        delegate.finished(success[0]);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, context.getResources().getString(R.string.receiving_data_failed), Toast.LENGTH_SHORT).show();
                    success[0] = false;
                    if (finalI == urls.size() - 1) {
                        progressDialog.dismiss();
                        delegate.finished(success[0]);
                    }
                }
            });
        }
        return true;
    }

    private boolean writeResponseBodyToDisk(ResponseBody body,  String fileName) {
        try {
            File file = new File(StorageHelper.getDir(context), fileName);
            if (!file.exists())
                file.mkdirs();

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
