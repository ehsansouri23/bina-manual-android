package com.usermanual.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.usermanual.R;
import com.usermanual.helper.PrefHelper;

import java.io.File;

import static com.usermanual.helper.Consts.*;

public class VideoViewActivity extends AppCompatActivity {
    private static final String TAG = "VideoViewActivity";

    String fileName;
    VideoView videoView;
    MediaController mediaController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);

        fileName = getIntent().getStringExtra(VIDEO_FILE_NAME);

        File f  = new File(fileName);
        mediaController = new MediaController(getApplicationContext());
        videoView.setVideoURI(Uri.fromFile(f));
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
    }
}
