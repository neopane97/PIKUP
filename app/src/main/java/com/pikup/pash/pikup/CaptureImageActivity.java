package com.pikup.pash.pikup;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CaptureImageActivity extends AppCompatActivity {

    private ImageView captureImageView;
    private Button buttonCaptureImage;
    private static final int CAMERA_REQUEST_CODE = 1;
    private StorageReference myStorage;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        myStorage = FirebaseStorage.getInstance().getReference();
        captureImageView = (ImageView) findViewById(R.id.captureImageView);
        buttonCaptureImage = (Button) findViewById(R.id.buttonCaptureImage);
        progressBar = (ProgressBar) findViewById(R.id.caputeImageProgressbar);

        buttonCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && requestCode == RESULT_OK){
            progressBar.setVisibility(View.VISIBLE);
            Uri uri = data.getData();
            StorageReference filepath = myStorage.child("PickUp Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CaptureImageActivity.this, "Thanks for Giving", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
