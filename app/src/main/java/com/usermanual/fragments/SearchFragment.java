package com.usermanual.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermanual.R;
import com.usermanual.activities.MainActivity;
import com.usermanual.adapter.SearchAdapter;

import static com.usermanual.helper.Consts.SEARCH_QUERY;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";

    RecyclerView searchList;
    SearchAdapter searchAdapter;

    String searchQuery;
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        searchList = (RecyclerView) view.findViewById(R.id.list);
        Bundle args = getArguments();
        searchQuery = args.getString(SEARCH_QUERY);
        searchAdapter = new SearchAdapter(getContext(), searchQuery, new SearchDelegate());
        searchList.setAdapter(searchAdapter);
        searchList.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public static SearchFragment newInstance(String searchQuery, MainActivity activity) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_QUERY, searchQuery);
        searchFragment.setArguments(bundle);
        searchFragment.setActivity(activity);
        return searchFragment;
    }

    public class SearchDelegate {
        public void clicked(int titleId) {
            Log.e(TAG, "clicked: " + titleId);
            activity.openTitlesFragment(titleId);
        }
    }

    public void setActivity(MainActivity mainActivity) {
        this.activity = mainActivity;
    }
}
