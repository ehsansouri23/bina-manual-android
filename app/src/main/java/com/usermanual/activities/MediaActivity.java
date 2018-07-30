package com.usermanual.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.usermanual.R;
import com.usermanual.adapter.MediaFragmentsAdapter;
import com.usermanual.helper.PrefHelper;
import com.usermanual.model.Media;

import java.util.List;

import static com.usermanual.helper.PrefHelper.MEDIA_KEY;

public class MediaActivity extends AppCompatActivity {
    private static final String TAG = "MediaActivity";

    TabLayout tabLayout;
    ViewPager viewPager;
    List<Media> mediaList;
    MediaFragmentsAdapter mediaFragmentsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        tabLayout = (TabLayout) findViewById(R.id.media_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        mediaList = getIntent().getExtras().getParcelableArrayList(MEDIA_KEY);

        mediaFragmentsAdapter = new MediaFragmentsAdapter(getApplicationContext(), getSupportFragmentManager(), mediaList);
        viewPager.setAdapter(mediaFragmentsAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
