package com.pikup.pash.pikup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class PasswordResetActivity extends AppCompatActivity {
    //private EditText editEmail;
    private EditText editOldPassword;
    private EditText editNewPassword;
    private Button buttonSubmit;
    private Button buttonBack;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        //editEmail = (EditText) findViewById(R.id.editForgetPassword);
        editOldPassword = (EditText) findViewById(R.id.editResetOldEmail);
        editNewPassword = (EditText) findViewById(R.id.editRestNewEmail);
        buttonSubmit = (Button) findViewById(R.id.buttonEmailSubmit);
        buttonBack = (Button) findViewById(R.id.buttonEmailReset);
        progressBar = (ProgressBar) findViewById(R.id.ResetProgressBar);
        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOldPassword.setVisibility(View.GONE);
                editNewPassword.setVisibility(View.VISIBLE);
                buttonSubmit.setVisibility(View.VISIBLE);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !editNewPassword.getText().toString().trim().equals("")) {
                    if (editNewPassword.getText().toString().trim().length() < 6) {
                        editNewPassword.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(editNewPassword.getText().toString().trim())

                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(PasswordResetActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(PasswordResetActivity.this, LoginActivity.class));
                                            finish();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(PasswordResetActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(PasswordResetActivity.this, PasswordResetActivity.class));
                                            finish();
                                        }
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    }
                } else if (editNewPassword.getText().toString().trim().equals("")) {
                    editNewPassword.setError("Enter password");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });




    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
