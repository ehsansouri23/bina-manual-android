package com.usermanual.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.eyalbira.loadingdots.LoadingDots;
import com.usermanual.R;
import com.usermanual.auth.Auth;
import com.usermanual.dbmodels.LoginModel;
import com.usermanual.dbmodels.LoginResponse;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    Context context;
    TextInputLayout userName, passWord;
    Button login;
    LoadingDots loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // set an exit transition
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Explode());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        checkPermissions();

        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.e(TAG, "imei: " + telephonyManager.getDeviceId());


        if (Auth.isLoggedIn(context)) {
            startMainActivity();
        }

        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        userName = (TextInputLayout) findViewById(R.id.username);
        passWord = (TextInputLayout) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        loading = (LoadingDots) findViewById(R.id.loading);

        userName.setHint(getResources().getString(R.string.username));
        passWord.setHint(getResources().getString(R.string.password));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getEditText().getText().toString().equals("")) {
                    userName.setError(getResources().getString(R.string.should_not_empty));
                    return;
                }
                if (passWord.getEditText().getText().toString().equals("")) {
                    passWord.setError(getResources().getString(R.string.should_not_empty));
                    return;
                }
                loading.setVisibility(View.VISIBLE);
                LoginModel loginModel = new LoginModel();
                loginModel.userName = String.valueOf(userName.getEditText().getText());
                loginModel.password = String.valueOf(passWord.getEditText().getText());
                if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                loginModel.imei = telephonyManager.getDeviceId();

                Call<LoginResponse> loginCall = data.login(loginModel);
                loginCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body() != null) {
                            loading.setVisibility(View.GONE);
                            if (response.body().result) {
                                Auth.login(context, response.body());
                                startMainActivity();
                            } else {
                                Toast.makeText(context, response.body().error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(context, getResources().getString(R.string.no_net), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    private void startMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, 112);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(112, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

}
