package edu.nmiriya1.smarthomegesturecontrol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.resources.TextAppearance;

public class PracticeActivity extends AppCompatActivity {

    private String[] gestureList;
    private String[] expertVideoList;
    private int countOfRetries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF018786"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        gestureList = getResources().getStringArray(R.array.gestureList);
        expertVideoList = getResources().getStringArray(R.array.recordingList);

        Intent practiceIntent = getIntent();
        String gestureIndexString = practiceIntent.getStringExtra("gestureIndex");
        int gestureIndex = Integer.parseInt(gestureIndexString);
        TextView gesturePracticeTextView = (TextView) findViewById(R.id.gesturePracticeTextView);
        gesturePracticeTextView.setText("Please go through the the expert video for \"" + gestureList[gestureIndex] + "\" Gesture, and click on Pratice to record your video");

        displayExpertVideo(gestureIndex);

        Button practiceBtn = (Button) findViewById(R.id.practiceBtn);
        practiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recordActivity = new Intent(getApplicationContext(), RecordActivity.class);
                recordActivity.putExtra("gestureIndex", gestureIndexString);
                startActivity(recordActivity);
                finish();
            }
        });

        Button replayBtn = (Button) findViewById(R.id.replayBtn);
        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countOfRetries < 3) {
                    countOfRetries++;
                    displayExpertVideo(gestureIndex);
                } else {
                    Toast.makeText(PracticeActivity.this, "Reached Maximum Retry Limit", Toast.LENGTH_SHORT).show();
                    replayBtn.setEnabled(false);
                }
            }
        });
    }

    private void displayExpertVideo(int gestureIndex) {
        VideoView gestureVideoView = (VideoView) findViewById(R.id.gestureVideoView);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(gestureVideoView);
        String uriString = "/storage/self/primary/Android/data/edu.nmiriya1.smarthomegesturecontrol/files/";

        uriString = uriString + expertVideoList[gestureIndex];

        Uri uri = Uri.parse(uriString);
        gestureVideoView.setMediaController(mediaController);
        gestureVideoView.setVideoURI(uri);
        gestureVideoView.requestFocus();
        gestureVideoView.start();
    }
}