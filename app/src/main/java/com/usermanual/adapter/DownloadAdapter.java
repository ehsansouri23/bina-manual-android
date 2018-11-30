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

import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {
    private static final String TAG = "DownloadAdapter";

    Context context;
    List<TableToDownloadFiles> toDownloadFiles = new ArrayList<>();

    public DownloadAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
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
        holder.text.setText(toDownloadFiles.get(position).fileKey.substring(toDownloadFiles.get(position).fileKey.length() - 5));
    }

    @Override
    public int getItemCount() {
        return toDownloadFiles.size();
    }

    public void setToDownloadFiles(List<TableToDownloadFiles> toDownloadFiles) {
        this.toDownloadFiles = toDownloadFiles;
    }
}
