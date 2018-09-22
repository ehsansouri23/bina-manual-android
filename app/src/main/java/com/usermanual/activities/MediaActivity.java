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
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.adapter.MediaFragmentsAdapter;
import com.usermanual.dbmodels.TableSubTitle;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.StorageHelper;

import static com.usermanual.helper.Consts.PREF_SUBTITLE_ID;

public class MediaActivity extends AppCompatActivity {
    private static final String TAG = "MediaActivity";

    ImageView headerImage;
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

        headerImage = (ImageView) findViewById(R.id.header_image);
        tabLayout = (TabLayout) findViewById(R.id.media_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        subtitleId = getIntent().getIntExtra(PREF_SUBTITLE_ID, -1);
        TableSubTitle tableSubTitle = DataBaseHelper.getSubtitle(getApplicationContext(), subtitleId);
        if (tableSubTitle != null) {
            StorageHelper.FileSpec imageFile = new StorageHelper.FileSpec(getApplicationContext(), tableSubTitle.picUrl, StorageHelper.FileType.SUBTITLES);
            Picasso.get().load(imageFile.getFile()).placeholder(R.mipmap.car).into(headerImage);
        }

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
            DataBaseHelper.saveFav(getApplicationContext(), subtitleId);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.added_to_favs), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
