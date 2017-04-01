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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText FirstName;
    private EditText LastName;
    private EditText Address;
    private EditText State;
    private EditText City;
    private EditText ZipCode;
    private EditText Dob;
    private EditText Email;
    private EditText PassWord;
    private EditText RePassword;
    private ProgressBar progressBar;
    private Button btnSubmit;
    private Button btnRegistered;
    private DatabaseReference mDatabase;
    private FirebaseAuth authentication;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        authentication = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        if (authentication.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }

        FirstName = (EditText) findViewById(R.id.editFirstName);
        LastName = (EditText) findViewById(R.id.editLastName);
        Address = (EditText) findViewById(R.id.editAddress);
        State = (EditText) findViewById(R.id.editAddress);
        City = (EditText) findViewById(R.id.editCity);
        ZipCode = (EditText) findViewById(R.id.editZipCode);
        Dob = (EditText) findViewById(R.id.editDateOfBirth);
        Email = (EditText) findViewById(R.id.editForgetPassword);
        PassWord = (EditText) findViewById(R.id.editPassword);
        RePassword = (EditText) findViewById(R.id.editRePassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        btnRegistered = (Button) findViewById(R.id.buttonAlreadyRegistered);

        final FirebaseUser user = authentication.getCurrentUser();

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
                startRegister();
            }
        });
    }

    private void startRegister() {
        final String firstname = FirstName.getText().toString().trim();
        final String lastname = LastName.getText().toString().trim();
        final String address = Address.getText().toString().trim();
        final String state = State.getText().toString().trim();
        final String city = City.getText().toString().trim();
        final String zipcode = ZipCode.getText().toString().trim();
        final String dob = Dob.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String password = PassWord.getText().toString().trim();
        final String repassword = RePassword.getText().toString().trim();

        if (!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) &&
                !TextUtils.isEmpty(address) && !TextUtils.isEmpty(state) &&
                !TextUtils.isEmpty(city) && !TextUtils.isEmpty(zipcode) &&
                !TextUtils.isEmpty(dob) && !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {

            progressBar.setVisibility(View.VISIBLE);
            progressBar.isShown();
            authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "Authentication Successful",
                                Toast.LENGTH_SHORT).show();
                        String userId = authentication.getCurrentUser().getUid();
                        DatabaseReference currentUser = mDatabase.child(userId);

                    UserInfo userInfo = new UserInfo(firstname, lastname, address, state, city, zipcode, dob, email);
                        currentUser.setValue(userInfo);

                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "Authentication Failed",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else {
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter Email ID", Toast.LENGTH_SHORT).show();
                return;
            }if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                return;
            }if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "At-least six characters password Required", Toast.LENGTH_SHORT).show();
                return;
            }if (TextUtils.isEmpty(repassword)) {
                Toast.makeText(getApplicationContext(), "Re-Enter password", Toast.LENGTH_SHORT).show();
                return;
            }if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "At-least six characters password Required", Toast.LENGTH_SHORT).show();
                return;
            }if (TextUtils.isEmpty(firstname)) {
                Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                return;

            }if (TextUtils.isEmpty(lastname)) {
                Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
                return;
            }if (TextUtils.isEmpty(state)) {
                Toast.makeText(getApplicationContext(), "Enter State", Toast.LENGTH_SHORT).show();
                return;
            }if (state.length() > 2){
                Toast.makeText(getApplicationContext(), "Enter 2-letter State", Toast.LENGTH_SHORT).show();
                return;
            }if (TextUtils.isEmpty(city)) {
                Toast.makeText(getApplicationContext(), "Enter City", Toast.LENGTH_SHORT).show();
                return;
            }if (TextUtils.isEmpty(zipcode)) {
                Toast.makeText(getApplicationContext(), "Enter Zip-Code", Toast.LENGTH_SHORT).show();
                return;
            }if (zipcode.length() < 6 && zipcode.length() > 5){
                Toast.makeText(getApplicationContext(), "Enter 5-digit zip code", Toast.LENGTH_SHORT).show();
                return;
            }if (TextUtils.isEmpty(dob)) {
                Toast.makeText(getApplicationContext(), "Enter Date Of Birth", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
    @Override
    public void onClick(View view){
        if (view == btnSubmit){
            startRegister();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
