package com.usermanual.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.usermanual.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubmediaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text)
    public TextView text;
    @BindView(R.id.pic)
    public ImageView pic;
    @BindView(R.id.caption)
    public TextView caption;

    public SubmediaViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
