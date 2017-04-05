package com.pikup.pash.pikup;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;




public class ItemDetailFragment extends Fragment {

	Context context;
	ImageView img;
	TextView title, zip, description;





	public ItemDetailFragment() {
		super();
	}

	public static ItemDetailFragment newInstance() {
		ItemDetailFragment df = new ItemDetailFragment();
		return df;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item_detail, container, false);

		img = (ImageView) view.findViewById(R.id.ditem_img);
		img.setImageResource(R.drawable.place);
		title = (TextView) view.findViewById(R.id.ditem_title);
		title.setText("Something");
		zip = (TextView) view.findViewById(R.id.ditem_zip);
		zip.setText("13210");
		description = (TextView) view.findViewById(R.id.ditem_description);
		description.setText("A description of the thing that might be really really" +
				" really really really really really really really REALLY long.");

		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.context = context;
	}
}
