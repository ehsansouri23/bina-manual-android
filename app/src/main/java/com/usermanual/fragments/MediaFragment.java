package com.usermanual.fragments;

import android.net.Uri;
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
import android.widget.VideoView;

import com.usermanual.R;
import com.usermanual.helper.PrefHelper;
import com.usermanual.helper.StorageHelper;
import com.usermanual.helper.dbmodels.TableMedia;

import java.io.File;
import java.util.List;

import static com.usermanual.helper.PrefHelper.MEDIA_KEY;

public class MediaFragment extends Fragment {

    LinearLayout mainLayout;
    List<TableMedia> tableMediaList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_fragment, container, false);
        mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
        Bundle bundle = getArguments();
        tableMediaList = bundle.getParcelable(MEDIA_KEY);

        for (int i = 0; i < tableMediaList.size(); i++) {
            TableMedia tableMedia = tableMediaList.get(i);
            if (tableMedia.mediaUrl != null) {
                if (tableMedia.type.equals("image")) {
                    ImageView imageView = new ImageView(getContext());
                    mainLayout.addView(imageView);
                    String fileName = tableMedia.mediaUrl.substring(tableMedia.mediaUrl.lastIndexOf("/"));
                    //todo image url
                } else if (tableMedia.type.equals("video")) {
                    VideoView videoView = new VideoView(getContext());
                    mainLayout.addView(videoView);
                    String fileName = tableMedia.mediaUrl.substring(tableMedia.mediaUrl.lastIndexOf("/"));
                    File videoFile = StorageHelper.getFile(getContext(), fileName);
                    videoView.setVideoURI(Uri.fromFile(videoFile));
                }
            }
            if (tableMedia.mediaText != null) {
                TextView textView = new TextView(getContext());
                mainLayout.addView(textView);
                textView.setText(tableMedia.mediaText);
                textView.setTextSize(PrefHelper.getInt(getContext(), PrefHelper.PREF_FONT_SIZE, 10));
            }
        }
        return view;
    }

}
