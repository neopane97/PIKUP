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

//        buttonCaptureImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, GALLERY_INTENT);
//            }
//        });
		buttonCaptureImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT);
//				File image = null;
//				try {
//					image = createImageFile();
//				} catch (IOException e) {
//					e.printStackTrace();
//					finish();
//				}
//				if (image != null) {
//					uri = FileProvider.getUriForFile(UploadImageActivity.this,
//							"com.example.android.fileprovider",
//							image);
//					intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//					List<ResolveInfo> resolveInfos = getApplicationContext()
//							.getPackageManager()
//							.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//					for (ResolveInfo ri : resolveInfos) {
//						String packageName = ri.activityInfo.packageName;
//						getApplicationContext().grantUriPermission(packageName, uri,
//								Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
//										Intent.FLAG_GRANT_READ_URI_PERMISSION);
//					}
//					startActivityForResult(intent, GALLERY_INTENT);
//				}
			}
		});

	}

//        @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
//            // progressBar.setVisibility(View.VISIBLE);
//
//            Uri uri = data.getData();
//            StorageReference filepath = myStorage.child("PickUpPhotos").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(UploadImageActivity.this, "Thanks For Giving", Toast.LENGTH_LONG).show();
//                    //   progressBar.setVisibility(View.GONE);
//                }
//            });
//        }
//    }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
			if (data != null) {
				uri = data.getData();
				Log.d("URI", "uri is " + uri.toString());
//				try {
//					Bitmap bitmap = MediaStore.Images.Media
//							.getBitmap(getApplicationContext().getContentResolver(), uri);
//				} catch (IOException e) {
//					e.printStackTrace();
//					Toast.makeText(getApplicationContext(), "Check stacktrace", Toast.LENGTH_SHORT)
//							.show();
//				}
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
//		getApplicationContext().revokeUriPermission(uri,
//				Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
//						Intent.FLAG_GRANT_READ_URI_PERMISSION);
		//noinspection ConstantConditions
		Post p = new Post(
				postName.getText().toString(),
				postDescr.getText().toString(),
				postLocation.getText().toString(),
				image_path,
				FirebaseAuth.getInstance().getCurrentUser().getUid());
		//Map<String, String> pm = p.toMap();
		//Log.d("POST", pm.toString());
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


//        uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, GALLERY_INTENT);
//            }
//        });
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
//            // progressBar.setVisibility(View.VISIBLE);
//
//            Uri uri = data.getData();
//            StorageReference filepath = myStorage.child("PickUpPhotos").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(PostActivity.this, "Thanks For Giving", Toast.LENGTH_LONG).show();
//                    //   progressBar.setVisibility(View.GONE);
//                }
//            });
//        }
//    }