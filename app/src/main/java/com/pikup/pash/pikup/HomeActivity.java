package com.pikup.pash.pikup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button postButton;
    private Button viewButton;
    private Button logoutButton;
    private Button userInfoButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitleTextColor(Color.WHITE);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Home");
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = auth.getCurrentUser();

		Toast.makeText(getApplicationContext(), "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();

        postButton = (Button) findViewById(R.id.buttonPost);
        viewButton = (Button) findViewById(R.id.buttonView);
        logoutButton = (Button) findViewById(R.id.buttonLogout);
        userInfoButton = (Button) findViewById(R.id.userInfoButton);

        userInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(HomeActivity.this, UserInformationActivity.class)));
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, PostActivity.class));
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ViewActivity.class));
            }
        });

        logoutButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        if (view == logoutButton){
            auth.signOut();
            finish();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class
            ));
        }
    }
}