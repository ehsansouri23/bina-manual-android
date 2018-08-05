package com.usermanual.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);

        titlesFragment = new TitlesFragment();
        final FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, titlesFragment).commit();

        if (NetworkHelper.isNetworkConnected(getApplicationContext())) {
            new SaveToDB(this, progressDialog).execute();
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



    }

    @Override
    public void onBackPressed() {
        if (titlesFragment.isVisible())
            if (titlesFragment.onBackPressed())
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
}
