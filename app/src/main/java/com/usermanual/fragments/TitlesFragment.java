package com.usermanual.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.squareup.picasso.Picasso;
import com.usermanual.ItemClickListener;
import com.usermanual.R;
import com.usermanual.activities.MainActivity;
import com.usermanual.activities.MediaActivity;
import com.usermanual.adapter.DataListAdapter;
import com.usermanual.dbmodels.TableSubTitle;
import com.usermanual.dbmodels.TableTitle;
import com.usermanual.dbmodels.TitleData;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.StorageHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.usermanual.helper.Consts.PREF_STATE;
import static com.usermanual.helper.Consts.PREF_SUBTITLE_ID;
import static com.usermanual.helper.Consts.PREF_TITLE_ID;

public class TitlesFragment extends Fragment implements ItemClickListener {
    private static final String TAG = "TitlesFragment";

    public static final int TITLES = 0;
    public static final int SUBTITLES = 1;
    public static final int MEDIAS = 2;

    int state;
    int selectedTitleId, selectedSubtitleId;

    Context context;

    @BindView(R.id.no_item)
    LinearLayout noItems;
    @BindView(R.id.parent_image)
    ImageView parentImage;
    @BindView(R.id.data_list)
    ShimmerRecyclerView dataList;

    DataListAdapter dataListAdapter;

    LayoutAnimationController listViewAnimation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.titles_fragment, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        dataListAdapter = new DataListAdapter(context, this);
        dataList.setAdapter(dataListAdapter);
        dataList.setLayoutManager(new LinearLayoutManager(context));
        dataList.showShimmerAdapter();
        state = TITLES;

//        if (PrefHelper.getBoolean(context, PREF_ANIMATIONS, true))
//            listViewAnimation =
//                    AnimationUtils.loadLayoutAnimation(context, R.anim.list_anim);
//        else
//            listViewAnimation = null;

        Bundle args = getArguments();
        if (args != null) {
            int showingState = args.getInt(PREF_STATE);
            switch (showingState) {
                case TITLES:
                    showList();
                    break;
            }
        }

        //dummy loading animation!!
        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
////                listView.setVisibility(View.VISIBLE);
//                parentImage.setVisibility(View.VISIBLE);
//
//                //show list base on state
//                Bundle args = getArguments();
//                if (args != null) {
//                    int showingState = args.getInt(PREF_STATE);
//                    if (showingState == SUBTITLES) {
//                        state = SUBTITLES;
//                        selectedTitleId = args.getInt(PREF_TITLE_ID);
//                        //showing image in header of list
//                        TableTitle tableTitle = DataBaseHelper.getTitle(context, selectedTitleId);
//                        Picasso.get().load(StorageHelper.getFile(context, tableTitle.fileKey)).placeholder(R.mipmap.car).into(parentImage);
//                    } else if (showingState == TITLES) {
//                        state = TITLES;
//                    }
//                }
//            }
//        };

//        handler.postDelayed(runnable, 1300);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void ItemClickListener(AdapterView<?> parent, View view, int position, long id) {
//                switch (state) {
//                    case TITLES:
//                        state = SUBTITLES;
//                        selectedTitleId = titleList.get(position).titleId;
//                        TableTitle tableTitle = DataBaseHelper.getTitle(context, selectedTitleId);
//                        Picasso.get().load(StorageHelper.getFile(context, tableTitle.fileKey)).placeholder(R.mipmap.car).into(parentImage);
//                        Log.d(TAG, "ItemClickListener: " + selectedTitleId + " title: [" + titleList.get(position).title + "] fileKey: [" + tableTitle.fileKey + "]");
//                        ((MainActivity) (getActivity())).setToolbarTitle(titleList.get(position).title);
//                        subtitleList = DataBaseHelper.getSubtitlesList(context, selectedTitleId);
//                        subtitlesString = getSubtitles(subtitleList);
//                        showList(subtitlesString);
//                        break;
//                    case SUBTITLES:
//                        state = MEDIAS;
//                        selectedSubtitleId = subtitleList.get(position).subtitleId;
//                        Log.d(TAG, "ItemClickListener: state: subtitle. subtitleId: " + selectedSubtitleId);
//
//                    case MEDIAS:
//                        Intent intent = new Intent(getActivity(), MediaActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(PREF_SUBTITLE_ID, selectedSubtitleId);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        break;
//                }
//            }
//        });

        return view;
    }

    public static TitlesFragment newInstance(int state) {
        TitlesFragment titlesFragment = new TitlesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PREF_STATE, state);
        titlesFragment.setArguments(bundle);
        return titlesFragment;
    }

    public static TitlesFragment newInstance(int state, int selectedTitleId) {
        TitlesFragment titlesFragment = new TitlesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PREF_STATE, state);
        bundle.putInt(PREF_TITLE_ID, selectedTitleId);
        titlesFragment.setArguments(bundle);
        return titlesFragment;
    }

    private void showList() {
        List<TableTitle> titles = DataBaseHelper.getTitlesList(context);
        Log.e(TAG, "showList: titles size=" + titles.size());
        List<TitleData> titleDatas = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            TitleData titleData = new TitleData(titles.get(i).titleId, -1, titles.get(i).title, titles.get(i).fileKey);
            titleDatas.add(titleData);
        }
        dataList.hideShimmerAdapter();
        if (titleDatas.isEmpty()) {
            noItems.setVisibility(View.VISIBLE);
            Log.e(TAG, "showList: is empty");
        }
        else {
            noItems.setVisibility(View.GONE);
            dataListAdapter.setTitles(titleDatas);
            dataListAdapter.notifyDataSetChanged();
        }
    }

    private void showList(int pid) {
        List<TableSubTitle> subTitles = DataBaseHelper.getSubtitlesList(context, pid);
        Log.e(TAG, "showList: subtitles size=" + subTitles.size());
        List<TitleData> titleDatas = new ArrayList<>();
        for (int i = 0; i < subTitles.size(); i++) {
            TitleData titleData = new TitleData(subTitles.get(i).subtitleId, subTitles.get(i).parentTitleId, subTitles.get(i).subtitle, subTitles.get(i).fileKey);
            titleDatas.add(titleData);
        }
        dataList.hideShimmerAdapter();
        if (titleDatas.isEmpty()) {
            noItems.setVisibility(View.VISIBLE);
            dataList.setVisibility(View.GONE);
        }
        else {
            noItems.setVisibility(View.GONE);
            dataList.setVisibility(View.VISIBLE);
            dataListAdapter.setTitles(titleDatas);
            dataListAdapter.notifyDataSetChanged();
        }
    }

    public boolean onBackPressed() {
        if (state == TITLES)
            return true;
        if (state == SUBTITLES) {
            Picasso.get().load(R.mipmap.car).into(parentImage);
            ((MainActivity) (getActivity())).setToolbarTitle(getResources().getString(R.string.home));
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

    @Override
    public void onItemClick(View v, int id, String url) {
        Log.d(TAG, "onItemClick: id=" + id + "    url=" + url);
        switch (state) {
            case TITLES:
                state = SUBTITLES;
                File file = StorageHelper.getFile(context, url);
                Log.d(TAG, "file: " + file.getAbsolutePath());
                Log.d(TAG, "exists: " + file.exists() + "     " + file.canRead() + "    " + file.isDirectory());
//                parentImage.setImageURI(Uri.fromFile(new File(file, "1daaec24a1ba881e15ebae65ec12089f")));
                Picasso.get().load(file).placeholder(R.mipmap.car).into(parentImage);
                showList(id);
                break;
            case SUBTITLES:
                Intent mediaIntent = new Intent(getActivity(), MediaActivity.class);
                mediaIntent.putExtra(PREF_SUBTITLE_ID, id);
                startActivity(mediaIntent);
                break;
        }
    }
}
