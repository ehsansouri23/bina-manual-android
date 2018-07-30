package com.usermanual.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.usermanual.R;
import com.usermanual.model.Media;

import static com.usermanual.helper.PrefHelper.MEDIA_KEY;

public class MediaFragment extends Fragment {

    Media media;

    TextView info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_fragment, container, false);
        info = (TextView) view.findViewById(R.id.info);
        Bundle bundle = getArguments();
        media = bundle.getParcelable(MEDIA_KEY);
        info.setText(media.getText());
        return view;
    }

}
