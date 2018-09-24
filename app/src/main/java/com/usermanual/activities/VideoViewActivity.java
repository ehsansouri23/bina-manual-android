package com.usermanual.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.usermanual.R;
import com.usermanual.helper.StorageHelper;

import java.io.File;

import static com.usermanual.helper.Consts.*;

public class VideoViewActivity extends AppCompatActivity {
    private static final String TAG = "VideoViewActivity";

    String fileKey;
    String videoUrl;
    VideoView videoView;
    MediaController mediaController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        videoView = (VideoView) findViewById(R.id.video_view);

        fileKey = getIntent().getStringExtra(VIDEO_FILE_KEY);
        videoUrl = getIntent().getStringExtra(VIDEO_URL);

        mediaController = new MediaController(VideoViewActivity.this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        if (videoUrl != null) {
            videoView.setVideoPath(videoUrl);
        } else {
            if (fileKey == null) {
                    fileKey = "";
            }
            File f = StorageHelper.getFile(getApplicationContext(), fileKey);
            videoView.setVideoURI(Uri.fromFile(f));
        }

        videoView.start();
    }
}
