package com.usermanual.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.usermanual.R;
import com.usermanual.auth.Auth;
import com.usermanual.helper.Consts;

import java.lang.invoke.ConstantCallSite;

public class IntroActivity extends AppCompatActivity {

    private static final int TIME = 1500;

    Context context;
    ImageView logoImage;
    TextView logoText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // set an exit transition
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Explode());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);



        logoImage = (ImageView) findViewById(R.id.logo);
        logoText = (TextView) findViewById(R.id.logo_text);

        context = getApplicationContext();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent;
                if (Auth.isLoggedIn(context)) {
                    startMainActivity();
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        startActivity(intent,
                                ActivityOptions.makeSceneTransitionAnimation(IntroActivity.this).toBundle());
                    } else
                        startActivity(intent);
                    finish();
                }
            }
        }, TIME);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, VideoViewActivity.class);
        intent.putExtra(Consts.VIDEO_URL, "https://hw6.cdn.asset.aparat.com/aparat-video/c7281f0f19e968c503c22b888092d71512677223-144p__63733.mp4");
        startActivity(intent);
//        Intent intent = new Intent(context, MainActivity.class);
//        startActivity(intent);
        finish();
    }
}
