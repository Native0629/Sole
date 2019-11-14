package com.nav.tagger;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by Navit on 7/24/2018.
 */

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Uri vidFile = Uri.parse(getIntent().getStringExtra("video"));
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(vidFile);
        videoView.setMediaController(new MediaController(this));
        videoView.start();
    }
}
