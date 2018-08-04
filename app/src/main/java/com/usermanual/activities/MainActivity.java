package com.usermanual.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.usermanual.R;
import com.usermanual.fragments.AboutUsFragment;
import com.usermanual.fragments.TitlesFragment;
import com.usermanual.helper.BottomNavigationViewHelper;
import com.usermanual.helper.NetworkHelper;
import com.usermanual.helper.SaveToDB;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    BottomNavigationView bottomNavigation;
    TitlesFragment titlesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlesFragment = new TitlesFragment();
        final FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();

        if (NetworkHelper.isNetworkConnected(getApplicationContext())) {
            new SaveToDB(this).execute();
        }

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.id_home:
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, titlesFragment).commit();
                        fragmentTransaction.addToBackStack(null);
                        break;
                    case R.id.id_about:
                        FragmentTransaction fragmentTransaction1 = manager.beginTransaction();
                        fragmentTransaction1.addToBackStack(null);
                        fragmentTransaction1.replace(R.id.fragment_container, new AboutUsFragment()).commit();
                        break;
                }
                return true;
            }
        });

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




    }

    @Override
    public void onBackPressed() {
        if (titlesFragment.isVisible())
            if (titlesFragment.onBackPressed())
                super.onBackPressed();
    }
}
