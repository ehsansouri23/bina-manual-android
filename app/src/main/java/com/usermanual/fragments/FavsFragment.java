package com.usermanual.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermanual.R;
import com.usermanual.adapter.FavsAdapter;

public class FavsFragment extends Fragment {

    RecyclerView list;
    FavsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        list = (RecyclerView) view.findViewById(R.id.list);
        adapter = new FavsAdapter(getContext());
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public static FavsFragment newInstance() {
        return new FavsFragment();
    }
}
