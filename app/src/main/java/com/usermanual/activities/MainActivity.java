package com.usermanual.activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.usermanual.R;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.model.Title;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ListView titlesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlesListView = (ListView) findViewById(R.id.titles_list_view);


        String json = "[\n" +
                "  {\n" +
                "    \"title\": \"title 1\",\n" +
                "    \"sub\" : [\n" +
                "      {\n" +
                "        \"subTitle\": \"subtitle 1 1\",\n" +
                "        \"medias\": [\n" +
                "          {\n" +
                "            \"text\": \"text1\",\n" +
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
        List<Title> titles = gson.fromJson(json, new TypeToken<List<Title>>(){}.getType());
        DataBaseHelper.saveToDB(getApplicationContext(), titles);
        List<String> titleList = DataBaseHelper.getTitlesList(getApplicationContext());
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, titleList);
        adapter.notifyDataSetChanged();
        titlesListView.setAdapter(adapter);

        titlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SubtitlesActivity.class);
                intent.putExtra("title", position + 1);
                startActivity(intent);
            }
        });
    }
}
