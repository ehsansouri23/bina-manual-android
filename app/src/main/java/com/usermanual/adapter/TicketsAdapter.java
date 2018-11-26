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
import com.usermanual.dbmodels.Ticket;
import com.usermanual.viewHolders.DataViewHolder;

import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private static final String TAG = "TicketsAdapter";

    Context context;
    List<Ticket> tickets;
    ItemClickListener itemClickListener;

    public TicketsAdapter(Context context, List<Ticket> tickets, ItemClickListener itemClickListener) {
        this.context = context;
        this.tickets = tickets;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item, parent, false);
        return new DataViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if (tickets.get(position).isDone == 0)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tickets.get(position).isDone == 0)
                        itemClickListener.onItemClick(view, tickets.get(position).id, "");
                }
            });

        holder.dataText.setText(tickets.get(position).ticketName);
        if (tickets.get(position).isDone == 0)
            Picasso.get().load(R.mipmap.ticket_open).into(holder.dataImage);
        else
            Picasso.get().load(R.mipmap.ticket_closed).into(holder.dataImage);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }
}
