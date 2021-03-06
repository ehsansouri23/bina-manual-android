package com.usermanual.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.dbmodels.TicketMessageModel;
import com.usermanual.fragments.MediaFragment;
import com.usermanual.helper.Consts;
import com.usermanual.helper.StorageHelper;
import com.usermanual.viewHolders.DataViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TicketMessagesAdapter extends RecyclerView.Adapter<DataViewHolder> {

    Context context;
    List<TicketMessageModel> messageModels = new ArrayList<>();
    MediaFragment.OnClick onClick;

    public TicketMessagesAdapter(Context context, MediaFragment.OnClick onClick) {
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_item, viewGroup, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i) {
        if (!messageModels.get(i).message.equals("")) {
            dataViewHolder.dataText.setVisibility(View.VISIBLE);
            dataViewHolder.dataText.setText(messageModels.get(i).message);
        } else dataViewHolder.dataText.setVisibility(View.GONE);

        if (messageModels.get(i).url != null && !messageModels.get(i).url.equals("")) {
            dataViewHolder.dataImage.setVisibility(View.VISIBLE);
            if (messageModels.get(i).type == Consts.IMAGE)//todo add complete url here
                Picasso.get().load(StorageHelper.getUrl(messageModels.get(i).url)).placeholder(R.mipmap.not_downloaded).into(dataViewHolder.dataImage);
            else if (messageModels.get(i).type == Consts.VIDEO)
                Picasso.get().load(R.mipmap.play).into(dataViewHolder.dataImage);

            dataViewHolder.dataImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onClick(messageModels.get(i).type, messageModels.get(i).url);
                }
            });
        } else dataViewHolder.dataImage.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public void setMessageModels(List<TicketMessageModel> messageModels) {
        this.messageModels = messageModels;
    }
}
