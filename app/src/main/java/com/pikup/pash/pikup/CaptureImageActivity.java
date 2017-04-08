package com.pikup.pash.pikup;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.view.View.GONE;

public class CaptureImageActivity extends AppCompatActivity {
    private DatabaseReference myDB;
    private StorageReference myStorage;
    private String image_path;
    private String itemCategory;

    private static final int CAMERA_REQUEST_CODE = 1;
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
        setContentView(R.layout.activity_capture_image);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/**/
        Intent intent = getIntent();
        if (intent.hasExtra(KEY_CATEGORY)) {
            itemCategory = intent.getStringExtra(KEY_CATEGORY);

        }

/**/
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
                    Toast.makeText(CaptureImageActivity.this, "Please enter a name", Toast.LENGTH_SHORT)
                            .show();
                } else if (postDescr.getText() == null) {
                    Toast.makeText(CaptureImageActivity.this, "Please enter a description", Toast.LENGTH_SHORT)
                            .show();
                } else if (postLocation.getText() == null) {
                    Toast.makeText(CaptureImageActivity.this, "Please enter a location", Toast.LENGTH_SHORT)
                            .show();
                } else{
                    uploadThings();
                }
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
                    e.printStackTrace();
                    finish();
                }
                if (image != null) {
                    uri = FileProvider.getUriForFile(CaptureImageActivity.this,
                            "com.example.android.fileprovider",
                            image);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    List<ResolveInfo> resolveInfos = getApplicationContext()
                            .getPackageManager()
                            .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo ri : resolveInfos) {
                        String packageName = ri.activityInfo.packageName;
                        getApplicationContext().grantUriPermission(packageName, uri,
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            buttonCaptureImage.setImageURI(uri);
            buttonCaptureText.setVisibility(GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    private File createImageFile() throws IOException {
        String file_name = System.currentTimeMillis() / 1000 + "";
        File dir = getExternalFilesDir(null);
        File image = File.createTempFile(file_name, ".png", dir);
        return image;
    }


    private void uploadThings() {
        try {
            StorageReference filepath = myStorage.child("PikUpPhotos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { /* nothing */}
            });
            image_path = uri.getLastPathSegment();
        } catch (NullPointerException npe) {
            Toast.makeText(this, "Please include a picture", Toast.LENGTH_SHORT)
                    .show();
            npe.printStackTrace();
            return;
        }
        getApplicationContext().revokeUriPermission(uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String pid = myDB.child("Posts").push().getKey();
        //noinspection ConstantConditions
        Post p = new Post(
                postName.getText().toString(),
                postLocation.getText().toString(),
                image_path,
                uid,
                itemCategory);
        myDB.child("/Posts/" + pid).setValue(p);
        myDB.child("/User-Posts/" + uid + "/" + pid).setValue(p);

        startActivity(new Intent(CaptureImageActivity.this, HomeActivity.class));
        Toast.makeText(CaptureImageActivity.this, "Thanks for Giving", Toast.LENGTH_LONG).show();
        finish();
    }


    public String getItemCategory()
    {return itemCategory;}

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
            startActivity(new Intent(CaptureImageActivity.this, HomeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


}