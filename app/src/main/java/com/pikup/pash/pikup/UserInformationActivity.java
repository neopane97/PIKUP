package com.pikup.pash.pikup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserInformationActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private UserInfo ui;
    private String userId;

    private static final String TAG = "UserInformationActivity";

    private Toolbar toolbar;
    //private ListView mListView;
	//private ArrayAdapter adapter;
	//private ArrayList information;
    private FrameLayout frame;
    private EditText name, address, city, state, zip, dob;
    private ProgressBar progressBar;
    private RecyclerView uiView;
    private GridLayoutManager gll;
    private UserInfoAdapter uiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

// Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

// User Info
        name = (EditText) findViewById(R.id.ui_edit_name);
        name.setEnabled(false);
        address = (EditText) findViewById(R.id.ui_edit_address);
        address.setEnabled(false);
        city = (EditText) findViewById(R.id.ui_edit_city);
        city.setEnabled(false);
        state = (EditText) findViewById(R.id.ui_edit_state);
        state.setEnabled(false);
        zip = (EditText) findViewById(R.id.ui_edit_zip);
        zip.setEnabled(false);
        dob = (EditText) findViewById(R.id.ui_edit_dob);
        dob.setEnabled(false);

// User Posts
        frame = (FrameLayout) findViewById(R.id.ui_frame);
        updateFrame(R.layout.ui_loading);

        uiAdapter = new UserInfoAdapter();

        progressBar = (ProgressBar) findViewById(R.id.ui_progressbar);
        progressBar.setVisibility(View.VISIBLE);

// Getting Data
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userId = user.getUid();

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Users/" + userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("onDataChange:init", dataSnapshot.toString());
                ui = dataSnapshot.getValue(UserInfo.class);
                //progressBar.setVisibility(View.GONE);

                uiAdapter.setPosts(userId);

				name.setText(ui.getFullName());
				address.setText(ui.getAddress());
                city.setText(ui.getCity());
				state.setText(ui.getState());
				zip.setText(ui.getZipCode());
				dob.setText(ui.getDateOfBirth());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateFrame(int layoutResId) {
        frame.removeAllViews();
        LayoutInflater.from(UserInformationActivity.this)
                .inflate(layoutResId, frame);
    }

    class UserInfoAdapter extends RecyclerView.Adapter<InfoHolder> {
        private DatabaseReference dbr;
        private ArrayList<Post> posts;

        public UserInfoAdapter() {
            this.posts = new ArrayList<>();
            dbr = FirebaseDatabase.getInstance().getReference();
        }

        public void setPosts(String uid) {
            dbr.child("User-Posts/" + uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Post p = ds.getValue(Post.class);
                        posts.add(p);
                    }
                    Log.i("Posts.size:", "" + posts.size());
                    if (posts.size() != 0) {
                        updateFrame(R.layout.ui_posts);
                        uiView = (RecyclerView) findViewById(R.id.ui_recycc);
                        gll = new GridLayoutManager(getApplicationContext(), 3);
                        uiView.setLayoutManager(gll);
                        uiView.setAdapter(uiAdapter);
                    } else
                        updateFrame(R.layout.ui_nop);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        @Override
        public InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ui_item, parent, false);
            return new InfoHolder(v);
        }

        @Override
        public void onBindViewHolder(InfoHolder holder, int position) {
            Post p = posts.get(position);
            Log.i("Post", "Position: " + p);
            String img_id = p.getImage_path();
            Log.i("ImagePath", img_id);

            holder.img.setImageResource(R.drawable.place);
            StorageReference imgRef = FirebaseStorage.getInstance()
                    .getReference()
                    .child("PikUpPhotos/" +
                            img_id);
            Log.i("ImageReference:", imgRef.toString());
            Glide.with(UserInformationActivity.this)
                    .using(new FirebaseImageLoader())
                    .load(imgRef)
                    .into(holder.img);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "////ye\\\\", Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }
    }

    static class InfoHolder extends RecyclerView.ViewHolder {
        ImageView img;
        
        public InfoHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.ui_item_img);
        }
    }

}
