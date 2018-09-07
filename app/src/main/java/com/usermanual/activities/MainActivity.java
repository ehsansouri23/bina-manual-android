package com.usermanual.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.usermanual.R;
import com.usermanual.fragments.AboutUsFragment;
import com.usermanual.fragments.NewsFragment;
import com.usermanual.fragments.SearchFragment;
import com.usermanual.fragments.SettingsFragment;
import com.usermanual.fragments.SupportFragment;
import com.usermanual.fragments.TitlesFragment;
import com.usermanual.helper.BottomNavigationViewHelper;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.DownloadFile;
import com.usermanual.helper.NetworkHelper;
import com.usermanual.helper.PrefHelper;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener {
    private static final String TAG = "MainActivity";


    BottomNavigationView bottomNavigation;
    TitlesFragment titlesFragment;
    ProgressDialog progressDialog;

    private Menu mMenu;

    boolean loadingData;
    boolean titlesVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        progressDialog = new ProgressDialog(MainActivity.this);

        titlesFragment = new TitlesFragment();
        final FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();

        if (NetworkHelper.isNetworkConnected(getApplicationContext())) {
            final List<String> urls = new ArrayList<>();
//            urls.add("https://hw18.cdn.asset.aparat.com/aparat-video/891cef18f74c87868fab05ef9d7a687b11719517-480p__94453.mp4");
//            urls.add("https://hw4.cdn.asset.aparat.com/aparat-video/efbcb62f72bf7ab85c1a408c8fe7272f11720053-720p__32612.mp4");
//            urls.add("https://hw18.cdn.asset.aparat.com/aparat-video/95364c5f0941a471f97ae5e87fbec44511720687-480p__22220.mp4");
            urls.add("http://www.coca.ir/wp-content/uploads/2017/05/Profile-picture-1.jpg");
            urls.add("http://rozanehonline.com/wp-content/uploads/2017/12/%D8%B9%DA%A9%D8%B3-%D9%87%D8%A7%DB%8C-%D8%BA%D9%85%DA%AF%DB%8C%D9%86-%D8%B9%D8%A7%D8%B4%D9%82%D8%A7%D9%86%D9%87-64.jpg");
            urls.add("http://rozanehonline.com/wp-content/uploads/2017/12/%D8%B9%DA%A9%D8%B3-%D9%87%D8%A7%DB%8C-%D8%BA%D9%85%DA%AF%DB%8C%D9%86-%D8%B9%D8%A7%D8%B4%D9%82%D8%A7%D9%86%D9%87-64.jpg");
            urls.add("http://rozanehonline.com/wp-content/uploads/2017/12/%D8%B9%DA%A9%D8%B3-%D9%87%D8%A7%DB%8C-%D8%BA%D9%85%DA%AF%DB%8C%D9%86-%D8%B9%D8%A7%D8%B4%D9%82%D8%A7%D9%86%D9%87-64.jpg");

//            progressDialog.show();

            loadingData = true;
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(getResources().getString(R.string.receiving_data_from_server))
                    .setTitle(getResources().getString(R.string.receiving_data))
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getData();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new DownloadFile(urls, null, MainActivity.this, new DownFilesDelegate()).execute();
                        }
                    })
                    .show();

            Log.e(TAG, "onCreate: hello");
        } else
            manager.beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                titlesVisible = false;
                switch (item.getItemId()) {
                    case R.id.id_home:
                        titlesVisible = true;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TitlesFragment()).commit();
                        break;
                    case R.id.id_news:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsFragment()).commit();
                        break;
                    case R.id.id_support:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SupportFragment()).commit();
                        break;
                    case R.id.id_about:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).commit();
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (titlesVisible) {
            Log.e(TAG, "onBackPressed: titles are visible");
            if (titlesFragment.onBackPressed())
                super.onBackPressed();
        } else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (progressDialog != null) {
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        this.mMenu = menu;
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.e(TAG, "onNavigationItemSelected: " + id);
        switch (id) {
            case R.id.id_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
            case R.id.id_news:
                bottomNavigation.getMenu().findItem(R.id.id_news).setChecked(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsFragment()).commit();
                break;
            case R.id.id_support:
                bottomNavigation.getMenu().findItem(R.id.id_support).setChecked(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SupportFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.e(TAG, "onQueryTextSubmit: " + query);
        List<TableTitle> tableTitles = DataBaseHelper.searchTitle(getApplicationContext(), query);
        List<TableSubTitle> tableSubTitles = DataBaseHelper.searchSubtitle(getApplicationContext(), query);
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setActivity(this);
        Bundle args = new Bundle();
        args.putString(PrefHelper.SEARCH_QUERY, query);
        searchFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
        if (mMenu != null) {
            (mMenu.findItem(R.id.action_search)).collapseActionView();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    /**
     * getting titles and subtitles from server and save them in data base
     */
    private void getData() {
        progressDialog.show();
        flushDataBase();
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<TableTitle>> call = data.getTitles();
        progressDialog.setMessage(getResources().getString(R.string.getting_titles));
        call.enqueue(new Callback<List<TableTitle>>() {
            @Override
            public void onResponse(Call<List<TableTitle>> call, Response<List<TableTitle>> response) {
                if (response.body() != null)
                    DataBaseHelper.saveTitles(getApplicationContext(), response.body());
                Call<List<TableSubTitle>> callSubtitle = data.getSubtitles(PrefHelper.SUBTITLES_URL);
                progressDialog.setMessage(getResources().getString(R.string.getting_subtitles));
                callSubtitle.enqueue(new Callback<List<TableSubTitle>>() {
                    @Override
                    public void onResponse(Call<List<TableSubTitle>> call, Response<List<TableSubTitle>> response) {
                        if (response.body() != null) {
                            DataBaseHelper.saveSubtitles(getApplicationContext(), response.body());
                            downloadMedias();
                            progressDialog.dismiss();
                            titlesFragment = new TitlesFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TableSubTitle>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<TableTitle>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    /**
     * downloading medias
     */
    private void downloadMedias() {
//        if (progressDialog.isShowing())
//            progressDialog.dismiss();

    }

    private void flushDataBase() {
        DataBaseHelper.deleteAllTitles(getApplicationContext());
        DataBaseHelper.deleteAllSubtitles(getApplicationContext());
        DataBaseHelper.deleteAllMedias(getApplicationContext());
    }

    public void change(int titleId) {
        Fragment titleFragment = new TitlesFragment();
        Bundle args = new Bundle();
        args.putInt(PrefHelper.PREF_STATE, TitlesFragment.SUBTITLES);
        args.putInt(PrefHelper.PREF_TITLE_ID, titleId);
        titleFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, titleFragment).commit();
    }

    public class DownFilesDelegate {
        public void finished(boolean success) {
            if (!success) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage(getResources().getString(R.string.receiving_data_failed))
                        .setTitle(getResources().getString(R.string.receiving_data))
                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
            }
            if (success) {
                titlesFragment = new TitlesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();
            }
        }
    }
}
