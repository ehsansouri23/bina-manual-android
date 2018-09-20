package com.usermanual.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eyalbira.loadingdots.LoadingDots;
import com.usermanual.R;
import com.usermanual.auth.Auth;
import com.usermanual.helper.dbmodels.LoginModel;
import com.usermanual.helper.dbmodels.LoginResponse;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    Context context;
    TextInputLayout userName, passWord;
    Button login;
    LoadingDots loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();

        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // set an exit transition
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Explode());


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
}
