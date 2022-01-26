package edu.nmiriya1.smarthomegesturecontrol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF018786"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        String[] gestureList = getResources().getStringArray(R.array.gestureList);
        GestureListAdapter gestureListAdapter = new GestureListAdapter(gestureList, getApplicationContext());
        Spinner gestureListSpinner = (Spinner) findViewById(R.id.gestureListSpinner);
        gestureListSpinner.setAdapter(gestureListAdapter);

        gestureListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0) {
                    Intent practiceActivity = new Intent(getApplicationContext(), PracticeActivity.class);
                    practiceActivity.putExtra("gestureIndex", String.valueOf(i));
                    startActivity(practiceActivity);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });
    }
}