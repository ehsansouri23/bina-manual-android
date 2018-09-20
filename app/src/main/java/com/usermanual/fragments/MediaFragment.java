package com.usermanual.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.usermanual.R;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.dbmodels.TableMedia;
import com.usermanual.helper.dbmodels.TableSubMedia;

import java.util.List;

import static com.usermanual.helper.Consts.MEDIA_KEY;

public class MediaFragment extends Fragment {

    int mediaId;
    LinearLayout mainLayout;
    List<TableSubMedia> tableSubMediaList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_fragment, container, false);
        mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
        Bundle bundle = getArguments();
        mediaId = bundle.getInt(MEDIA_KEY);
        tableSubMediaList = DataBaseHelper.getSubmedias(getContext(), mediaId);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(50, 30, 50, 30);
//
//        for (int i = 0; i < tableMediaList.size(); i++) {
//            TableMedia tableMedia = tableMediaList.get(i);
//            if (tableMedia.mediaUrl != null) {
//                if (tableMedia.type.equals("image")) {
//                    ImageView imageView = new ImageView(getContext());
//                    imageView.setLayoutParams(layoutParams);
//                    mainLayout.addView(imageView);
//                    String fileName = tableMedia.mediaUrl.substring(tableMedia.mediaUrl.lastIndexOf("/"));
//                    //todo image url
//                } else if (tableMedia.type.equals("video")) {
//                    ImageView imageView = new ImageView(getContext());
//                    imageView.setLayoutParams(layoutParams);
//                    mainLayout.addView(imageView);
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getContext(), VideoViewActivity.class);
//                            intent.putExtra(VIDEO_FILE_NAME, "d");//todo video url
//                            startActivity(intent);
//                        }
//                    });
//                }
//            }
//            if (tableMedia.mediaText != null) {
//                TextView textView = new TextView(getContext());
//                textView.setLayoutParams(layoutParams);
//                mainLayout.addView(textView);
//                textView.setText(tableMedia.mediaText);
//                textView.setTextSize(PrefHelper.getInt(getContext(), PREF_FONT_SIZE, 10));
//            }
//        }
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
