package com.pikup.pash.pikup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by since on 4/3/2017.
 */

public class FilterCategoryActivity extends AppCompatActivity
{
    private Button buttonOk;
    public String category;
    private Toolbar toolbar;
    public static final String KEY_CATEGORY="category";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            startActivity(new Intent(FilterCategoryActivity.this, HomeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_category);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        buttonOk = (Button) findViewById(R.id.buttonCapture);

    }
    public void selectCategory (View view)
    {
        final boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId())
        {
            case R.id.electronic_Cat:
                if (checked)
                {
                    category = "electronics";
                }
                break;
            case R.id.furniture_Cat:
                if (checked)
                {
                    category = "furniture";
                }
                break;
            case R.id.appliance_Cat:
                if (checked)
                {
                    category = "appliances";
                }
                break;
            case R.id.misc_Cat:
                if (checked)
                {
                    category = "miscellaneous";
                }
                break;

        }//end switch


        buttonOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (checked) {
                    Intent intent = new Intent(FilterCategoryActivity.this, ViewActivity.class);
                    intent.putExtra(KEY_CATEGORY, getCategory());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Pleas select Filter Option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//end selectCategory

    public String getCategory()
    {
        return category;
    }

}