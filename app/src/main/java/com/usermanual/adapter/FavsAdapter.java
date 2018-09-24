package com.usermanual.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.activities.MediaActivity;
import com.usermanual.dbmodels.Favs;
import com.usermanual.dbmodels.TableSubTitle;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.StorageHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.usermanual.helper.Consts.PREF_SUBTITLE_ID;

public class FavsAdapter extends RecyclerView.Adapter<FavsAdapter.ViewHolder> {

    Context context;
    List<Favs> favsList;
    List<TableSubTitle> tableSubTitleList;

    public FavsAdapter(Context context) {
        this.context = context;
        favsList = DataBaseHelper.getAllFavs(context);
        tableSubTitleList = new ArrayList<>();
        for (int i = 0; i < favsList.size(); i++) {
            tableSubTitleList.add(DataBaseHelper.getSubtitle(context, favsList.get(i).subtitleId));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.text.setText(tableSubTitleList.get(position).subtitle);
        File imageFile = StorageHelper.getFile(context, tableSubTitleList.get(position).fileKey);
        Picasso.get().load(imageFile).placeholder(R.mipmap.car).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MediaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(PREF_SUBTITLE_ID, tableSubTitleList.get(position).subtitleId);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.title_text);
        }
    }

}
