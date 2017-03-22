package com.pikup.pash.pikup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class CaptureImageActivity extends AppCompatActivity {

    //private ImageView captureImageView;
    private ImageButton buttonCaptureImage;
    private TextView buttonCaptureText;
    private static final int CAMERA_REQUEST_CODE = 1;
    private StorageReference myStorage;
    private ProgressBar progressBar;
    private Button buttonSubmitItem;


    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        myStorage = FirebaseStorage.getInstance().getReference();
//        captureImageView = (ImageView) findViewById(R.id.buttonCaptureImage);
        buttonCaptureImage = (ImageButton) findViewById(R.id.buttonCaptureImage);
        progressBar = (ProgressBar) findViewById(R.id.caputeImageProgressbar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonCaptureText = (TextView) findViewById(R.id.buttonCaptureText);

        buttonSubmitItem = (Button) findViewById(R.id.buttonSubmitItem);
        buttonSubmitItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CaptureImageActivity.this, PostActivity.class));
                Toast.makeText(CaptureImageActivity.this, "Thanks for Giving", Toast.LENGTH_LONG).show();

            }
        });
        buttonCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File image = null;
                try {
                    image = createImageFile();
                } catch (IOException e) {
                    finish();
                }
                if (image != null) {
                    uri = FileProvider.getUriForFile(CaptureImageActivity.this,
                            "com.example.android.fileprovider",
                            image);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            buttonCaptureText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            StorageReference filepath = myStorage.child("PickUpPhotos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(CaptureImageActivity.this, "Thanks for Giving", Toast.LENGTH_LONG).show();
                    buttonCaptureImage.setImageURI(uri);
                }
            });
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(GONE);
    }

    private File createImageFile() throws IOException {
        String name = System.currentTimeMillis() / 1000 + "";
        File dir = getExternalFilesDir(null);
        File image = File.createTempFile(name, ".png", dir);
        //path = "file:" + image.getAbsolutePath();
        return image;
    }
}
