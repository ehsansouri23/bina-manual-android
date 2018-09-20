package com.usermanual.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import com.usermanual.auth.Auth;
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
import com.usermanual.helper.StorageHelper;
import com.usermanual.helper.dbmodels.TableMedia;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.usermanual.helper.Consts.PREF_STATE;
import static com.usermanual.helper.Consts.PREF_TITLE_ID;
import static com.usermanual.helper.Consts.SEARCH_QUERY;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener {
    private static final String TAG = "MainActivity";

    Context context;
    Toolbar toolbar;
    FragmentManager fmanager;

    BottomNavigationView bottomNavigation;
    TitlesFragment titlesFragment;
    ProgressDialog progressDialog;

    List<StorageHelper.FileSpec> toDownloadImages;

    private Menu mMenu;

    boolean loadingData;
    boolean titlesVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDownloadImages = new ArrayList<>();

        context = getApplicationContext();
        fmanager = getSupportFragmentManager();
        fmanager.beginTransaction().replace(R.id.fragment_container, new TitlesFragment()).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
//
//
////            final List<String> urls = new ArrayList<>();
////            urls.add("https://hw18.cdn.asset.aparat.com/aparat-video/891cef18f74c87868fab05ef9d7a687b11719517-480p__94453.mp4");
////            urls.add("https://hw4.cdn.asset.aparat.com/aparat-video/efbcb62f72bf7ab85c1a408c8fe7272f11720053-720p__32612.mp4");
////            urls.add("https://hw18.cdn.asset.aparat.com/aparat-video/95364c5f0941a471f97ae5e87fbec44511720687-480p__22220.mp4");
////            urls.add("http://www.coca.ir/wp-content/uploads/2017/05/Profile-picture-1.jpg");
////            urls.add("http://rozanehonline.com/wp-content/uploads/2017/12/%D8%B9%DA%A9%D8%B3-%D9%87%D8%A7%DB%8C-%D8%BA%D9%85%DA%AF%DB%8C%D9%86-%D8%B9%D8%A7%D8%B4%D9%82%D8%A7%D9%86%D9%87-64.jpg");
////            urls.add("http://rozanehonline.com/wp-content/uploads/2017/12/%D8%B9%DA%A9%D8%B3-%D9%87%D8%A7%DB%8C-%D8%BA%D9%85%DA%AF%DB%8C%D9%86-%D8%B9%D8%A7%D8%B4%D9%82%D8%A7%D9%86%D9%87-64.jpg");
////            urls.add("http://rozanehonline.com/wp-content/uploads/2017/12/%D8%B9%DA%A9%D8%B3-%D9%87%D8%A7%DB%8C-%D8%BA%D9%85%DA%AF%DB%8C%D9%86-%D8%B9%D8%A7%D8%B4%D9%82%D8%A7%D9%86%D9%87-64.jpg");
//
////            progressDialog.show();
//
//
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
                        fmanager.beginTransaction().replace(R.id.fragment_container, new TitlesFragment()).commit();
                        break;
                    case R.id.id_news:
                        setToolbarTitle(getResources().getString(R.string.news));
                        titlesVisible = false;
                        fmanager.beginTransaction().replace(R.id.fragment_container, new NewsFragment()).commit();
                        break;
                    case R.id.id_support:
                        setToolbarTitle(getResources().getString(R.string.support));
                        titlesVisible = false;
                        fmanager.beginTransaction().replace(R.id.fragment_container, new SupportFragment()).commit();
                        break;
                    case R.id.id_about:
                        setToolbarTitle(getResources().getString(R.string.about));
                        titlesVisible = false;
                        fmanager.beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).commit();
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
                setToolbarTitle(getResources().getString(R.string.settings));
                fmanager.beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
            case R.id.id_support:
                setToolbarTitle(getResources().getString(R.string.support));
                bottomNavigation.getMenu().findItem(R.id.id_support).setChecked(true);
                fmanager.beginTransaction().replace(R.id.fragment_container, new SupportFragment()).commit();
                break;
            case R.id.id_sync:
                syncData();
                break;
            case R.id.id_about:
                setToolbarTitle(getResources().getString(R.string.about));
                bottomNavigation.getMenu().findItem(R.id.id_about).setChecked(true);
                fmanager.beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).commit();
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
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setActivity(this);
        Bundle args = new Bundle();
        args.putString(SEARCH_QUERY, query);
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

    private void syncData() {
        if (NetworkHelper.isNetworkConnected(context)) {
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
        flushDataBase();
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<TableTitle>> call = data.getTitles(Auth.getToken(context));
        progressDialog.setMessage(getResources().getString(R.string.getting_titles));
        call.enqueue(new Callback<List<TableTitle>>() {
            @Override
            public void onResponse(Call<List<TableTitle>> call, Response<List<TableTitle>> response) {
                if (response.body() != null) {
                    DataBaseHelper.saveTitles(context, response.body());
                    for (int i = 0; i < response.body().size(); i++) {
                        StorageHelper.FileSpec imageFile = new StorageHelper.FileSpec(context, response.body().get(i).picUrl, StorageHelper.FileType.TITLES);
                        toDownloadImages.add(imageFile);
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
        progressDialog.setMessage(getResources().getString(R.string.getting_subtitles));
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<TableSubTitle>> callSubtitle = data.getSubtitles(Auth.getToken(context));
        callSubtitle.enqueue(new Callback<List<TableSubTitle>>() {
            @Override
            public void onResponse(Call<List<TableSubTitle>> call, Response<List<TableSubTitle>> response) {
                if (response.body() != null) {
                    DataBaseHelper.saveSubtitles(context, response.body());
                    for (int i = 0; i < response.body().size(); i++) {
                        StorageHelper.FileSpec imageFile = new StorageHelper.FileSpec(context, response.body().get(i).picUrl, StorageHelper.FileType.SUBTITLES);
                        toDownloadImages.add(imageFile);
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
        progressDialog.setMessage(getResources().getString(R.string.getting_medias));
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<TableMedia>> getMediaCall = data.getMedias(Auth.getToken(context));
        getMediaCall.enqueue(new Callback<List<TableMedia>>() {
            @Override
            public void onResponse(Call<List<TableMedia>> call, Response<List<TableMedia>> response) {
                if (response.body() != null) {
                    DataBaseHelper.saveMedias(context, response.body());
                    //download image files of titles and subtitles
                    downloadFiles(toDownloadImages);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<TableMedia>> call, Throwable t) {
                Toast.makeText(context, getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
//                finish();
            }
        });
    }

    /**
     * downloading medias
     */
    private void downloadFiles(List<StorageHelper.FileSpec> fileSpecs) {
        new DownloadFile(context, fileSpecs).execute();

    }

    private void flushDataBase() {
        DataBaseHelper.deleteAllTitles(context);
        DataBaseHelper.deleteAllSubtitles(context);
        DataBaseHelper.deleteAllMedias(context);
    }

    public void openFragment(int titleId) {
        Fragment titleFragment = new TitlesFragment();
        Bundle args = new Bundle();
        args.putInt(PREF_STATE, TitlesFragment.SUBTITLES);
        args.putInt(PREF_TITLE_ID, titleId);
        titleFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, titleFragment).commit();
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
}
