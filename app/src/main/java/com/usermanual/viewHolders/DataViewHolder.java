package com.usermanual.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.usermanual.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.data_image)
    public ImageView dataImage;
    @BindView(R.id.data_text)
    public TextView dataText;

    public DataViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
