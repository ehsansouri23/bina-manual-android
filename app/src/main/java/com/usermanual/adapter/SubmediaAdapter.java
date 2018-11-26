package com.usermanual.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.activities.ImageViewActivity;
import com.usermanual.dbmodels.TableSubMedia;
import com.usermanual.fragments.MediaFragment;
import com.usermanual.helper.Consts;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.StorageHelper;
import com.usermanual.viewHolders.SubmediaViewHolder;

import java.util.List;

public class SubmediaAdapter extends RecyclerView.Adapter<SubmediaViewHolder> {

    private Context context;
    List<TableSubMedia> submedias;
    MediaFragment.OnClick onClick;

    public SubmediaAdapter(Context context, List<TableSubMedia> submedias, MediaFragment.OnClick onClick) {
        this.context = context;
        this.submedias = submedias;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public SubmediaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.submedia_item, viewGroup, false);
        return new SubmediaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubmediaViewHolder submediaViewHolder, int i) {
        submediaViewHolder.text.setText(submedias.get(i).text);
        if (!submedias.get(i).fileKey.equals("")) {
            submediaViewHolder.pic.setVisibility(View.VISIBLE);
            if (submedias.get(i).fileType == Consts.IMAGE) {
                Picasso.get().load(StorageHelper.getFile(context, submedias.get(i).fileKey)).into(submediaViewHolder.pic);
                submediaViewHolder.pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClick.onClick(Consts.IMAGE, submedias.get(i).fileKey);
                    }
                });
            }
            if (submedias.get(i).fileType == Consts.VIDEO) {
                if (DataBaseHelper.isFileDownloaded(context, submedias.get(i).fileKey)) {
                    Picasso.get().load(R.mipmap.play).into(submediaViewHolder.pic);
                    submediaViewHolder.pic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onClick.onClick(Consts.VIDEO, submedias.get(i).fileKey);
                        }
                    });
                } else Picasso.get().load(R.mipmap.not_downloaded).into(submediaViewHolder.pic);
            }
        } else submediaViewHolder.pic.setVisibility(View.GONE);
        if (!submedias.get(i).caption.equals("")) {
            submediaViewHolder.caption.setVisibility(View.VISIBLE);
            submediaViewHolder.caption.setText(submedias.get(i).caption);
        } else submediaViewHolder.caption.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return submedias.size();
    }
}
