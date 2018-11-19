package com.usermanual.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermanual.R;
import com.usermanual.dbmodels.Ticket;
import com.usermanual.viewHolders.DataViewHolder;

import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private static final String TAG = "TicketsAdapter";

    Context context;
    List<Ticket> tickets;
    public TicketsAdapter(Context context, List<Ticket> tickets) {
        this.context = context;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        return new DataViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
//todo handle here
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }
}
