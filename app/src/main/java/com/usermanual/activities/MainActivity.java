package com.usermanual.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.usermanual.R;
import com.usermanual.auth.Auth;
import com.usermanual.dbmodels.TableMedia;
import com.usermanual.dbmodels.TableSubMedia;
import com.usermanual.dbmodels.TableSubTitle;
import com.usermanual.dbmodels.TableTitle;
import com.usermanual.dbmodels.TableToDownloadFiles;
import com.usermanual.fragments.AboutUsFragment;
import com.usermanual.fragments.DownloadFragment;
import com.usermanual.fragments.FavsFragment;
import com.usermanual.fragments.NewsFragment;
import com.usermanual.fragments.SearchFragment;
import com.usermanual.fragments.SettingsFragment;
import com.usermanual.fragments.SupportFragment;
import com.usermanual.fragments.TitlesFragment;
import com.usermanual.helper.BottomNavigationViewHelper;
import com.usermanual.helper.DataBaseHelper;
import com.usermanual.helper.NetworkHelper;
import com.usermanual.helper.StorageHelper;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener {
    private static final String TAG = "MainActivity";

    Context context;
    Toolbar toolbar;
    FragmentManager fmanager;

    BottomNavigationView bottomNavigation;
    View navigationHeader;
    TitlesFragment titlesFragment;
    ProgressDialog progressDialog;

    private Menu mMenu;

    boolean titlesVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        StorageHelper.createFilesDataBase(context);

        String[] files = StorageHelper.getFile(context, "").list();
        Log.e(TAG, "file size: " + files.length);
        for (int i = 0; i < files.length; i++) {
            Log.e(TAG, "file: " + files[i] + " fileType: " + DataBaseHelper.getFileType(context, files[i]));
        }

        flushDataBase();

//        List<TableToDownloadFiles> toDownloadFiles = new ArrayList<>();
//        TableToDownloadFiles tableToDownloadFiles = new TableToDownloadFiles();
//        tableToDownloadFiles.url = "https://hw15.cdn.asset.aparat.com/aparat-video/993690ee92cab49ca429f6a2e390341111864889-720p__55425.mp4";
//        tableToDownloadFiles.fileKey = "aaa";
//        toDownloadFiles.add(tableToDownloadFiles);
////        DataBaseHelper.saveToDownloadFile(context, tableToDownloadFiles);
//        TableToDownloadFiles t = new TableToDownloadFiles();
//        t.url = "https://as2.cdn.asset.aparat.com/aparat-video/bc14ddc38678800c1e53f78bb1a25c327183777-360p__16953.mp4";
//        t.fileKey = "bbb";
//        toDownloadFiles.add(t);
//        DataBaseHelper.saveToDownloadFiles(context, toDownloadFiles);
//        DataBaseHelper.saveToDownloadFiles(context, toDownloadFiles);

        titlesFragment = TitlesFragment.newInstance(TitlesFragment.TITLES);
        fmanager = getSupportFragmentManager();
        fmanager.beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationHeader = navigationView.getHeaderView(0);
        setupNavigationHeader();
        navigationView.setNavigationItemSelectedListener(this);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                titlesVisible = false;
                switch (item.getItemId()) {
                    case R.id.id_home:
                        titlesVisible = true;
                        setToolbarTitle(getResources().getString(R.string.home));
                        fmanager.beginTransaction().replace(R.id.fragment_container, TitlesFragment.newInstance(TitlesFragment.TITLES)).commit();
                        break;
                    case R.id.id_news:
                        setToolbarTitle(getResources().getString(R.string.news));
                        titlesVisible = false;
                        fmanager.beginTransaction().replace(R.id.fragment_container, NewsFragment.newInstance()).commit();
                        break;
                    case R.id.id_support:
                        setToolbarTitle(getResources().getString(R.string.support));
                        titlesVisible = false;
                        fmanager.beginTransaction().replace(R.id.fragment_container, SupportFragment.newInstance()).commit();
                        break;
                    case R.id.id_about:
                        setToolbarTitle(getResources().getString(R.string.about));
                        titlesVisible = false;
                        fmanager.beginTransaction().replace(R.id.fragment_container, AboutUsFragment.newInstance()).commit();
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
            case R.id.id_favs:
                setToolbarTitle(getResources().getString(R.string.favorites));
                fmanager.beginTransaction().replace(R.id.fragment_container, FavsFragment.newInstance()).commit();
                break;
            case R.id.id_settings:
                setToolbarTitle(getResources().getString(R.string.settings));
                fmanager.beginTransaction().replace(R.id.fragment_container, SettingsFragment.newInstance()).commit();
                break;
            case R.id.id_support:
                setToolbarTitle(getResources().getString(R.string.support));
                bottomNavigation.getMenu().findItem(R.id.id_support).setChecked(true);
                fmanager.beginTransaction().replace(R.id.fragment_container, SupportFragment.newInstance()).commit();
                break;
            case R.id.id_sync:
                syncData();
                break;
            case R.id.id_download:
//                downloadFiles();
                fmanager.beginTransaction().replace(R.id.fragment_container, new DownloadFragment()).commit();
                break;
            case R.id.id_about:
                setToolbarTitle(getResources().getString(R.string.about));
                bottomNavigation.getMenu().findItem(R.id.id_about).setChecked(true);
                fmanager.beginTransaction().replace(R.id.fragment_container, AboutUsFragment.newInstance()).commit();
                break;
            case R.id.id_logout:
                Auth.logout(context);
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.e(TAG, "onQueryTextSubmit: " + query);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SearchFragment.newInstance(query, MainActivity.this)).commit();
        if (mMenu != null) {
            (mMenu.findItem(R.id.action_search)).collapseActionView();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void syncData() {
        if (NetworkHelper.isNetworkConnected(context)) {
            flushDataBase();
            getTitles();
//            getSubtitles();
//            getMedias();
        }

    }

    /**
     * getting titles and subtitles from server and save them in data base
     */
    private void getTitles() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        progressDialog.setMessage(getResources().getString(R.string.getting_titles));
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<TableTitle>> call = data.getTitles(Auth.getToken(context));
        call.enqueue(new Callback<List<TableTitle>>() {
            @Override
            public void onResponse(Call<List<TableTitle>> call, Response<List<TableTitle>> response) {
                if (response.body() != null) {
                    DataBaseHelper.saveTitles(context, response.body());
                    for (int i = 0; i < response.body().size(); i++) {
                        if (!response.body().get(i).fileKey.equals("")) {
                            DataBaseHelper.saveToDownloadFile(context, response.body().get(i).fileKey);
                            DataBaseHelper.saveFileType(context, response.body().get(i).fileKey, response.body().get(i).fileType);
                        }
                    }
                    progressDialog.dismiss();
                    getSubtitles();
                }
            }

            @Override
            public void onFailure(Call<List<TableTitle>> call, Throwable t) {
                Toast.makeText(context, getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
//                finish();
            }
        });
    }

    private void getSubtitles() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        progressDialog.setMessage(getResources().getString(R.string.getting_subtitles));
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<TableSubTitle>> callSubtitle = data.getSubtitles(Auth.getToken(context));
        callSubtitle.enqueue(new Callback<List<TableSubTitle>>() {
            @Override
            public void onResponse(Call<List<TableSubTitle>> call, Response<List<TableSubTitle>> response) {
                if (response.body() != null) {
                    DataBaseHelper.saveSubtitles(context, response.body());
                    for (int i = 0; i < response.body().size(); i++) {
                        if (!response.body().get(i).fileKey.equals("")) {
                            DataBaseHelper.saveToDownloadFile(context, response.body().get(i).fileKey);
                            DataBaseHelper.saveFileType(context, response.body().get(i).fileKey, response.body().get(i).fileType);
                        }
                    }
                    progressDialog.dismiss();
                    getMedias();
                }
            }

            @Override
            public void onFailure(Call<List<TableSubTitle>> call, Throwable t) {
                Toast.makeText(context, getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
//                finish();
            }
        });
    }

    private void getMedias() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        progressDialog.setMessage(getResources().getString(R.string.getting_medias));
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<TableMedia>> getMediaCall = data.getMedias(Auth.getToken(context));
        getMediaCall.enqueue(new Callback<List<TableMedia>>() {
            @Override
            public void onResponse(Call<List<TableMedia>> call, Response<List<TableMedia>> response) {
                if (response.body() != null) {
                    DataBaseHelper.saveMedias(context, response.body());
                    progressDialog.dismiss();
                    getSubmedias();
                }
            }

            @Override
            public void onFailure(Call<List<TableMedia>> call, Throwable t) {
                Toast.makeText(context, getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
//                finish();
            }
        });
    }

    private void getSubmedias() {
        progressDialog.setMessage(getResources().getString(R.string.download_files));
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<TableSubMedia>> getSubmediaCall = data.getSubMedias(Auth.getToken(context));
        getSubmediaCall.enqueue(new Callback<List<TableSubMedia>>() {
            @Override
            public void onResponse(Call<List<TableSubMedia>> call, Response<List<TableSubMedia>> response) {
                if (response.body() != null) {
                    DataBaseHelper.saveSubmedias(context, response.body());
                    for (int j = 0; j < response.body().size(); j++) {
                        DataBaseHelper.saveToDownloadFile(context, response.body().get(j).fileKey);
                        DataBaseHelper.saveFileType(context, response.body().get(j).fileKey, response.body().get(j).fileType);
                    }
                    progressDialog.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setMessage(getResources().getString(R.string.download_files_from_slide_menu))
                            .setTitle(getResources().getString(R.string.download_files))
                            .setPositiveButton(getResources().getString(R.string.ok), null)
                            .show();

                }
            }

            @Override
            public void onFailure(Call<List<TableSubMedia>> call, Throwable t) {
                Toast.makeText(context, getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setMessage(getResources().getString(R.string.download_files_from_slide_menu))
                        .setTitle(getResources().getString(R.string.download_files))
                        .setPositiveButton(getResources().getString(R.string.ok), null)
                        .show();
            }
        });

    }

    private void flushDataBase() {
        DataBaseHelper.deleteAllTitles(context);
        DataBaseHelper.deleteAllSubtitles(context);
        DataBaseHelper.deleteAllMedias(context);
        DataBaseHelper.deleteAllSubmedias(context);
        DataBaseHelper.deleteAllToDownloadFiles(context);
        DataBaseHelper.deleteFileModels(context);
    }

    public void openTitlesFragment(int titleId) {
        TitlesFragment titlesFragment = TitlesFragment.newInstance(TitlesFragment.SUBTITLES, titleId);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();
    }

//    public class DownFilesDelegate {
//        public void finished(boolean success) {
//            if (!success) {
//                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
//                        .setMessage(getResources().getString(R.string.receiving_data_failed))
//                        .setTitle(getResources().getString(R.string.receiving_data))
//                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                finish();
//                            }
//                        })
//                        .show();
//            }
//            if (success) {
//                titlesFragment = new TitlesFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();
//            }
//        }
//    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    private void setupNavigationHeader() {
        ImageView avatar = (ImageView) navigationHeader.findViewById(R.id.avatar);
        TextView name = (TextView) navigationHeader.findViewById(R.id.name);
        name.setText(Auth.getName(context));
        Picasso.get().load(Auth.getUserPicUrl(context)).placeholder(R.mipmap.avatar).into(avatar);
    }
}
