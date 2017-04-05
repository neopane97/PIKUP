package com.pikup.pash.pikup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class FilterByLocation extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    private Button button;
    public String location;

    public static final String KEY_LOCATION="location";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by_location);


        button = (Button) findViewById(R.id.locSubmit);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    Intent intent = new Intent(FilterByLocation.this, ViewActivity.class);
                    intent.putExtra(KEY_LOCATION, getLocation());
                    startActivity(intent);
            }
        });

    }
    public String getLocation()
    {
        return location;
    }

}
