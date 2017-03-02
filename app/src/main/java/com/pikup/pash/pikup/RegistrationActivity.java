package com.pikup.pash.pikup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private Button btnSubmit;
    private Button btnRegistered;
    private EditText Email;
    private EditText PassWord;
    private ProgressBar progressBar;
    private FirebaseAuth authentication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        authentication = FirebaseAuth.getInstance();

        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        btnRegistered = (Button) findViewById(R.id.buttonAlreadyRegistered);

        Email = (EditText) findViewById(R.id.editForgetPassword);
        PassWord = (EditText) findViewById(R.id.editPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        FirebaseUser user = authentication.getCurrentUser();

        btnRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Email.getText().toString().trim();
                String password = PassWord.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password ", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
               // progressBar.isShown();

                authentication.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegistrationActivity.this, "Authentication Successfull", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "Authentication failed",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}