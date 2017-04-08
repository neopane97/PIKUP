//package com.pikup.pash.pikup;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//
//public class FilterByLocation extends AppCompatActivity {
//    private EditText editText;
//    private TextView textView;
//    private Button button;
//    public String location;
//
//    public static final String KEY_LOCATION="location";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_filter_by_location);
//
//
//        button = (Button) findViewById(R.id.locSubmit);
//
//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                    Intent intent = new Intent(FilterByLocation.this, ViewActivity.class);
//                    intent.putExtra(KEY_LOCATION, getLocation());
//                    startActivity(intent);
//            }
//        });
//
//    }
//    public String getLocation()
//    {
//        return location;
//    }
//
//}


package com.pikup.pash.pikup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class FilterByLocation extends AppCompatActivity {
    private EditText editText;
    //private TextView textView;
    private Button buttonOk;
    public String location;
    private Toolbar toolbar;
    public static final String KEY_LOCATION="location";

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_home)
        {
            startActivity(new Intent(FilterByLocation.this, HomeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by_location);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.locEditText);

        buttonOk = (Button) findViewById(R.id.locSubmit);

    }

    public void resolveLocation (View view) {
        //final String temp = "";
//        buttonOk.setOnClickListener(new View.OnClickListener()

        //{
//
//            @Override
//            public void onClick(View v)
//            {
                location = editText.getText().toString();

                //if (temp != getLocation()) {
                    Intent intent = new Intent(FilterByLocation.this, ViewActivity.class);
                    intent.putExtra(KEY_LOCATION, getLocation());
                    startActivity(intent);
                //}
                //else {
                 //   Toast.makeText(getApplicationContext(),"Pleas select Filter Option", Toast.LENGTH_SHORT).show();
                //}
            //}
       // });
    }//end selectCategory

    public String getLocation()
    {
        return location;
    }

}