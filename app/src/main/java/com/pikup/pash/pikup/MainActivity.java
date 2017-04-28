package com.pikup.pash.pikup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegister;
    private TextView loginLink;
    private TextView registerLink;

    private FirebaseAuth auth;

    private final int PERMISSIONS_CODE = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checks the permission
        checkPermissions();

        //when the user click on login button it intent to login activity
        btnLogin = (Button) findViewById(R.id.buttonForgetSubmit);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        //when the user click on register button it intent to Registration activity
        btnRegister = (Button) findViewById(R.id.buttonForgetBack);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }

    // Permission checked for storage/camera
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission_group.STORAGE) +
            ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission_group.STORAGE, Manifest.permission.CAMERA},
                    PERMISSIONS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}