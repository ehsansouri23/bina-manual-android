package com.usermanual.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermanual.R;
import com.usermanual.viewHolders.DataViewHolder;

public class TicketMessagesAdapter extends RecyclerView.Adapter<DataViewHolder> {
    //todo handle here
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_item, viewGroup, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 50;
    }
}
