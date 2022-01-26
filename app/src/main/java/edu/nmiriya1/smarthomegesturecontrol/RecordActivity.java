package edu.nmiriya1.smarthomegesturecontrol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordActivity extends AppCompatActivity {

    String[] gestureList;
    String[] expertVideoList;
    int gestureIndex;
    String uriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

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

        Button recordFirstBtn = (Button) findViewById(R.id.recordFirstBtn);
        Button recordSecondBtn = (Button) findViewById(R.id.recordSecondBtn);
        Button recordThirdBtn = (Button) findViewById(R.id.recordThirdBtn);
        Button firstAttemptBtn = (Button) findViewById(R.id.firstAttemptBtn);
        Button secondAttemptBtn = (Button) findViewById(R.id.secondAttemptBtn);
        Button thirdAttemptBtn = (Button) findViewById(R.id.thirdAttemptBtn);

        Intent recordActivity = getIntent();
        String gestureIndexString = recordActivity.getStringExtra("gestureIndex");
        gestureIndex = Integer.parseInt(gestureIndexString);
        TextView recordGestureTextView = (TextView) findViewById(R.id.recordGestureTextView);
        recordGestureTextView.setText("Record your gesture for " + gestureList[gestureIndex]);

        recordFirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uriString = "/storage/self/primary/Android/data/edu.nmiriya1.smarthomegesturecontrol/files/" + gestureList[gestureIndex] + "_Practice_1.mp4";
                Toast.makeText(RecordActivity.this, "Please record the first attempt", Toast.LENGTH_SHORT).show();
                startRecording(uriString);
            }
        });

        recordSecondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uriString = "/storage/self/primary/Android/data/edu.nmiriya1.smarthomegesturecontrol/files/" + gestureList[gestureIndex] + "_Practice_2.mp4";
                Toast.makeText(RecordActivity.this, "Please record the second attempt", Toast.LENGTH_SHORT).show();
                startRecording(uriString);
            }
        });

        recordThirdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uriString = "/storage/self/primary/Android/data/edu.nmiriya1.smarthomegesturecontrol/files/" + gestureList[gestureIndex] + "_Practice_3.mp4";
                Toast.makeText(RecordActivity.this, "Please record the third attempt", Toast.LENGTH_SHORT).show();
                startRecording(uriString);
            }
        });

        firstAttemptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent displayVideoActivity = new Intent(getApplicationContext(), DisplayVideoActivity.class);
                displayVideoActivity.putExtra("videoName", gestureList[gestureIndex] + "_Practice_" + String.valueOf(1));
                startActivity(displayVideoActivity);
            }
        });

        secondAttemptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent displayVideoActivity = new Intent(getApplicationContext(), DisplayVideoActivity.class);
                displayVideoActivity.putExtra("videoName", gestureList[gestureIndex] + "_Practice_" + String.valueOf(2));
                startActivity(displayVideoActivity);
            }
        });

        thirdAttemptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent displayVideoActivity = new Intent(getApplicationContext(), DisplayVideoActivity.class);
                displayVideoActivity.putExtra("videoName", gestureList[gestureIndex] + "_Practice_" + String.valueOf(3));
                startActivity(displayVideoActivity);
            }
        });

        Button uploadBtn = (Button) findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 1; i< 4; i++) {
                    String videoName = gestureList[gestureIndex] + "_Practice_" + String.valueOf(i) + ".mp4";
                    uploadFile(videoName);
                }
                Toast.makeText(RecordActivity.this, "Successfully uploaded the " + gestureList[gestureIndex] + " Gesture to Cloud", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void startRecording(String uriString) {
        File mediaFile = new File(uriString);
        Uri uri = FileProvider.getUriForFile(RecordActivity.this, getApplicationContext().getPackageName() + ".provider", mediaFile);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,5);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, 101);
    }

    private void uploadFile(String videoName) {
        String uriString = "/storage/self/primary/Android/data/edu.nmiriya1.smarthomegesturecontrol/files/" + videoName;
        File file = new File(uriString);
        Uri fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
        // create upload service client
        FileUploadService service =
                ServiceGenerator.createService(FileUploadService.class);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("video", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "Description";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.upload(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
}