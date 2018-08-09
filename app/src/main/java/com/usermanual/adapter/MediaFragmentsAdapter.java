package com.usermanual.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.usermanual.fragments.MediaFragment;
import com.usermanual.helper.dbmodels.TableMedia;

import java.util.List;

import static com.usermanual.helper.PrefHelper.MEDIA_KEY;

public class MediaFragmentsAdapter extends FragmentPagerAdapter {

    Context context;
    List<TableMedia> mediaList;

    public MediaFragmentsAdapter(Context context, FragmentManager fm, List<TableMedia> mediaList) {
        super(fm);
        this.context = context;
        this.mediaList = mediaList;
    }

    @Override
    public Fragment getItem(int position) {
        MediaFragment mediaFragment = new MediaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MEDIA_KEY, mediaList.get(position));
        mediaFragment.setArguments(bundle);
        return mediaFragment;
    }

    @Override
    public int getCount() {
        return mediaList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mediaList.get(position).mediaText;
    }
}
