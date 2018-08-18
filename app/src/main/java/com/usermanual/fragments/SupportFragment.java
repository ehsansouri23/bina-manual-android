package com.usermanual.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.usermanual.R;

public class SupportFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SupportFragment";

    Button send;
    EditText question;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support_fragment, container, false);
        question = (EditText) view.findViewById(R.id.question);
        send = (Button) view.findViewById(R.id.send);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send) {

        }
    }
}
