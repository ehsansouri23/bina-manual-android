package com.usermanual.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.usermanual.fragments.MediaFragment;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.dbmodels.TableMedia;

import java.util.List;

public class MediaFragmentsAdapter extends FragmentPagerAdapter {

    Context context;
    int subtitleId;
    List<TableMedia> tableMediaList;

    public MediaFragmentsAdapter(Context context, FragmentManager fm, int subtitleId) {
        super(fm);
        this.context = context;
        this.subtitleId = subtitleId;
        tableMediaList = DataBaseHelper.getMediaList(context, subtitleId);
    }

    @Override
    public Fragment getItem(int position) {
        return MediaFragment.newInstance(tableMediaList.get(position).mediaId);
    }

    @Override
    public int getCount() {
        return tableMediaList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tableMediaList.get(position).mediaTitle;
    }
}
