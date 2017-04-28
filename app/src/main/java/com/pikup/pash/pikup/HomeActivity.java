package com.pikup.pash.pikup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        //Getting Firebase authentication instance
        auth = FirebaseAuth.getInstance();

        //if user is null then intent to the Login Activity page
        if (auth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //initializing the fireabse user to get the auth of current user
        FirebaseUser user = auth.getCurrentUser();

        // When the user is login, or open the app, is toast a welcome mesage
        Toast.makeText(getApplicationContext(), "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();

        postButton = (Button) findViewById(R.id.buttonForgetBack);
        viewButton = (Button) findViewById(R.id.buttonForgetSubmit);
        logoutButton = (Button) findViewById(R.id.buttonLogout);
        userInfoButton = (Button) findViewById(R.id.locSubmit);

        // UI button will intent to user infromation page
        userInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(HomeActivity.this, UserInformationActivity.class)));
            }
        });

        // Post button will intent to user Choose category activity where user can upload and capture an item
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ChooseCategoryActivity.class));
            }
        });

        // View button will intent to user View activity activity where user can view the uploaded item
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ViewActivity.class));
            }
        });

        //User logout from the app
        logoutButton.setOnClickListener(this);
    }
    //method for when user logout it toast a message and takes to the main activity
    @Override
    public void onClick(View view){
        if (view == logoutButton){
            auth.signOut();
            finish();
            startActivity(new Intent(HomeActivity.this, MainActivity.class
            ));

            Toast.makeText(getApplicationContext(), "See you soon", Toast.LENGTH_SHORT).show();

        }
    }
}