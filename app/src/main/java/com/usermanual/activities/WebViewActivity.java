package com.usermanual.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.usermanual.R;

import static com.usermanual.helper.Consts.*;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    String fullHtml;
    String imageKey;
    WebView webView;
    ImageView image;
    final String mimeType = "text/html";
    final String encoding = "UTF-8";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        image = (ImageView) findViewById(R.id.news_image);
        fullHtml = getIntent().getStringExtra(NEWS_FULL_HTML);
        imageKey = getIntent().getStringExtra(NEWS_IMAGE_KEY);
        Picasso.get().load(BASE_URL + FILE_URL + imageKey).placeholder(R.mipmap.new_place).into(image);
        webView = (WebView) findViewById(R.id.webview);
        webView.loadDataWithBaseURL("", fullHtml, mimeType, encoding, "");
    }

}
