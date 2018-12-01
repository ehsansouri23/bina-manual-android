package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.usermanual.R;
import com.usermanual.activities.NewsActivity;
import com.usermanual.adapter.NewsAdapter;
import com.usermanual.auth.Auth;
import com.usermanual.dbmodels.NewsModel;
import com.usermanual.helper.Consts;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";

    @BindView(R.id.no_net)
    LinearLayout noNet;
    @BindView(R.id.news_list)
    ShimmerRecyclerView newsList;
    NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        ButterKnife.bind(this, view);
        newsAdapter = new NewsAdapter(getContext(), new OnClick() {
            @Override
            public void onClick(String title, String txt, String fileKey) {
                Intent newsActivity = new Intent(getActivity(), NewsActivity.class);
                newsActivity.putExtra(Consts.NEWS_IMAGE_KEY, fileKey);
                newsActivity.putExtra(Consts.NEWS_TEXT, txt);
                newsActivity.putExtra(Consts.NEWS_TITLE, title);
                startActivity(newsActivity);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        newsList.setAdapter(newsAdapter);
        newsList.setLayoutManager(layoutManager);
        newsList.showShimmerAdapter();
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<NewsModel>> newsListCall = data.getNewsList(Auth.getToken(getContext()));
        newsListCall.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if (response == null || response.body() == null) {
                    Toast.makeText(getContext(), getResources().getString(R.string.no_item), Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e(TAG, "onResponse: success");
                List<NewsModel> newsModelList = response.body();
                newsAdapter.setNewsModelList(newsModelList);
                newsList.hideShimmerAdapter();
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                noNet.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    public static NewsFragment newInstance() {
        NewsFragment newsFragment = new NewsFragment();
        return newsFragment;
    }

    public interface OnClick {
        void onClick(String title, String txt, String fileKey);
    }
}
