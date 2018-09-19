package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eyalbira.loadingdots.LoadingDots;
import com.usermanual.R;
import com.usermanual.activities.WebViewActivity;
import com.usermanual.adapter.NewsAdapter;
import com.usermanual.auth.Auth;
import com.usermanual.helper.PrefHelper;
import com.usermanual.helper.dbmodels.NewsModel;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.usermanual.helper.Consts.*;

public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";

    LoadingDots loading;
    LinearLayout noNet;
    RecyclerView newsList;
    NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_fragment, container, false);
        loading = (LoadingDots) v.findViewById(R.id.loading);
        noNet = (LinearLayout) v.findViewById(R.id.no_net);
        newsList = (RecyclerView) v.findViewById(R.id.news_list);
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<NewsModel>> newsListCall = data.getNewsList(Auth.getToken(getContext()));
        newsListCall.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                loading.animate().alpha(0).setDuration(200);
                loading.setVisibility(View.GONE);
                newsList.setVisibility(View.VISIBLE);
                List<NewsModel> newsModelList = response.body();
                for (int i = 0; i < newsModelList.size(); i++) {
                    Log.e(TAG, "onResponse: " + newsModelList.get(i).fullHtml);
                }
                newsAdapter = new NewsAdapter(getContext(), newsModelList, new NewsClickDelegate());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                newsList.setAdapter(newsAdapter);
                newsList.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                noNet.setVisibility(View.VISIBLE);
            }
        });


        return v;
    }

    public class NewsClickDelegate {
        public void onClick(String fullHtml) {
            Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
            webViewIntent.putExtra(NEWS_FULL_HTML, fullHtml);
            startActivity(webViewIntent);
        }
    }
}
