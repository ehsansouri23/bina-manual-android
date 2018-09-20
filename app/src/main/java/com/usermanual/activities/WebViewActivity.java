package com.usermanual.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.usermanual.R;

import static com.usermanual.helper.Consts.*;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    String fullHtml;
    WebView webView;
    final String mimeType = "text/html";
    final String encoding = "UTF-8";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        fullHtml = getIntent().getStringExtra(NEWS_FULL_HTML);
        webView = (WebView) findViewById(R.id.webview);
        webView.loadDataWithBaseURL("", fullHtml, mimeType, encoding, "");
    }

}
