
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

public class ChooseCategoryActivity extends AppCompatActivity
{
    private Button buttonCapture;
    private Button buttonUpload;
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
            startActivity(new Intent(ChooseCategoryActivity.this, HomeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonCapture = (Button) findViewById(R.id.buttonCapture);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);


//        buttonCapture.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ChooseCategoryActivity.this, CaptureImageActivity.class);
//                intent.putExtra(KEY_CATEGORY, getCategory());
//                startActivity(intent);
//            }
//        });

//        buttonUpload.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ChooseCategoryActivity.this, UploadImageActivity.class);
//                intent.putExtra(KEY_CATEGORY, getCategory());
//                startActivity(intent);
//             }
//        });

    }
    public void selectCategory (View view) {
    final boolean checked = ((RadioButton) view).isChecked();

    switch (view.getId())
    {
        case R.id.electronic_Cat:
            //do something
            if (checked)
            {
                category = "electronics";
                Toast.makeText(ChooseCategoryActivity.this, "Electronics", Toast.LENGTH_LONG).show();
            }
            break;
        case R.id.furniture_Cat:
            //do something
            if (checked)
            {
                category = "furniture";
                Toast.makeText(ChooseCategoryActivity.this, "Furniture", Toast.LENGTH_LONG).show();
            }
            break;
        case R.id.appliance_Cat:
            //do somemthing
            if (checked)
            {
                category = "appliances";
                Toast.makeText(ChooseCategoryActivity.this, "Appliance", Toast.LENGTH_LONG).show();
            }
            break;
        case R.id.misc_Cat:
            //do something
            if (checked)
            {
                category = "miscellaneous";
                Toast.makeText(ChooseCategoryActivity.this, "Miscellaneous", Toast.LENGTH_LONG).show();
            }
            break;

    }//end switch

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked) {
                    Intent intent = new Intent(ChooseCategoryActivity.this, CaptureImageActivity.class);
                    intent.putExtra(KEY_CATEGORY, getCategory());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Select button to Proceed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked) {
                    Intent intent = new Intent(ChooseCategoryActivity.this, UploadImageActivity.class);
                    intent.putExtra(KEY_CATEGORY, getCategory());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Select button to Proceed", Toast.LENGTH_SHORT).show();

                }
            }
        });
}//end selectCategory

    public String getCategory()
    {
        return category;
    }

}