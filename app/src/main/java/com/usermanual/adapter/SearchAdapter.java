package com.usermanual.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.usermanual.R;
import com.usermanual.helper.dbmodels.SearchModel;
import com.usermanual.helper.dbmodels.TableSubTitle;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private static final String TAG = "SearchAdapter";

    Context context;

    List<SearchModel> searchModelList;

    ArrayAdapter<String> adapter;

    public SearchAdapter(Context context, List<SearchModel> searchModelList) {
        this.searchModelList = searchModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleString;
        LinearLayout mainLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            titleString = (TextView) itemView.findViewById(R.id.title_text);
            mainLayout = (LinearLayout) itemView.findViewById(R.id.main_layout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        for (int j = 0; j < holder.mainLayout.getChildCount(); j++) {
            holder.mainLayout.getChildAt(j).setVisibility(View.GONE);
        }
        if (searchModelList.get(position).title != null) {
            holder.titleString.setText(searchModelList.get(position).title.title);
            holder.titleString.setVisibility(View.VISIBLE);
            Log.e(TAG, "onBindViewHolder: we have title for search list");
        }
        if (searchModelList.get(position).subtitles != null) { //todo handle click and some buity here
            List<String> subtitlesString = getString(searchModelList.get(position).subtitles);
            for (int i = 0; i < subtitlesString.size(); i++) {
                Log.e(TAG, "onBindViewHolder: " + subtitlesString.get(i));
                TextView subtitleTextView = new TextView(holder.itemView.getContext());
                subtitleTextView.setText(subtitlesString.get(i));
                holder.mainLayout.addView(subtitleTextView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return searchModelList.size();
    }

    private List<String> getString(List<TableSubTitle> tableSubTitles) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < tableSubTitles.size(); i++) {
            strings.add(tableSubTitles.get(i).subtitle);
        }
        return strings;
    }
}
