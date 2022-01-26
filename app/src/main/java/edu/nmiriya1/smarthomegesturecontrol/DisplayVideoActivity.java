package edu.nmiriya1.smarthomegesturecontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class DisplayVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_video);

        Intent displayVideoActivity = getIntent();
        String videoName = displayVideoActivity.getStringExtra("videoName");
        String uriString = "/storage/self/primary/Android/data/edu.nmiriya1.smarthomegesturecontrol/files/" + videoName +".mp4";
        File mediaFile = new File(uriString);
        Uri uri = FileProvider.getUriForFile(DisplayVideoActivity.this, getApplicationContext().getPackageName() + ".provider", mediaFile);

        VideoView recordedVideoView = (VideoView) findViewById(R.id.displayVideoView);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(recordedVideoView);

        recordedVideoView.setMediaController(mediaController);
        recordedVideoView.setVideoURI(Uri.parse(uriString));
        recordedVideoView.requestFocus();
        recordedVideoView.start();
    }
}