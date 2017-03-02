package com.pikup.pash.pikup;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ItemDetailActivity extends AppCompatActivity {

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
