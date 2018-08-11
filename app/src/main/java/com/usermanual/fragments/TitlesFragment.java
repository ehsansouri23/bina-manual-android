package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.usermanual.R;
import com.usermanual.activities.MediaActivity;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.dbmodels.TableMedia;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;

import java.util.ArrayList;
import java.util.List;

import static com.usermanual.helper.PrefHelper.MEDIA_LIST_KEY;

public class TitlesFragment extends Fragment {

    private static final int TITLES = 0;
    private static final int SUBTITLES = 1;
    private static final int MEDIAS = 2;

    int state;
    int selectedTitleId, selectedSubtitleId;

    ListView titlesListView;
    LayoutAnimationController listViewAnimation;
    ArrayAdapter<String> adapter;

    TextView titleGuide, arrow, subtitleGuide;
    EditText search;
    Button submit;

    List<TableTitle> titleList;
    List<TableSubTitle> subtitleList;
    List<String> titlesString;
    List<String> subtitlesString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.titles_fragment, container, false);
        titlesListView = (ListView) view.findViewById(R.id.titles_list_view);
        titleGuide = (TextView) view.findViewById(R.id.title_guide);
        arrow = (TextView) view.findViewById(R.id.arrow);
        subtitleGuide = (TextView) view.findViewById(R.id.subtitle_guide);
        search = (EditText) view.findViewById(R.id.search);
        submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {//todo should handle search like this
            @Override
            public void onClick(View v) {
                String s = search.getText().toString();
                Log.e("tag", "onClick: " + s);
                titleList = DataBaseHelper.searchTitle(getContext(), s);
                adapter.clear();
                titlesString = getTitles(titleList);
                adapter.addAll(titlesString
                );
                adapter.notifyDataSetChanged();
            }
        });

        titleList = new ArrayList<>();
        subtitleList = new ArrayList<>();

        listViewAnimation =
                AnimationUtils.loadLayoutAnimation(view.getContext(), R.anim.list_anim);

        titleList = DataBaseHelper.getTitlesList(getContext());
        titlesString = getTitles(titleList);

        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, titlesString);
        adapter.notifyDataSetChanged();
        titlesListView.setAdapter(adapter);
        titlesListView.setLayoutAnimation(listViewAnimation);

        titlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (state) {
                    case TITLES:
                        state = SUBTITLES;
                        selectedTitleId = titleList.get(position).titleId;
                        Log.e("tag", "onItemClick: " + selectedTitleId + " title: " + titleList.get(position).title);
                        titleGuide.setText(titleList.get(position).title);
                        titleGuide.setVisibility(View.VISIBLE);
                        subtitleList = DataBaseHelper.getSubtitlesList(getContext(), selectedTitleId);
                        subtitlesString = getSubtitles(subtitleList);
                        adapter.clear();
                        adapter.addAll(subtitlesString);
                        adapter.notifyDataSetChanged();
                        titlesListView.setLayoutAnimation(listViewAnimation);
                        break;
                    case SUBTITLES:
                        state = MEDIAS;
                        selectedSubtitleId = subtitleList.get(position).subtitleId;
                        arrow.setVisibility(View.VISIBLE);
                        subtitleGuide.setText(adapter.getItem(position));
                        subtitleGuide.setVisibility(View.VISIBLE);

                    case MEDIAS:
                        List<TableMedia> mediaList = DataBaseHelper.getMediaList(getContext(), selectedSubtitleId);
                        Intent intent = new Intent(getActivity(), MediaActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(MEDIA_LIST_KEY, (ArrayList<TableMedia>) mediaList);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
            }
        });

        titleGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = TITLES;
                adapter.clear();
                adapter.addAll(titlesString);
                adapter.notifyDataSetChanged();
                titlesListView.setLayoutAnimation(listViewAnimation);
                titleGuide.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public boolean onBackPressed() {
        if (state == TITLES)
            return true;
        if (state == SUBTITLES) {
            adapter.clear();
            titlesString = getTitles(DataBaseHelper.getTitlesList(getContext()));
            adapter.addAll(titlesString);
            adapter.notifyDataSetChanged();
            titlesListView.setLayoutAnimation(listViewAnimation);
            titleGuide.setVisibility(View.GONE);
            state = TITLES;
            return false;
        }
        return false;
    }

    public void onResume() {
        super.onResume();
        if (state == MEDIAS)
            state = SUBTITLES;
    }

    private List<String> getTitles(List<TableTitle> tableTitles) {
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < tableTitles.size(); i++) {
            titleList.add(tableTitles.get(i).title);
        }
        return titleList;
    }

    private List<String> getSubtitles(List<TableSubTitle> tableSubTitles) {
        List<String> subtitleList = new ArrayList<>();
        for (int i = 0; i < tableSubTitles.size(); i++) {
            subtitleList.add(tableSubTitles.get(i).subtitle);
        }
        return subtitleList;
    }
}
