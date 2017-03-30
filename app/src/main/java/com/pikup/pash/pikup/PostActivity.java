package com.pikup.pash.pikup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PostActivity extends AppCompatActivity {
	private StorageReference myStorage;
	private Uri uri;

	private static final int GALLERY_INTENT = 2;

    private Button captureButton;
    private Button uploadButton;


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
				finish();
            }
        });


		uploadButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PostActivity.this, UploadImageActivity.class));
				finish();
			}
		});
    }

}













