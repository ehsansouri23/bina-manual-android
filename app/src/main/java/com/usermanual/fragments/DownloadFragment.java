package com.usermanual.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;
import com.usermanual.R;
import com.usermanual.adapter.DownloadAdapter;
import com.usermanual.dbmodels.TableToDownloadFiles;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.StorageHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadFragment extends Fragment {
    private static final String TAG = "DownloadFragment";

    LinearLayout noItems;
    RecyclerView list;
    Button downloadAll;
    DownloadAdapter downloadAdapter;
    List<TableToDownloadFiles> toDownloadFilesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        noItems = (LinearLayout) view.findViewById(R.id.no_item);
        downloadAll = (Button) view.findViewById(R.id.download_all);
        downloadAll.setVisibility(View.VISIBLE);
        list = (RecyclerView) view.findViewById(R.id.list);

        if (DataBaseHelper.getToDownloadFiles(getContext()).isEmpty()) {
            noItems.setVisibility(View.VISIBLE);
            return view;
        } else
            noItems.setVisibility(View.GONE);

        toDownloadFilesList = DataBaseHelper.getToDownloadFiles(getContext());
        for (int i = 0; i < toDownloadFilesList.size(); i++) {
            Log.d(TAG, "to download files: " + toDownloadFilesList.get(i));
        }
        downloadAdapter = new DownloadAdapter(getContext());
        downloadAdapter.setToDownloadFiles(toDownloadFilesList);
        list.setAdapter(downloadAdapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        FileDownloader.setup(getContext());

        final FileDownloadListener queueTarget = new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.e(TAG, "pending: " + task.getUrl());
            }

            @Override
            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                Log.e(TAG, "connected: " + task.getUrl());
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.e(TAG, "progress: " + task.getUrl());
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
                Log.e(TAG, "blockComplete: " + task.getUrl());
            }

            @Override
            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                Log.e(TAG, "retry: " + task.getUrl());
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.e(TAG, "completed: " + task.getUrl().substring(22));
                DataBaseHelper.fileDownloaded(getContext(), task.getUrl().substring(22));
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.e(TAG, "paused: " + task.getUrl());
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Log.e(TAG, "error: " + task.getUrl(), e);
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                Log.e(TAG, "warn: " + task.getUrl());
            }
        };
        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(queueTarget);
        final List<BaseDownloadTask> tasks = new ArrayList<>();
        for (int i = 0; i < toDownloadFilesList.size(); i++) {
            TableToDownloadFiles file = toDownloadFilesList.get(i);
            File baseDir = StorageHelper.getBaseFile(getContext());
            File f = StorageHelper.getFile(getContext(), file.fileKey);
            tasks.add(FileDownloader.getImpl().create(StorageHelper.getUrl(file.fileKey)).setPath(f.getAbsolutePath()).setTag(i + 1));
        }
        queueSet.disableCallbackProgressTimes();
        queueSet.setAutoRetryTimes(2);
        queueSet.downloadSequentially(tasks);

        downloadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: started");
                queueSet.start();
//                tasks.get(i).
                for (int i = 0; i < tasks.size(); i++) {
                    Log.e(TAG, "onClick: " + tasks.get(i).getUrl());
                }
            }
        });

        return view;
    }
}
