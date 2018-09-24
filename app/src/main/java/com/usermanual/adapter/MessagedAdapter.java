package com.usermanual.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.activities.VideoViewActivity;
import com.usermanual.dbmodels.Message;
import com.usermanual.helper.Consts;
import com.usermanual.helper.StorageHelper;

import java.util.List;

import static com.usermanual.helper.Consts.VIDEO_FILE_KEY;
import static com.usermanual.helper.Consts.VIDEO_URL;

public class MessagedAdapter extends RecyclerView.Adapter<MessagedAdapter.ViewHolder> {

    Context context;
    List<Message> messageList;

    MessagedAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            image = (ImageView) itemView.findViewById(R.id.image);
            date = (TextView) itemView.findViewById(R.id.date);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.text.setText(messageList.get(position).text);
        holder.date.setText(messageList.get(position).created.substring(0,  9));
        if (StorageHelper.getFileType(messageList.get(position).fileType) == Consts.IMAGE) {
            Picasso.get().load(StorageHelper.getUrl(messageList.get(position).fileKey)).placeholder(R.mipmap.car).into(holder.image);
        }
        else if (StorageHelper.getFileType(messageList.get(position).fileType) == Consts.VIDEO) {
            Picasso.get().load(R.mipmap.ic_launcher).into(holder.image);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VideoViewActivity.class);
                    intent.putExtra(VIDEO_URL, StorageHelper.getUrl(messageList.get(position).fileKey));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

}
