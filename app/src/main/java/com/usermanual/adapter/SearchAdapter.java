package com.usermanual.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.usermanual.R;
import com.usermanual.activities.MediaActivity;
import com.usermanual.fragments.SearchFragment;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;

import java.util.List;

import static com.usermanual.helper.Consts.PREF_SUBTITLE_ID;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private static final String TAG = "SearchAdapter";

    Context context;

    String query;
    SearchFragment.SearchDelegate searchDelegate;

    List<TableTitle> tableTitles;
    List<TableSubTitle> tableSubTitles;

    public SearchAdapter(Context context, String query, SearchFragment.SearchDelegate SearchDelegate) {
        this.context = context;
        this.query = query;
        this.searchDelegate = SearchDelegate;
        tableTitles = DataBaseHelper.searchTitles(context, query);
        tableSubTitles = DataBaseHelper.searchSubtitles(context, query);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView image;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
//            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.title_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (position < tableTitles.size()) {
            holder.text.setText(tableTitles.get(position).title);
//            Picasso.get().load(R.mipmap.car).into(holder.image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchDelegate.clicked(tableTitles.get(position).titleId);
                }
            });
        } else if (position >= tableTitles.size()) {
            final int pos = position - tableTitles.size();
            holder.text.setText(tableSubTitles.get(pos).subtitle);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MediaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(PREF_SUBTITLE_ID, tableSubTitles.get(pos).subtitleId);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return tableTitles.size() + tableSubTitles.size();
    }
}
