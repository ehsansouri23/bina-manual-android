package com.usermanual.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.usermanual.R;
import com.usermanual.adapter.MediaFragmentsAdapter;
import com.usermanual.helper.PrefHelper;

public class MediaActivity extends AppCompatActivity {
    private static final String TAG = "MediaActivity";

    TabLayout tabLayout;
    ViewPager viewPager;
    int subtitleId;
    MediaFragmentsAdapter mediaFragmentsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(android.R.color.transparent));

        tabLayout = (TabLayout) findViewById(R.id.media_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        subtitleId = getIntent().getIntExtra(PrefHelper.PREF_SUBTITLE_ID, -1);

        mediaFragmentsAdapter = new MediaFragmentsAdapter(getApplicationContext(), getSupportFragmentManager(), subtitleId);
        viewPager.setAdapter(mediaFragmentsAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.media_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        if (item.getItemId() == R.id.id_favs) {
            String s = PrefHelper.getString(getApplicationContext(), PrefHelper.PREF_FAV_SUBTITLES, "");
            if (!s.equals(""))
                s += ",";
            s += subtitleId;
            PrefHelper.saveString(getApplicationContext(),PrefHelper.PREF_FAV_SUBTITLES, s);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.added_to_favs), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
