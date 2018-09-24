package com.usermanual.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.usermanual.R;
import com.usermanual.dbmodels.TableToDownloadFiles;
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

    Context context;
    List<TableToDownloadFiles> toDownloadFiles;
    ProgressDialog progressDialog;

    public DownloadFile(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        toDownloadFiles = DataBaseHelper.getToDownloadFiles(context);
        printDownloadList(toDownloadFiles);
    }

    private void printDownloadList(List<TableToDownloadFiles> tableToDownloadFilesList) {
        for (int i = 0; i < tableToDownloadFilesList.size(); i++) {
            Log.d(TAG, "To Download list: key: [ " + tableToDownloadFilesList.get(i).fileKey + " ] " + " fileKey:[ " + StorageHelper.getUrl(tableToDownloadFilesList.get(i).fileKey) + " ]");
        }
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        final boolean[] success = {true};
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        for (int i = 0; i < toDownloadFiles.size(); i++) {
            final File file = StorageHelper.getFile(context, toDownloadFiles.get(i).fileKey);
            if (file.exists()) {
                Log.e(TAG, "file: " + file.getAbsolutePath() + " exists. not downloading");
                continue;
            }
            Call<ResponseBody> downloadCall = data.downloadFile(StorageHelper.getUrl(toDownloadFiles.get(i).fileKey));
            final int finalI = i;
            downloadCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.body() == null) {
                        Log.e(TAG, "file [ " + file.getAbsolutePath() + " ] cannot download");
                    }
                    else {
                        String fileType = response.headers().get("Content-Type");
                        Log.d(TAG, "file [ " + file.getAbsolutePath() + " ]. fileType [ " + fileType + " ]");
                        success[0] = writeResponseBodyToDisk(response.body(), StorageHelper.getFile(context, toDownloadFiles.get(finalI).fileKey));
                        Log.d(TAG, "download of file [ " + file.getAbsolutePath() + " ]. result [ " + success[0] + " ]");
                        if (success[0]) {
                            DataBaseHelper.saveFileType(context, toDownloadFiles.get(finalI).fileKey, StorageHelper.getFileType(fileType));
                            DataBaseHelper.deleteToDownloadFile(context, toDownloadFiles.get(finalI).fileKey);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, context.getResources().getString(R.string.receiving_data_failed), Toast.LENGTH_SHORT).show();
                    success[0] = false;
                }
            });
        }
        return true;
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, File file) {
        try {
//            if (!file.exists())
//                file.mkdirs();

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                Log.e(TAG, "writeResponseBodyToDisk: filesize: " + fileSize);
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
            e.printStackTrace();
            return false;
        }
    }
}
