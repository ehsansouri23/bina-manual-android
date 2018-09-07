package com.usermanual.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.usermanual.fragments.MediaFragment;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.dbmodels.TableMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.usermanual.helper.PrefHelper.MEDIA_KEY;

public class MediaFragmentsAdapter extends FragmentPagerAdapter {

    Context context;
    int subtitleId;
    List<TableMedia> tableMediaList;
    HashMap<Integer, List<TableMedia>> mediasMap;

    public MediaFragmentsAdapter(Context context, FragmentManager fm, int subtitleId) {
        super(fm);
        this.context = context;
        this.subtitleId = subtitleId;
        tableMediaList = DataBaseHelper.getMediaList(context, subtitleId);
        mediasMap = prepareMedias(tableMediaList);
    }

    @Override
    public Fragment getItem(int position) {
        MediaFragment mediaFragment = new MediaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MEDIA_KEY, (Parcelable) mediasMap.get(position));
        mediaFragment.setArguments(bundle);
        return mediaFragment;
    }

    @Override
    public int getCount() {
        return mediasMap.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mediasMap.get(position).get(0).title;
    }

    private HashMap<Integer, List<TableMedia>> prepareMedias(List<TableMedia> tableMediaList) { //todo check it
        HashMap<Integer, List<TableMedia>> mediasMap = new HashMap<>();
        String title = tableMediaList.get(0).title;
        List<TableMedia> tableMedia = new ArrayList<>();
        tableMedia.add(tableMediaList.get(0));
        int index = 0;
        if (tableMedia.size() == 1)
            mediasMap.put(index, tableMediaList);
        for (int i = 1; i < tableMediaList.size(); i++) {
            if (tableMediaList.get(i).title.equals(title)) {
                tableMedia.add(tableMediaList.get(i));
            } else {
                mediasMap.put(index, tableMedia);
                index++;
                tableMedia.clear();
                tableMedia.add(tableMediaList.get(i));
            }
        }
        return mediasMap;
    }
}
