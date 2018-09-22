package com.usermanual.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.usermanual.dbmodels.Message;

import java.util.List;

public class MessagedAdapter {

    Context context;
    List<Message> messageList;

    MessagedAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {

//        TextView

        public ViewHoder(View itemView) {
            super(itemView);
        }

    }

}
