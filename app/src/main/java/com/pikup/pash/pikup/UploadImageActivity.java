package com.pikup.pash.pikup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    public static final String KEY_CATEGORY="category";

    private ImageButton buttonCaptureImage;
    private TextView buttonCaptureText;
    private ProgressBar progressBar;
    private Button buttonSubmitItem;
    private Uri uri;
    private Toolbar toolbar;

    private EditText postName, postDescr, postLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_CATEGORY)) {
            itemCategory = intent.getStringExtra(KEY_CATEGORY);
        }

        // Init things
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDB = FirebaseDatabase.getInstance().getReference();
        myStorage = FirebaseStorage.getInstance().getReference();

        buttonCaptureImage = (ImageButton) findViewById(R.id.buttonCaptureImage);
        progressBar = (ProgressBar) findViewById(R.id.caputeImageProgressbar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonCaptureText = (TextView) findViewById(R.id.buttonCaptureText);

        postName = (EditText) findViewById(R.id.post_name);
        postDescr = (EditText) findViewById(R.id.post_descr);
        postLocation = (EditText) findViewById(R.id.post_location);

        // if the user submit a button without inputting the post name, post description,
        // post location, then it toast a message requesting user to input
        // the below information.
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

        // Upload the image from the device
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
    // if the request code and result code match
    // if the data is not null
    // then uri is set to the get data.
    // otherwise it produce the error message
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

    // No need to create filepath to save new image to, since it already exists
    // We do need a unique name to save to Firebase storage though
    // So use time to make unique filename
    private String createImageFileName() {
        return System.currentTimeMillis() / 100 + "";
    }

    // Method to upload post
    private void uploadThings() {
        image_path = createImageFileName();
        StorageReference filepath = myStorage.child("PikUpPhotos").child(image_path);
        try {
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { /* nothing */}
            });
        } catch (IllegalArgumentException iae) {
            Toast.makeText(this, "Please include a picture", Toast.LENGTH_SHORT)
                    .show();
            iae.printStackTrace();
            return;
        }

        // Database references
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String pid = myDB.child("Posts").push().getKey();
        // Make post using model class
        Post p = new Post(
                postName.getText().toString(),
                postLocation.getText().toString(),
                image_path,
                uid,
                itemCategory);
        // Upload to general posts branch
        // and personal user posts branch
        myDB.child("/Posts/" + pid).setValue(p);
        myDB.child("/User-Posts/" + uid + "/" + pid).setValue(p);


        startActivity(new Intent(UploadImageActivity.this, HomeActivity.class));
        Toast.makeText(UploadImageActivity.this, "Thanks for Giving", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            startActivity(new Intent(UploadImageActivity.this, HomeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}