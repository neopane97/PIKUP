package com.pikup.pash.pikup;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ItemDetailActivity extends AppCompatActivity {


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.homemenu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_home) {
			startActivity(new Intent(ItemDetailActivity.this, HomeActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		FragmentManager fm = getFragmentManager();
		ItemDetailFragment idtf = (ItemDetailFragment) fm.findFragmentByTag("idtf");
		if (idtf == null) {
			idtf = ItemDetailFragment.newInstance();
			fm.beginTransaction()
					.add(R.id.frame, idtf)
					.commit();
		}
	}
}
