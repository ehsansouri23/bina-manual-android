package com.usermanual.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.usermanual.R;

public class DataViewHolder extends RecyclerView.ViewHolder {

    public ImageView dataImage;
    public TextView dataText;

    public DataViewHolder(View itemView) {
        super(itemView);
        dataImage = (ImageView) itemView.findViewById(R.id.data_image);
        dataText = (TextView) itemView.findViewById(R.id.data_text);
    }
}
