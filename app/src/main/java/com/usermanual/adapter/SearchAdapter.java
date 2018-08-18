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
        this.context = context;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        for (int j = 0; j < holder.mainLayout.getChildCount(); j++) {
            holder.mainLayout.getChildAt(j).setVisibility(View.GONE);
        }
        if (searchModelList.get(position).title != null) {
            holder.titleString.setText(searchModelList.get(position).title.title);
            holder.titleString.setVisibility(View.VISIBLE);
            holder.titleString.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            Log.e(TAG, "onBindViewHolder: we have title for search list");
        }
        if (searchModelList.get(position).subtitles != null) { //todo handle click and some buity here
            List<String> subtitlesString = getString(searchModelList.get(position).subtitles);
            for (int i = 0; i < subtitlesString.size(); i++) {
                Log.e(TAG, "onBindViewHolder: " + subtitlesString.get(i));
                TextView subtitleTextView = new TextView(context);
                subtitleTextView.setBackground(context.getResources().getDrawable(R.drawable.gray_ripple_effect));
                subtitleTextView.setPadding(40, 40,40,40);
                subtitleTextView.setText(searchModelList.get(position).subtitles.get(i).subtitle);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 5, 65, 5);
                holder.mainLayout.addView(subtitleTextView, layoutParams);
                final int finalI = i;
                subtitleTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "onClick: " + searchModelList.get(position).subtitles.get(finalI).subtitle);
                    }
                });
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
