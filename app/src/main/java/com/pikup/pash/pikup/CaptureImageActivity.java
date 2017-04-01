package com.pikup.pash.pikup;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
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

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.view.View.GONE;
import static com.pikup.pash.pikup.ChooseCategoryActivity.KEY_CATEGORY;

public class CaptureImageActivity extends AppCompatActivity {
    private DatabaseReference myDB;
    private StorageReference myStorage;
    private String image_path;
    private String itemCategory;

    private static final int CAMERA_REQUEST_CODE = 1;

    private ImageButton buttonCaptureImage;
    private TextView buttonCaptureText;
    private ProgressBar progressBar;
    private Button buttonSubmitItem;
    private Uri uri;

    private EditText postName, postDescr, postLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
/**/
        Intent intent = getIntent();
        if (null != intent) {
            itemCategory = intent.getStringExtra(KEY_CATEGORY);
            //Toast.makeText(CaptureImageActivity.this, itemCategory, Toast.LENGTH_LONG).show();

        }//end if

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
        StorageReference filepath = myStorage.child("PikUpPhotos").child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { /* nothing */}
        });
        String key = myDB.child("Posts").push().getKey();
        image_path = uri.getLastPathSegment();
        getApplicationContext().revokeUriPermission(uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Toast.makeText(CaptureImageActivity.this, itemCategory, Toast.LENGTH_LONG).show();

        Post p = new Post(
                postName.getText().toString(),
                postDescr.getText().toString(),
                postLocation.getText().toString(),
                image_path,
                itemCategory,
                FirebaseAuth.getInstance().getCurrentUser().getUid());

        myDB.child("/Posts/" + key).setValue(p);

        startActivity(new Intent(CaptureImageActivity.this,PostActivity.class));
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

}