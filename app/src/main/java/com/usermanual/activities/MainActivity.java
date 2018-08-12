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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.usermanual.R;
import com.usermanual.fragments.AboutUsFragment;
import com.usermanual.fragments.NewsFragment;
import com.usermanual.fragments.TitlesFragment;
import com.usermanual.helper.BottomNavigationViewHelper;
import com.usermanual.helper.NetworkHelper;
import com.usermanual.helper.SaveToDB;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";


    BottomNavigationView bottomNavigation;
    TitlesFragment titlesFragment;
    ProgressDialog progressDialog;

    boolean loadingData;

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
        manager.beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();

        if (NetworkHelper.isNetworkConnected(getApplicationContext())) {
            loadingData = true;
            new SaveToDB(this, progressDialog).execute();
        } else
            manager.beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.id_home:
                        if (!loadingData) {
                            FragmentTransaction fragmentTransaction = manager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, titlesFragment).commit();
                        }
                        break;
                    case R.id.id_news:
                        FragmentTransaction fragmentTransaction1 = manager.beginTransaction();
                        fragmentTransaction1.replace(R.id.fragment_container, new NewsFragment()).commit();
                        break;
                    case R.id.id_about:
                        FragmentTransaction fragmentTransaction2 = manager.beginTransaction();
                        fragmentTransaction2.replace(R.id.fragment_container, new AboutUsFragment()).commit();
                        break;
                }
                return true;
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (titlesFragment.isVisible()) {
            if (titlesFragment.onBackPressed())
                super.onBackPressed();
        } else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void onDataLoaded(boolean success) {
        if (success) {
            loadingData = false;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();
        }
        if (!success) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(getResources().getString(R.string.reciving_data_failed))
                    .setTitle(getResources().getString(R.string.failed_title))
                    .setPositiveButton(getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new SaveToDB(MainActivity.this, progressDialog).execute();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
