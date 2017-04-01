//package com.pikup.pash.pikup;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ProgressBar;
//
//import com.google.firebase.storage.StorageReference;
//
//public class PostActivity extends AppCompatActivity {
//    private Button captureButton;
//    private Button uploadButton;
//    private ProgressBar progressBar;
//    private StorageReference myStorage;
//
//    private static final int GALLERY_INTENT = 2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_post);
//
//      //  myStorage = FirebaseStorage.getInstance().getReference();
//        uploadButton = (Button) findViewById(R.id.buttonUpload);
//        captureButton = (Button) findViewById(R.id.buttonCaptureImage);
//
//        captureButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(PostActivity.this, ChooseCategoryActivity.class));
//
//            }
//        });
//
//        uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(PostActivity.this, UploadImageActivity.class));
//            }
//        });
//    }
//}
//
//
//
//
//
//
package com.pikup.pash.pikup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.storage.StorageReference;

public class PostActivity extends AppCompatActivity {
    private Button captureButton;
    private Button uploadButton;
    private ProgressBar progressBar;
    private StorageReference myStorage;
    private String itemCategory;

    private static final int GALLERY_INTENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        uploadButton = (Button) findViewById(R.id.buttonUpload);
        captureButton = (Button) findViewById(R.id.buttonCaptureImage);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, ChooseCategoryActivity.class));

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, UploadImageActivity.class));
            }
        });
    }
}



