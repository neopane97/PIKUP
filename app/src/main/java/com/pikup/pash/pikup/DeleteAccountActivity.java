package com.pikup.pash.pikup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteAccountActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private Button buttonDeleteAccount;
    private Button buttonDeleteBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    //personalize menu on top
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //display the home menu on top right of the delete account activity page
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            startActivity(new Intent(DeleteAccountActivity.this, HomeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonDeleteAccount = (Button) findViewById(R.id.buttonDeleteAccount);
        buttonDeleteBack = (Button) findViewById(R.id.buttonDeleteBAck);
        progressBar = (ProgressBar) findViewById(R.id.ForgetProgressBar);

       // auth = FirebaseAuth.getInstance();

       final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //takes back to previous page
        buttonDeleteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // When the user click on delete button, the user have to follow few steps to complete the process
        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                // if the input is empty, then it generate a message to ask user to enter the registered email
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String userEmail = user.getEmail();

                //If it's not a register email, then user have to re enter the registered email
                if (!email.equals(userEmail)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                if (user != null) {

                    //Datareferece to remove the user information from the database(firebase)
                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                    databaseReference.child(uid).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        }
                    });

                    //When the user is delete it toast a succefull message ant intent to the main Activity
                    // else it failed to delete the accout and stay on same page.
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(DeleteAccountActivity.this, "Your profile is deleted: \n We will Miss you", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(DeleteAccountActivity.this, MainActivity.class));
                                        finish();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(DeleteAccountActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
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
