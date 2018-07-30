package com.usermanual.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.List;

import static com.usermanual.helper.PrefHelper.MEDIA_LIST_KEY;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";




    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case
//        }
        return true;
    }
}
