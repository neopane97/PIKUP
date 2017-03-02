package com.pikup.pash.pikup;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button postButton;
    private Button viewButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        postButton = (Button) findViewById(R.id.buttonPost);
        viewButton = (Button) findViewById(R.id.buttonView);
        logoutButton = (Button) findViewById(R.id.buttonLogout);

        postButton = (Button) findViewById(R.id.buttonPost);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, PostActivity.class));
            }
        });

        viewButton = (Button) findViewById(R.id.buttonView);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ViewActivity.class));
            }
        });
        logoutButton = (Button) findViewById(R.id.buttonLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });
    }
}
