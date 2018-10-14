package com.usermanual.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.usermanual.R;
import com.usermanual.dbmodels.TableToDownloadFiles;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.StorageHelper;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {
    private static final String TAG = "DownloadAdapter";

    Context context;
    List<TableToDownloadFiles> toDownloadFiles;

    public DownloadAdapter(Context context) {
        this.context = context;
        toDownloadFiles = DataBaseHelper.getToDownloadFiles(context);
        for (int i = 0; i < toDownloadFiles.size(); i++) {
            Log.d(TAG, "file: [" + toDownloadFiles.get(i).fileKey + "]. url: [ " + StorageHelper.getUrl(toDownloadFiles.get(i).fileKey) + " ]");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button download;
        TextView text;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            download = (Button) itemView.findViewById(R.id.download);
            text = (TextView) itemView.findViewById(R.id.text);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
        }
    }

    @NonNull
    @Override
    public DownloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.download_item, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final DownloadAdapter.ViewHolder holder, final int position) {
        holder.text.setText(toDownloadFiles.get(position).fileKey);
        holder.text.setTextColor(Color.BLACK);
        if (PRDownloader.getStatus(DataBaseHelper.getDownloadId(context, toDownloadFiles.get(position).fileKey)) == Status.RUNNING) {
            holder.text.setText("downloading....");
            holder.download.setEnabled(false);
        }
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.text.setText("downloading....");
                holder.download.setEnabled(false);
                final TableToDownloadFiles downloadFiles = toDownloadFiles.get(position);
                int id = PRDownloader.download(StorageHelper.getUrl(downloadFiles.fileKey), context.getDir("app", Context.MODE_PRIVATE).getAbsolutePath(), downloadFiles.fileKey)
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                holder.progressBar.setIndeterminate(false);
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                holder.progressBar.setProgress((int) progressPercent);
                                holder.progressBar.setIndeterminate(false);
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                holder.text.setText("completed. " + downloadFiles.fileKey);
                                holder.download.setEnabled(false);
                                DataBaseHelper.deleteToDownloadFile(context, downloadFiles.fileKey);
                            }

                            @Override
                            public void onError(Error error) {
                                holder.text.setText("Error");
                                holder.text.setTextColor(Color.RED);
                                holder.download.setEnabled(true);
                            }

                        });
                DataBaseHelper.saveDownloadId(context, downloadFiles.fileKey, id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return toDownloadFiles.size();
    }
}
