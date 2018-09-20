package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.eyalbira.loadingdots.LoadingDots;
import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.activities.MainActivity;
import com.usermanual.activities.MediaActivity;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.PrefHelper;
import com.usermanual.helper.StorageHelper;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;

import java.util.ArrayList;
import java.util.List;

import static com.usermanual.helper.Consts.*;

public class TitlesFragment extends Fragment {
    private static final String TAG = "TitlesFragment";

    public static final int TITLES = 0;
    public static final int SUBTITLES = 1;
    public static final int MEDIAS = 2;

    int state;
    int selectedTitleId, selectedSubtitleId;

    LoadingDots loading;
    LinearLayout noItems;
    ImageView headerImage;

    ListView listView;
    LayoutAnimationController listViewAnimation;
    ArrayAdapter<String> adapter;

    List<TableTitle> titleList;
    List<TableSubTitle> subtitleList;
    List<String> titlesString;
    List<String> subtitlesString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.titles_fragment, container, false);
        loading = (LoadingDots) view.findViewById(R.id.loading);
        noItems = (LinearLayout) view.findViewById(R.id.no_item);
        headerImage = (ImageView) view.findViewById(R.id.header_image);

        listView = (ListView) view.findViewById(R.id.titles_list_view);

        state = TITLES;
        titleList = new ArrayList<>();
        subtitleList = new ArrayList<>();

        if (PrefHelper.getBoolean(getContext(), PREF_ANIMATIONS, true))
            listViewAnimation =
                    AnimationUtils.loadLayoutAnimation(view.getContext(), R.anim.list_anim);
        else
            listViewAnimation = null;

        //dummy loading animation!!
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        };
        handler.postDelayed(runnable, 1400);

        //show list base on state
        Bundle args = getArguments();
        if (args != null) {
            int showingState = args.getInt(PREF_STATE);
            if (showingState == SUBTITLES) {
                selectedTitleId = args.getInt(PREF_TITLE_ID);
                //showing image in header of list
                TableTitle tableTitle = DataBaseHelper.getTitle(getContext(), selectedTitleId);
                StorageHelper.FileSpec imageFile = new StorageHelper.FileSpec(getContext(), tableTitle.picUrl, StorageHelper.FileType.TITLES);
                Picasso.get().load(imageFile.getFile()).placeholder(R.mipmap.car).into(headerImage);
                subtitleList = DataBaseHelper.getSubtitlesList(getContext(), selectedTitleId);
                subtitlesString = getSubtitles(subtitleList);
                showList(subtitlesString);
            } else if (showingState == TITLES) {
                titleList = DataBaseHelper.getTitlesList(getContext());
                titlesString = getTitles(titleList);
                showList(titlesString);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (state) {
                    case TITLES:
                        state = SUBTITLES;
                        selectedTitleId = titleList.get(position).titleId;
                        TableTitle tableTitle = DataBaseHelper.getTitle(getContext(), selectedTitleId);
                        StorageHelper.FileSpec imageFile = new StorageHelper.FileSpec(getContext(), tableTitle.picUrl, StorageHelper.FileType.TITLES);
                        Picasso.get().load(imageFile.getFile()).placeholder(R.mipmap.car).into(headerImage);
                        Log.e(TAG, "onItemClick: " + selectedTitleId + " title: " + titleList.get(position).title);
                        ((MainActivity) (getActivity())).setToolbarTitle(titleList.get(position).title);
                        subtitleList = DataBaseHelper.getSubtitlesList(getContext(), selectedTitleId);
                        subtitlesString = getSubtitles(subtitleList);
                        showList(subtitlesString);
                        break;
                    case SUBTITLES:
                        state = MEDIAS;
                        selectedSubtitleId = subtitleList.get(position).subtitleId;

                    case MEDIAS:
                        Intent intent = new Intent(getActivity(), MediaActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(PREF_SUBTITLE_ID, selectedSubtitleId);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
            }
        });

        return view;
    }

    public static TitlesFragment newInstance(int state) {
        TitlesFragment titlesFragment = new TitlesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PREF_STATE, state);
        titlesFragment.setArguments(bundle);
        return titlesFragment;
    }

    public boolean onBackPressed() {
        if (state == TITLES)
            return true;
        if (state == SUBTITLES) {
            adapter.clear();
            titlesString = getTitles(DataBaseHelper.getTitlesList(getContext()));
            adapter.addAll(titlesString);
            adapter.notifyDataSetChanged();
            listView.setLayoutAnimation(listViewAnimation);
            state = TITLES;
            return false;
        }
        return false;
    }

    public void onResume() {
        super.onResume();
        if (state == MEDIAS)
            state = SUBTITLES;
    }

    private List<String> getTitles(List<TableTitle> tableTitles) {
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < tableTitles.size(); i++) {
            titleList.add(tableTitles.get(i).title);
        }
        return titleList;
    }

    private List<String> getSubtitles(List<TableSubTitle> tableSubTitles) {
        List<String> subtitleList = new ArrayList<>();
        for (int i = 0; i < tableSubTitles.size(); i++) {
            subtitleList.add(tableSubTitles.get(i).subtitle);
        }
        return subtitleList;
    }

    private void showList(List<String> strings) {
        if (strings.isEmpty()) {
            noItems.setVisibility(View.VISIBLE);
        } else {
            noItems.setVisibility(View.GONE);
        }
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, strings);
        adapter.clear();
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setLayoutAnimation(listViewAnimation);
    }
}
