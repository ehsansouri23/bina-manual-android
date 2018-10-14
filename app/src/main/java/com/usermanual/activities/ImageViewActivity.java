package com.usermanual.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.usermanual.R;
import com.usermanual.helper.StorageHelper;

import static com.usermanual.helper.Consts.FILE_KEY;

public class ImageViewActivity extends AppCompatActivity {
    private static final String TAG = "ImageViewActivity";

    Context context;
    String fileKey;
    WebView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        context = getApplicationContext();
        image = (WebView) findViewById(R.id.image);

        fileKey = getIntent().getExtras().getString(FILE_KEY);
        Uri imageUri = Uri.fromFile(StorageHelper.getFile(context, fileKey));
        image.getSettings().setBuiltInZoomControls(true);
        image.loadUrl(imageUri.toString());
    }
}
