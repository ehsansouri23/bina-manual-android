package com.usermanual.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.usermanual.R;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.model.Media;
import com.usermanual.model.Title;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    int state;
    int title, subTitle;

    ListView titlesListView;
    ArrayAdapter<String> adapter;

    TextView titleGuide, arrow, subtitleGuide;

    List<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        state = 0;
        titlesListView = (ListView) findViewById(R.id.titles_list_view);
        titleGuide = (TextView) findViewById(R.id.title_guide);
        arrow = (TextView) findViewById(R.id.arrow);
        subtitleGuide = (TextView) findViewById(R.id.subtitle_guide);

//        for (int i = 0; i < 100; i++) { //todo delete it
//            DownloadClass downloadClass = new DownloadClass();
//            downloadClass.execute(i + "");
//        }


        String json = "[\n" +
                "  {\n" +
                "    \"title\": \"title 1\",\n" +
                "    \"sub\" : [\n" +
                "      {\n" +
                "        \"subTitle\": \"subtitle 1 1\",\n" +
                "        \"medias\": [\n" +
                "          {\n" +
                "            \"text\": \"text 1\",\n" +
                "            \"url\": \"url 1\",\n" +
                "            \"type\": 1\n" +
                "          },\n" +
                "          {\n" +
                "            \"text\": \"text 2\",\n" +
                "            \"url\": \"url 1\",\n" +
                "            \"type\": 1\n" +
                "          },\n" +
                "          {\n" +
                "            \"text\": \"text 3\",\n" +
                "            \"url\": \"url 1\",\n" +
                "            \"type\": 1\n" +
                "          }\n" +
                "          ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"subTitle\": \"subtitle 1 2\",\n" +
                "        \"medias\": [\n" +
                "          {\n" +
                "            \"text\": \"text1\",\n" +
                "            \"url\": \"url 1\",\n" +
                "            \"type\": 1\n" +
                "          }\n" +
                "          ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"subTitle\": \"subtitle 1 3\",\n" +
                "        \"medias\": [\n" +
                "          {\n" +
                "            \"text\": \"text1\",\n" +
                "            \"url\": \"url 1\",\n" +
                "            \"type\": 1\n" +
                "          }\n" +
                "          ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"subTitle\": \"subtitle 1 4\",\n" +
                "        \"medias\": [\n" +
                "          {\n" +
                "            \"text\": \"text1\",\n" +
                "            \"url\": \"url 1\",\n" +
                "            \"type\": 1\n" +
                "          }\n" +
                "          ]\n" +
                "      }\n" +
                "      ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"title 2\",\n" +
                "    \"sub\" : [\n" +
                "      {\n" +
                "        \"subTitle\": \"subtitle 2 1\",\n" +
                "        \"medias\": [\n" +
                "          {\n" +
                "            \"text\": \"text1\",\n" +
                "            \"url\": \"url 1\",\n" +
                "            \"type\": 1\n" +
                "          }\n" +
                "          ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"subTitle\": \"subtitle 2 2\",\n" +
                "        \"medias\": [\n" +
                "          {\n" +
                "            \"text\": \"text1\",\n" +
                "            \"url\": \"url 1\",\n" +
                "            \"type\": 1\n" +
                "          }\n" +
                "          ]\n" +
                "      }\n" +
                "      ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"title 3\",\n" +
                "    \"sub\" : [\n" +
                "      {\n" +
                "        \"subTitle\": \"subtitle\",\n" +
                "        \"medias\": [\n" +
                "          {\n" +
                "            \"text\": \"text1\",\n" +
                "            \"url\": \"url 1\",\n" +
                "            \"type\": 1\n" +
                "          }\n" +
                "          ]\n" +
                "      }\n" +
                "      ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"title\": \"title 4\",\n" +
                "    \"sub\" : [\n" +
                "      {\n" +
                "        \"subTitle\": \"subtitle\",\n" +
                "        \"medias\": [\n" +
                "          {\n" +
                "            \"text\": \"text1\",\n" +
                "            \"url\": \"url 1\",\n" +
                "            \"type\": 1\n" +
                "          }\n" +
                "          ]\n" +
                "      }\n" +
                "      ]\n" +
                "  }\n" +
                "  \n" +
                "]";

        Gson gson = new Gson();
        List<Title> titles = gson.fromJson(json, new TypeToken<List<Title>>() {
        }.getType());
        DataBaseHelper.saveToDB(getApplicationContext(), titles);
        titleList = DataBaseHelper.getTitlesList(getApplicationContext());
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, titleList);
        adapter.notifyDataSetChanged();
        titlesListView.setAdapter(adapter);

        titlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (state) {
                    case 0:
                        state = 1;
                        title = position + 1;
                        titleGuide.setText(titleList.get(position));
                        titleGuide.setVisibility(View.VISIBLE);
                        List<String> subtitles = DataBaseHelper.getSubtitleList(getApplicationContext(), position + 1);
                        adapter.clear();
                        adapter.addAll(subtitles);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        state = 2;
                        subTitle = position + 1;
                        arrow.setVisibility(View.VISIBLE);
                        subtitleGuide.setText(adapter.getItem(position));
                        subtitleGuide.setVisibility(View.VISIBLE);

                    case 2:
                        List<Media> mediaList = DataBaseHelper.getMediaList(getApplicationContext(), title, subTitle);
                        Intent intent = new Intent(MainActivity.this, MediaActivity.class);
                        Log.e(TAG, "onItemClick: " + mediaList.size());
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (state == 2)
            state = 1;
    }

    @Override
    public void onBackPressed() {
        if (state == 0)
            super.onBackPressed();
        if (state == 1) {
            titleList = DataBaseHelper.getTitlesList(getApplicationContext());
            adapter.clear();
            adapter.addAll(titleList);
            adapter.notifyDataSetChanged();
            titleGuide.setVisibility(View.GONE);
            state = 0;
        }
    }
}
