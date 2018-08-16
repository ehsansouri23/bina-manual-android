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
import com.usermanual.adapter.SearchAdapter;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.PrefHelper;
import com.usermanual.helper.dbmodels.SearchModel;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";

    RecyclerView searchList;
    SearchAdapter searchAdapter;

    List<SearchModel> searchModelList;

    String searchQuery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        searchList = (RecyclerView) view.findViewById(R.id.search_list);
        Bundle args = getArguments();
        searchQuery = args.getString(PrefHelper.SEARCH_QUERY);
        searchModelList = prepareSearchModelList(searchQuery);
        searchAdapter = new SearchAdapter(getContext(), searchModelList);
        searchList.setAdapter(searchAdapter);
        searchList.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    List<SearchModel> prepareSearchModelList(String query) {
        searchModelList = new ArrayList<>();
        List<TableTitle> tableTitleList = DataBaseHelper.getTitlesList(getContext());
        for (int i = 0; i < tableTitleList.size(); i++) {
            SearchModel searchModel = new SearchModel();
            if (tableTitleList.get(i).title.contains(query)) {
                searchModel.title = tableTitleList.get(i);
                Log.e(TAG, "prepareSearchModelList: title contains query. " + tableTitleList.get(i).title);
            }
            List<TableSubTitle> tableSubTitles = DataBaseHelper.searchSubtitle(getContext(), tableTitleList.get(i).titleId, query);
            for (int j = 0; j < tableSubTitles.size(); j++) {
                Log.e(TAG, "prepareSearchModelList: sub for " + tableTitleList.get(i).title + " : " + tableSubTitles.get(j).subtitle);
            }
            searchModel.subtitles = tableSubTitles;
            searchModelList.add(searchModel);
        }
        return searchModelList;
    }
}
