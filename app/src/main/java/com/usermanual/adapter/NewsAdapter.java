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

import java.util.List;

import static com.usermanual.helper.Consts.*;

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
        if (!newsModelList.get(position).title.equals(""))
            holder.newsTitle.setText(newsModelList.get(position).title);
        String imageUrl = BASE_URL + newsModelList.get(position).picUrl;
        Picasso.get().load(imageUrl).placeholder(R.mipmap.new_place).into(holder.newsImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.onClick(newsModelList.get(position).fullHtml, newsModelList.get(position).picUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }
}
