package com.pikup.pash.pikup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userInformationActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText address;
    private EditText state;
    private EditText city;
    private EditText zipcode;
    private EditText dob;
    private Button submitInformation;
    private ProgressBar progressBar;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        firstname = (EditText) findViewById(R.id.editFirstName);
        lastname = (EditText) findViewById(R.id.editFirstName);
        address = (EditText) findViewById(R.id.editFirstName);
        zipcode = (EditText) findViewById(R.id.editFirstName);
        dob = (EditText) findViewById(R.id.editFirstName);
        submitInformation = (Button) findViewById(R.id.buttonSubmit);

//        submitInformation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(userInformationActivity.this, HomeActivity.class));
//            }
//        });
    }
}
