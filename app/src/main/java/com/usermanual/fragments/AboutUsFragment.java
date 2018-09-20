package com.usermanual.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.usermanual.R;

public class AboutUsFragment extends Fragment {
    private static final String TAG = "AboutUsFragment";

    ImageView imageView;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.aboutus_fragment, container, false);
        imageView = (ImageView) v.findViewById(R.id.image);
        textView = (TextView) v.findViewById(R.id.text);

//        Picasso.get().
        return v;
    }
}
