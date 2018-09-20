package com.usermanual.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.usermanual.R;
import com.usermanual.activities.MainActivity;
import com.usermanual.helper.dbmodels.TableToDownloadFiles;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadFile extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "DownloadFile";

    Context context;
    List<StorageHelper.FileSpec> fileSpecs;
    ProgressDialog progressDialog;

    boolean downloadFromDB = false;

    public DownloadFile(Context context, List<StorageHelper.FileSpec> fileSpecs) {
        this.context = context;
        this.fileSpecs = fileSpecs;
        this.progressDialog = new ProgressDialog(context);
    }

    public DownloadFile(Context context) {
        this.context = context;
        downloadFromDB = true;
        fileSpecs = new ArrayList<>();
        List<TableToDownloadFiles> tableToDownloadFiles = DataBaseHelper.getToDownloadFiles(context);
        for (int i = 0; i < tableToDownloadFiles.size(); i++) {
            StorageHelper.FileSpec fileSpec = new StorageHelper.FileSpec(context, tableToDownloadFiles.get(i).fileKey, StorageHelper.FileType.MEDIAS);
            fileSpecs.add(fileSpec);
        }
    }

    @Override
    protected void onPreExecute() {
        if (progressDialog != null) {
            progressDialog.setMessage(context.getResources().getString(R.string.receiving_data));
            if (!progressDialog.isShowing())
                progressDialog.show();
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        final boolean[] success = {true};
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        for (int i = 0; i < fileSpecs.size(); i++) {
            if (fileSpecs.get(i).getFile().exists())
                continue;
            Call<ResponseBody> call = data.downloadFile(fileSpecs.get(i).getUrl());
            final int finalI = i;
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    success[0] = writeResponseBodyToDisk(response.body(), fileSpecs.get(finalI).getFile());
                    if (downloadFromDB)
                        DataBaseHelper.deleteToDownlaodFile(context, fileSpecs.get(finalI).getFileKey());
                    if (finalI == fileSpecs.size() - 1) {
                        progressDialog.dismiss();
//                        if (delegate != null)
//                            delegate.finished(success[0]);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, context.getResources().getString(R.string.receiving_data_failed), Toast.LENGTH_SHORT).show();
                    success[0] = false;
                    if (finalI == fileSpecs.size() - 1) {
                        progressDialog.dismiss();
//                        if (delegate != null)
//                            delegate.finished(success[0]);
                    }
                }
            });
        }
        return true;
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, File file) {
        try {
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
