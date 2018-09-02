package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermanual.R;
import com.usermanual.activities.WebViewActivity;
import com.usermanual.adapter.NewsAdapter;
import com.usermanual.helper.PrefHelper;
import com.usermanual.helper.dbmodels.NewsModel;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";

    RecyclerView newsList;
    NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_fragment, container, false);
        newsList = (RecyclerView) v.findViewById(R.id.news_list);
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<NewsModel>> newsListCall = data.getNewsList();
        newsListCall.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                List<NewsModel> newsModelList = response.body();
                newsAdapter = new NewsAdapter(getContext(), newsModelList, new NewsClickDelegate());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                newsList.setAdapter(newsAdapter);
                newsList.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {

            }
        });


        return v;
    }

    public class NewsClickDelegate {
        public void onClick(int newsId) {
            Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
            webViewIntent.putExtra(PrefHelper.PREF_NEWS_ID, newsId);
            startActivity(webViewIntent);
        }
    }
}
