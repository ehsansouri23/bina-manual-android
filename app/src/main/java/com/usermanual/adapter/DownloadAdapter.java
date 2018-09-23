package com.usermanual.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.usermanual.R;
import com.usermanual.dbmodels.TableToDownloadFiles;
import com.usermanual.helper.DataBaseHelper;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    Context context;
    List<TableToDownloadFiles> toDownloadFiles;

    public DownloadAdapter(Context context) {
        this.context = context;
        toDownloadFiles = DataBaseHelper.getToDownloadFiles(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button download;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            download = (Button) itemView.findViewById(R.id.download);
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
    public void onBindViewHolder(@NonNull DownloadAdapter.ViewHolder holder, int position) {
        holder.text.setText(toDownloadFiles.get(position).fileKey);
    }

    @Override
    public int getItemCount() {
        return toDownloadFiles.size();
    }
}
