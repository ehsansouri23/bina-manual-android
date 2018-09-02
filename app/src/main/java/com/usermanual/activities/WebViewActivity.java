package com.usermanual.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.usermanual.R;
import com.usermanual.helper.PrefHelper;
import com.usermanual.helper.dbmodels.FullNewsModel;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    int newsId;
    WebView webView;
    final String mimeType = "text/html";
    final String encoding = "UTF-8";
    String html;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        newsId = getIntent().getIntExtra(PrefHelper.PREF_NEWS_ID, 0);
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        final Call<FullNewsModel> htmlNewsCall = data.getHtmlNews(PrefHelper.FULL_NEWS_URL + newsId);
        htmlNewsCall.enqueue(new Callback<FullNewsModel>() {
            @Override
            public void onResponse(Call<FullNewsModel> call, Response<FullNewsModel> response) {
                html = response.body().htmlText;
                webView = (WebView) findViewById(R.id.webview);
                webView.loadDataWithBaseURL("", html, mimeType, encoding, "");
            }

            @Override
            public void onFailure(Call<FullNewsModel> call, Throwable t) {

            }
        });
    }
}
