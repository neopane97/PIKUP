package com.pikup.pash.pikup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.view.View.GONE;

public class UploadImageActivity extends AppCompatActivity {
    private DatabaseReference myDB;
    private StorageReference myStorage;
    private String image_path;
    private String itemCategory;

    private static final int GALLERY_INTENT = 2;

    private ImageButton buttonCaptureImage;
    private TextView buttonCaptureText;
    private ProgressBar progressBar;
    private Button buttonSubmitItem;
    private Uri uri;

    private EditText postName, postDescr, postLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        myDB = FirebaseDatabase.getInstance().getReference();
        myStorage = FirebaseStorage.getInstance().getReference();

        buttonCaptureImage = (ImageButton) findViewById(R.id.buttonCaptureImage);
        progressBar = (ProgressBar) findViewById(R.id.caputeImageProgressbar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonCaptureText = (TextView) findViewById(R.id.buttonCaptureText);

        postName = (EditText) findViewById(R.id.post_name);
        postDescr = (EditText) findViewById(R.id.post_descr);
        postLocation = (EditText) findViewById(R.id.post_location);

        buttonSubmitItem = (Button) findViewById(R.id.buttonSubmitItem);
        buttonSubmitItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postName.getText() == null) {
                    Toast.makeText(UploadImageActivity.this, "Please enter a name", Toast.LENGTH_SHORT)
                            .show();
                } else if (postDescr.getText() == null) {
                    Toast.makeText(UploadImageActivity.this, "Please enter a description", Toast.LENGTH_SHORT)
                            .show();
                } else if (postLocation.getText() == null) {
                    Toast.makeText(UploadImageActivity.this, "Please enter a location", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    uploadThings();
                }
            }
        });
        buttonCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.getData();
                Log.d("URI", "uri is " + uri.toString());
            } else {

                Toast.makeText(getApplicationContext(), "Something was null", Toast.LENGTH_SHORT)
                        .show();
            }
            buttonCaptureImage.setImageURI(uri);
            buttonCaptureText.setVisibility(GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private String createImageFileName() {
        return System.currentTimeMillis() / 100 + "";
    }


    private void uploadThings() {
        image_path = createImageFileName();
        StorageReference filepath = myStorage.child("PikUpPhotos").child(image_path);
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { /* nothing */}
        });
        String key = myDB.child("Posts").push().getKey();
        Post p = new Post(
                postName.getText().toString(),
                postDescr.getText().toString(),
                postLocation.getText().toString(),
                image_path,
                itemCategory,
                FirebaseAuth.getInstance().getCurrentUser().getUid());
        myDB.child("/Posts/" + key).setValue(p);


        startActivity(new Intent(UploadImageActivity.this, PostActivity.class));
        Toast.makeText(UploadImageActivity.this, "Thanks for Giving", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(GONE);
    }
}