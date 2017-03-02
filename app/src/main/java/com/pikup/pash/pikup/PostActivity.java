package com.pikup.pash.pikup;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {
    private Button captureButton;
    private Button uploadButton;
    private ProgressBar progressBar;
    private StorageReference myStorage;
    private static final int GALLERY_INTENT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        myStorage = FirebaseStorage.getInstance().getReference();
        uploadButton = (Button) findViewById(R.id.buttonUpload);
        captureButton = (Button) findViewById(R.id.buttonCaptureImage);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, CaptureImageActivity.class));

            }
        });

        progressBar = (ProgressBar) findViewById(R.id.PostProgressBar);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            progressBar.setVisibility(View.VISIBLE);
            Uri uri = data.getData();
            StorageReference filepath = myStorage.child("PickUpPhotos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(PostActivity.this, "Thanks For Giving", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
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
