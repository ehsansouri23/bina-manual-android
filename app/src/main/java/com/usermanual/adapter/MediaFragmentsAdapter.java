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

import static com.usermanual.helper.Consts.*;

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

//    private HashMap<Integer, List<TableMedia>> prepareMedias(List<TableMedia> tableMediaList) {
//        HashMap<Integer, List<TableMedia>> mediasMap = new HashMap<>();
//        String title = tableMediaList.get(0).title;
//        List<TableMedia> tableMedia = new ArrayList<>();
//        tableMedia.add(tableMediaList.get(0));
//        int index = 0;
//        if (tableMedia.size() == 1)
//            mediasMap.put(index, tableMediaList);
//        for (int i = 1; i < tableMediaList.size(); i++) {
//            if (tableMediaList.get(i).title.equals(title)) {
//                tableMedia.add(tableMediaList.get(i));
//            } else {
//                mediasMap.put(index, tableMedia);
//                index++;
//                tableMedia.clear();
//                tableMedia.add(tableMediaList.get(i));
//            }
//        }
//        return mediasMap;
//    }
}
