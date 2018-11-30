package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.eyalbira.loadingdots.LoadingDots;
import com.usermanual.R;
import com.usermanual.activities.WebViewActivity;
import com.usermanual.adapter.DataListAdapter;
import com.usermanual.adapter.NewsAdapter;
import com.usermanual.auth.Auth;
import com.usermanual.dbmodels.NewsModel;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.usermanual.helper.Consts.NEWS_FULL_HTML;
import static com.usermanual.helper.Consts.NEWS_IMAGE_KEY;

public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";

    @BindView(R.id.loading)
    LoadingDots loading;
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
//        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
//        Call<List<NewsModel>> newsListCall = data.getNewsList(Auth.getToken(getContext()));
//        newsListCall.enqueue(new Callback<List<NewsModel>>() {
//            @Override
//            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
//                if (response == null || response.body() == null) {
//                    loading.setVisibility(View.GONE);
//                    Toast.makeText(getContext(), getResources().getString(R.string.no_item), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                loading.animate().alpha(0).setDuration(200);
//                loading.setVisibility(View.GONE);
//                newsList.setVisibility(View.VISIBLE);
//                List<NewsModel> newsModelList = response.body();
//                newsAdapter = new NewsAdapter(getContext(), newsModelList, new NewsClickDelegate());
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//                newsList.setAdapter(newsAdapter);
//                newsList.setLayoutManager(layoutManager);
//            }
//
//            @Override
//            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
//                loading.setVisibility(View.GONE);
//                noNet.setVisibility(View.VISIBLE);
//            }
//        });

//        DataListAdapter dataListAdapter = new DataListAdapter(getContext(), null, new TitlesFragment.ItemClickListener() {
//            @Override
//            public void ItemClickListener(View v, int position) {
//                Log.e(TAG, "ItemClickListener: position = " + position);
//            }
//        });
//        newsList.setAdapter(dataListAdapter);
//        newsList.showShimmerAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: clicked");
            }
        });
        return view;
    }

    public static NewsFragment newInstance() {
        NewsFragment newsFragment = new NewsFragment();
        return newsFragment;
    }

    public class NewsClickDelegate {
        public void onClick(String fullHtml, String fileKey) {
            Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
            webViewIntent.putExtra(NEWS_FULL_HTML, fullHtml);
            webViewIntent.putExtra(NEWS_IMAGE_KEY, fileKey);
            startActivity(webViewIntent);
        }
    }
}
