package com.usermanual.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.usermanual.R;
import com.usermanual.fragments.NewsFragment;
import com.usermanual.helper.dbmodels.NewsModel;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private static final String TAG = "NewsAdapter";

    Context context;
    List<NewsModel> newsModelList;
    NewsFragment.NewsClickDelegate delegate;

    public NewsAdapter(Context context, List<NewsModel> newsModelList, NewsFragment.NewsClickDelegate delegate) {
        this.context = context;
        this.newsModelList = newsModelList;
        this.delegate = delegate;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        TextView newsTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            newsImage = (ImageView) itemView.findViewById(R.id.news_image);
            newsTitle = (TextView) itemView.findViewById(R.id.news_title);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.newsTitle.setText(newsModelList.get(position).title);
        //todo show image here
        holder.newsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.onClick(newsModelList.get(position).newsId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }
}
