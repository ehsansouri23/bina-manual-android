package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.usermanual.R;
import com.usermanual.activities.ImageViewActivity;
import com.usermanual.activities.VideoViewActivity;
import com.usermanual.adapter.SubmediaAdapter;
import com.usermanual.dbmodels.TableSubMedia;
import com.usermanual.helper.Consts;
import com.usermanual.helper.DataBaseHelper;

import java.util.List;

import static com.usermanual.helper.Consts.MEDIA_KEY;

public class MediaFragment extends Fragment {

    int mediaId;
    LinearLayout mainLayout;
    List<TableSubMedia> tableSubMediaList;
    ShimmerRecyclerView list;
    SubmediaAdapter submediaAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_fragment, container, false);
        mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
        list = (ShimmerRecyclerView) view.findViewById(R.id.list);
        list.hideShimmerAdapter();
        Bundle bundle = getArguments();
        mediaId = bundle.getInt(MEDIA_KEY);
        tableSubMediaList = DataBaseHelper.getSubmedias(getContext(), mediaId);
        submediaAdapter = new SubmediaAdapter(getContext(), tableSubMediaList, new OnClick() {
            @Override
            public void onClick(int type, String key) {
                if (type == Consts.IMAGE) {
                    Intent imageActivityIntent = new Intent(getActivity(), ImageViewActivity.class);
                    imageActivityIntent.putExtra(Consts.FILE_KEY, key);
                    startActivity(imageActivityIntent);
                } else if (type == Consts.VIDEO) {
                    Intent videoActivityIntent = new Intent(getContext(), VideoViewActivity.class);
                    videoActivityIntent.putExtra(Consts.FILE_KEY, key);
                    startActivity(videoActivityIntent);
                }
            }
        });

        list.setAdapter(submediaAdapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public static MediaFragment newInstance(int mediaId) {
        MediaFragment mediaFragment = new MediaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MEDIA_KEY, mediaId);
        mediaFragment.setArguments(bundle);
        return mediaFragment;
    }

    public interface OnClick {
        void onClick(int type, String key);
    }
}
