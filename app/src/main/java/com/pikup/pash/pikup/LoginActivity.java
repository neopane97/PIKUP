package com.pikup.pash.pikup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;
    private FirebaseAuth auth;
    private Button buttonLogin;
    private Button buttonForget;
    private Button buttonNotRegistered;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Getting firebase auth instance
        auth = FirebaseAuth.getInstance();

        //if the current user is not null then it intent to homeActivity
        if(auth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        setContentView(R.layout.activity_login);

        userEmail = (EditText) findViewById(R.id.editUserName);
        userPassword = (EditText) findViewById(R.id.editPassword);
        buttonLogin = (Button) findViewById(R.id.buttonForgetSubmit);
        buttonForget = (Button) findViewById(R.id.buttonForget);
        buttonNotRegistered = (Button) findViewById(R.id.buttonNotRegister);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        //Getting firebase auth instance
        auth = FirebaseAuth.getInstance();

        //Button Registered intent to the Registration Activity class
        buttonNotRegistered.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        //Button Forget intent to the ForgetActivity class
        buttonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
            }
        });

        //it checks the cases on each login input, such as validation or requirements of user input.
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String editEmail = userEmail.getText().toString();
                final String editPassword = userPassword.getText().toString();

                if(TextUtils.isEmpty(editEmail)){
                    Toast.makeText(getApplicationContext(), "Enter Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editPassword)){
                    Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //authenticate user
                auth.signInWithEmailAndPassword(editEmail, editPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds then the auth state listener will be
                                // notified and logic to handle the signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                //If there is an error exist then it display the following error, else it jump to Home Activity.
                                if(!task.isSuccessful()) {
                                    //There was an error
                                    if (editPassword.length() < 6) {
                                        userPassword.setError("Input password greater then six character");
                                    } else {
                                        Toast.makeText(LoginActivity.this, ("Authentication Failed"), Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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