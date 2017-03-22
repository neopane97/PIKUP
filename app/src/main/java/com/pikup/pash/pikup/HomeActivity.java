package com.pikup.pash.pikup;

        import android.content.Intent;
        import android.media.MediaRouter;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button postButton;
    private Button viewButton;
    private Button logoutButton;
    private Button userProfileButton;
    private FirebaseAuth auth;
    private Button buttonInfo;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = auth.getCurrentUser();

        Toast.makeText(getApplicationContext(), "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();


        buttonInfo = (Button) findViewById(R.id.buttonUserInfo);
        postButton = (Button) findViewById(R.id.buttonPost);
        viewButton = (Button) findViewById(R.id.buttonView);
        logoutButton = (Button) findViewById(R.id.buttonLogout);


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

        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, UserInformationActivity.class));
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
