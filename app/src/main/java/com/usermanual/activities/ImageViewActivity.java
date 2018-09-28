package com.usermanual.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.helper.StorageHelper;

import static com.usermanual.helper.Consts.FILE_KEY;

public class ImageViewActivity extends AppCompatActivity {
    private static final String TAG = "ImageViewActivity";

    String fileKey;
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        image = (ImageView) findViewById(R.id.image);

        fileKey = getIntent().getExtras().getString(FILE_KEY);
        Picasso.get().load(StorageHelper.getFile(getApplicationContext(), fileKey)).into(image);
    }
}
