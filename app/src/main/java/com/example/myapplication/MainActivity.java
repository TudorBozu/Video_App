package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    // Constant for saving/restoring the video position
    private static final String VIDEO_POSITION = "video_position";

    private VideoView mVideoView;
    private int mVideoPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonPlayVideo = findViewById(R.id.buton);
        mVideoView = findViewById(R.id.view);
        getWindow().setFormat(PixelFormat.UNKNOWN);

        // Set the URI path of the video
        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.ed;
        Uri uri = Uri.parse(uriPath);
        mVideoView.setVideoURI(uri);

        buttonPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.start();
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
