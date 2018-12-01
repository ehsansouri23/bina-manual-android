package com.usermanual.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.fragments.NewsFragment;
import com.usermanual.dbmodels.NewsModel;
import com.usermanual.helper.StorageHelper;
import com.usermanual.viewHolders.DataViewHolder;

import java.util.List;

import static com.usermanual.helper.Consts.*;

public class NewsAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private static final String TAG = "NewsAdapter";

    Context context;
    List<NewsModel> newsModelList;
    NewsFragment.OnClick onClick;

    public NewsAdapter(Context context, NewsFragment.OnClick onClick) {
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i) {
        if (!newsModelList.get(i).title.equals(""))
            dataViewHolder.dataText.setText(newsModelList.get(i).title);
        Picasso.get().load(StorageHelper.getUrl(newsModelList.get(i).fileKey)).placeholder(R.mipmap.new_place).into(dataViewHolder.dataImage);
        dataViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(newsModelList.get(i).title, newsModelList.get(i).text, newsModelList.get(i).fileKey);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }

    public void setNewsModelList(List<NewsModel> newsModelList) {
        this.newsModelList = newsModelList;
    }
}
