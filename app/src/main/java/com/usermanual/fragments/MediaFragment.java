package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.activities.ImageViewActivity;
import com.usermanual.activities.VideoViewActivity;
import com.usermanual.dbmodels.TableSubMedia;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.PrefHelper;
import com.usermanual.helper.StorageHelper;

import java.util.List;

import static com.usermanual.helper.Consts.IMAGE;
import static com.usermanual.helper.Consts.MEDIA_KEY;
import static com.usermanual.helper.Consts.PREF_FONT_SIZE;
import static com.usermanual.helper.Consts.VIDEO;
import static com.usermanual.helper.Consts.FILE_KEY;

public class MediaFragment extends Fragment {

    int mediaId;
    LinearLayout mainLayout;
    List<TableSubMedia> tableSubMediaList;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_fragment, container, false);
        mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
        Bundle bundle = getArguments();
        mediaId = bundle.getInt(MEDIA_KEY);
        tableSubMediaList = DataBaseHelper.getSubmedias(getContext(), mediaId);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 30, 50, 30);
        for (int i = 0; i < tableSubMediaList.size(); i++) {
            TableSubMedia tableSubMedia = tableSubMediaList.get(i);
            if (!tableSubMedia.text.equals("")) {
                TextView textView = new TextView(getContext());
                textView.setLayoutParams(layoutParams);
                mainLayout.addView(textView);
                textView.setText(tableSubMedia.text);
                textView.setTextSize(PrefHelper.getInt(getContext(), PREF_FONT_SIZE, 10));
            }
            if (!tableSubMedia.fileKey.equals("")) {
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(layoutParams);
                mainLayout.addView(imageView);
                final String fileKey = tableSubMedia.fileKey;
                final int fileType = DataBaseHelper.getFileType(getContext(), fileKey);
                if (fileType == IMAGE) {
                    Picasso.get().load(StorageHelper.getFile(getContext(), fileKey)).placeholder(R.mipmap.car).into(imageView);
                } else if (fileType == VIDEO) {
                    Picasso.get().load(R.mipmap.video).into(imageView);
                }
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = null;
                            if (fileType == IMAGE)
                                intent = new Intent(getContext(), ImageViewActivity.class);
                            else if (fileType == VIDEO)
                                intent = new Intent(getContext(), VideoViewActivity.class);
                            if (intent == null)
                                return;
                            intent.putExtra(FILE_KEY, fileKey);
                            startActivity(intent);
                        }
                    });
                }
            }

        return view;

    }

    public static MediaFragment newInstance(int mediaId) {
        MediaFragment mediaFragment = new MediaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MEDIA_KEY, mediaId);
        mediaFragment.setArguments(bundle);
        return mediaFragment;
    }
}
