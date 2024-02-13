package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private static final String VIDEO_POSITION = "video_position";

    private VideoView mVideoView;
    private int mVideoPosition = 0;

    Boolean isPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonPlayVideo = findViewById(R.id.buton);
        Button buttonrepaly = findViewById(R.id.buttonreplay);
        mVideoView = findViewById(R.id.video_view);

        // Set the URI path of the video
        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.ed;
        Uri uri = Uri.parse(uriPath);
        MediaController mediaController = new MediaController(this);
        mVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(mVideoView);

        mVideoView.setVideoURI(uri);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        buttonPlayVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isPlay) {
                    isPlay = false;
                    mVideoView.pause();
                } else {
                    isPlay = true;
                    mVideoView.start();
                }

            }
        });

        buttonrepaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.resume();
            }
        });
        if (savedInstanceState != null) {
            mVideoPosition = savedInstanceState.getInt(VIDEO_POSITION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.seekTo(mVideoPosition);
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoPosition = mVideoView.getCurrentPosition();
        mVideoView.pause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current video playback position
        outState.putInt(VIDEO_POSITION, mVideoPosition);
    }
}