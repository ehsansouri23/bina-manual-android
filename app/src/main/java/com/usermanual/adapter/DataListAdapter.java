package com.usermanual.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.usermanual.ItemClickListener;
import com.usermanual.R;
import com.usermanual.dbmodels.TitleData;
import com.usermanual.helper.StorageHelper;
import com.usermanual.viewHolders.DataViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private static final String TAG = "DataListAdapter";

    Context context;
    List<TitleData> titles = new ArrayList<>();
    ItemClickListener itemClickListener;

    public DataListAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, final int position) {
        holder.dataText.setText(titles.get(position).text);
        Picasso.get().load(StorageHelper.getFile(context, titles.get(position).fileKey)).placeholder(R.mipmap.carr).into(holder.dataImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(view, titles.get(position).id, titles.get(position).fileKey);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public void setTitles(List<TitleData> titles) {
        this.titles = titles;
    }
}
