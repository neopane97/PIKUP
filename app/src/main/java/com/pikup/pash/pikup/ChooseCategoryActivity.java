package com.pikup.pash.pikup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class ChooseCategoryActivity extends AppCompatActivity
{
    private Button nextPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        nextPageButton = (Button) findViewById(R.id.btnNextPage);

        nextPageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseCategoryActivity.this, CaptureImageActivity.class));
            }
        });
    }
    public void selectCategory (View view)
    {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId())
        {
            case R.id.electronic_Cat:
                //do something
                if (checked)
                {
                    Toast.makeText(ChooseCategoryActivity.this, "Electronics", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.furniture_Cat:
                //do something
                if (checked)
                {
                    Toast.makeText(ChooseCategoryActivity.this, "Furniture", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.appliance_Cat:
                //do somemthing
                if (checked)
                {
                    Toast.makeText(ChooseCategoryActivity.this, "Appliance", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.misc_Cat:
                //do something
                if (checked)
                {
                    Toast.makeText(ChooseCategoryActivity.this, "Miscellaneous", Toast.LENGTH_LONG).show();
                }
                break;

        }//end switch

    }//end selectCategory

}
