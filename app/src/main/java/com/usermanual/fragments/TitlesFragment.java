package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    int state;
    int title, subTitle;

    ListView titlesListView;
    LayoutAnimationController listVeiwAnimation;
    ArrayAdapter<String> adapter;

    TextView titleGuide, arrow, subtitleGuide;

    List<String> titleList;
    List<String> subtitleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.titles_fragment, container, false);
        titlesListView = (ListView) view.findViewById(R.id.titles_list_view);
        titleGuide = (TextView) view.findViewById(R.id.title_guide);
        arrow = (TextView) view.findViewById(R.id.arrow);
        subtitleGuide = (TextView) view.findViewById(R.id.subtitle_guide);

        titleList = new ArrayList<>();
        subtitleList = new ArrayList<>();

        listVeiwAnimation =
                AnimationUtils.loadLayoutAnimation(view.getContext(), R.anim.list_anim);

        titleList = getTitles();

        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, titleList);
        adapter.notifyDataSetChanged();
        titlesListView.setAdapter(adapter);
        titlesListView.setLayoutAnimation(listVeiwAnimation);

        titlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (state) {
                    case 0:
                        state = 1;
                        title = position + 1;
                        titleGuide.setText(titleList.get(position));
                        titleGuide.setVisibility(View.VISIBLE);
                        subtitleList = getSubtitles(titleList.get(position));
                        adapter.clear();
                        adapter.addAll(subtitleList);
                        adapter.notifyDataSetChanged();
                        titlesListView.setLayoutAnimation(listVeiwAnimation);
                        break;
                    case 1:
                        state = 2;
                        subTitle = position + 1;
                        arrow.setVisibility(View.VISIBLE);
                        subtitleGuide.setText(adapter.getItem(position));
                        subtitleGuide.setVisibility(View.VISIBLE);

                    case 2:
                        List<TableMedia> mediaList = DataBaseHelper.getMediaList(getContext(), subtitleList.get(position));
                        Intent intent = new Intent(getActivity(), MediaActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(MEDIA_LIST_KEY, (ArrayList<TableMedia>) mediaList);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
            }
        });

        return view;
    }

    public boolean onBackPressed() {
        if (state == 0)
            return true;
        if (state == 1) {
            titleList = getTitles();
            adapter.clear();
            adapter.addAll(titleList);
            adapter.notifyDataSetChanged();
            titlesListView.setLayoutAnimation(listVeiwAnimation);
            titleGuide.setVisibility(View.GONE);
            state = 0;
            return false;
        }
        return false;
    }

    public void onResume() {
        super.onResume();
        if (state == 2)
            state = 1;
    }

    private List<String> getTitles() {
        List<String> titleList = new ArrayList<>();
        List<TableTitle> titles = DataBaseHelper.getTitlesList(getContext());
        for (int i = 0; i < titles.size(); i++) {
            titleList.add(titles.get(i).title);
        }
        return titleList;
    }

    private List<String> getSubtitles(String title) {
        List<String> subtitleList = new ArrayList<>();
        List<TableSubTitle> subTitles = DataBaseHelper.getSubtitlesList(getContext(), title);
        for (int i = 0; i < subTitles.size(); i++) {
            subtitleList.add(subTitles.get(i).title);
        }
        return subtitleList;
    }
}
