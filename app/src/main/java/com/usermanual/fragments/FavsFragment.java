package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermanual.ItemClickListener;
import com.usermanual.R;
import com.usermanual.activities.MediaActivity;
import com.usermanual.adapter.DataListAdapter;
import com.usermanual.dbmodels.TableSubTitle;
import com.usermanual.dbmodels.TitleData;
import com.usermanual.helper.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

import static com.usermanual.helper.Consts.PREF_SUBTITLE_ID;

public class FavsFragment extends Fragment {

    RecyclerView list;
    DataListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        list = (RecyclerView) view.findViewById(R.id.list);
        adapter = new DataListAdapter(getContext(), new ItemClickListener() {
            @Override
            public void onItemClick(View v, int id, String url) {
                Intent mediaIntent = new Intent(getActivity(), MediaActivity.class);
                mediaIntent.putExtra(PREF_SUBTITLE_ID, id);
                startActivity(mediaIntent);
            }
        });
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        List<TableSubTitle> savedSubtitles = DataBaseHelper.getAllFavs(getContext());

        List<TitleData> titleDatas = new ArrayList<>();
        for (int i = 0; i < savedSubtitles.size(); i++) {
            TitleData titleData = new TitleData(savedSubtitles.get(i).subtitleId, savedSubtitles.get(i).parentTitleId, savedSubtitles.get(i).subtitle, savedSubtitles.get(i).fileKey);
            titleDatas.add(titleData);
        }

        adapter.setTitles(titleDatas);
        adapter.notifyDataSetChanged();

        return view;
    }

    public static FavsFragment newInstance() {
        return new FavsFragment();
    }
}
