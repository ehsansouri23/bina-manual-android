package com.usermanual.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.usermanual.R;
import com.usermanual.helper.DataBaseHelper;

import java.util.List;

public class SubtitlesActivity extends AppCompatActivity {

    ListView subTitlesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subTitlesListView = (ListView) findViewById(R.id.titles_list_view);

        int title = getIntent().getIntExtra("title", 0);

        List<String> subtitleList = DataBaseHelper.getSubtitleList(getApplicationContext(), title);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, subtitleList);
        adapter.notifyDataSetChanged();
        subTitlesListView.setAdapter(adapter);
    }

}
