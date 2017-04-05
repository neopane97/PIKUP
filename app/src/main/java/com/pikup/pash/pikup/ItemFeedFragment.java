package com.pikup.pash.pikup;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.pikup.pash.pikup.R.id.recycc;

public class ItemFeedFragment extends Fragment {
	Context context;
	FirebaseAuth auth;
	FirebaseUser user;


	View view;
	LinearLayoutManager llm;
	RecyclerAdapter reAdapter;
	RecyclerView reView;


	@Override

	public void onAttach(Context context) {
		super.onAttach(context);
		this.context = context;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof ViewActivity)
			this.context = activity;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		auth = FirebaseAuth.getInstance();
		user = auth.getCurrentUser();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if (view != null)
			return view;
		setHasOptionsMenu(true);
		view = inflater.inflate(R.layout.fragment_item_feed, container, false);
		reView = (RecyclerView) view.findViewById(recycc);
		llm = new LinearLayoutManager(context);
		reView.setLayoutManager(llm);
		reAdapter = new RecyclerAdapter();
		Intent i = getActivity().getIntent();
		if (i.hasExtra("category"))
			reAdapter.filterBy(i.getStringExtra("category"));
		else
			reAdapter.initPosts();
		reView.setAdapter(reAdapter);
		Log.d("CREATEVIEW", "onCreateView is done");
		return view;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_claim:
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getEmail()});
				i.putExtra(Intent.EXTRA_SUBJECT, "I would like to come pick up your given (Free) item(s)");
				i.putExtra(Intent.EXTRA_TEXT, "Please reply to my email to negotiate a time and place for me to pickup your item." +
						" \n" + "\nI look forward hearing back from you\n" + "\nThank You");
				try {
					startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.menu_fast:
				if (reAdapter.isFast()) {
					reAdapter.goNormal();
				} else
					reAdapter.goFast();
				break;
			case R.id.menu_filter:
				Intent intent = new Intent(context, FilterCategoryActivity.class);
				startActivity(intent);
				break;
			case R.id.menu_home:
				Intent intent1 = new Intent(context, HomeActivity.class);
				startActivity(intent1);
			break;
			case R.id.menu_loc:
				Intent intent2 = new Intent(context, FilterByLocation.class);
				startActivity(intent2);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		// Check exists
		if (menu.findItem(R.id.menu_claim) == null)
			inflater.inflate(R.menu.reycc_menu, menu);
	}

	@Override
	public void onDestroyView() {
		if (view.getParent() != null)
			((ViewGroup) view.getParent()).removeView(view);
		super.onDestroyView();
	}

	class RecyclerAdapter extends RecyclerView.Adapter<Holder> {
		DatabaseReference dbr;
		private ArrayList<Post> posts;
		private boolean fast;
		private ChildEventListener chel;
		//boolean allCheckd = false;

		RecyclerAdapter() {
			posts = new ArrayList<>();
			dbr = FirebaseDatabase.getInstance().getReference().child("Posts");
			fast = false;
		}

		@Override
		public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.feed_item, parent, false);
			return new Holder(v);
		}

		@Override
		public void onBindViewHolder(Holder holder, int position) {
			holder.img.setImageResource(R.drawable.place);
			Post p = posts.get(position);
			Log.i("IMAGEPATH", p.getImage_path());
			StorageReference imgRef = FirebaseStorage
					.getInstance()
					.getReference()
					.child("PikUpPhotos/" +
							p.getImage_path());
			Glide.with(context)
					.using(new FirebaseImageLoader())
					.load(imgRef)
					.into(holder.img);
			holder.title.setText(p.getTitle());
			holder.checkBox.setChecked(false);
		}

		@Override
		public int getItemCount() {
			return posts.size();
		}

		public void initPosts() {
			Log.d("INITPOSTS", "called initPosts()");
			final int k = posts.size();

			dbr.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot ds : dataSnapshot.getChildren()) {
						Post p = ds.getValue(Post.class);
						posts.add(p);
					}
					notifyDataSetChanged();
					llm.scrollToPosition(0);

				}

				@Override
				public void onCancelled(DatabaseError databaseError) {
					Log.w("DBERROR", databaseError.toString());
				}
			});
			Log.d("INITPOSTS", "called notifyDataSetChanged()");
		}

		public void filterBy(final String filter) {
			Log.d("INITPOSTS", "called initPosts()");
			final int k = posts.size();

			dbr.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot ds : dataSnapshot.getChildren()) {
						Post p = ds.getValue(Post.class);
						String pcat = p.getPcat();
						if (pcat.equals(filter))
							posts.add(p);
					}
					notifyDataSetChanged();
					llm.scrollToPosition(0);

				}

				@Override
				public void onCancelled(DatabaseError databaseError) {
					Log.w("DBERROR", databaseError.toString());
				}
			});
			Log.d("INITPOSTS", "called notifyDataSetChanged()");
		}

		public boolean isFast() {
			return fast;
		}

		public void goNormal() {
			dbr.removeEventListener(chel);
			fast = false;
			Toast.makeText(context, "Normal Mode", Toast.LENGTH_SHORT)
					.show();
		}

		public void goFast() {
			if (chel == null) {
				chel = new ChildEventListener() {
					@Override
					public void onChildAdded(DataSnapshot dataSnapshot, String s) {
						Post p = dataSnapshot.getValue(Post.class);
						for (Post q : posts) {
							String qimg = q.getImage_path();
							String pimg = p.getImage_path();
							if (qimg.equalsIgnoreCase(pimg))
								return;
						}
						posts.add(0, p);
						SystemClock.sleep(3000);
						reAdapter.notifyItemInserted(0);
						llm.scrollToPosition(0);

					}

					@Override
					public void onChildChanged(DataSnapshot dataSnapshot, String s) {

					}

					@Override
					public void onChildRemoved(DataSnapshot dataSnapshot) {

					}

					@Override
					public void onChildMoved(DataSnapshot dataSnapshot, String s) {

					}

					@Override
					public void onCancelled(DatabaseError databaseError) {

					}
				};
			}
			dbr.addChildEventListener(chel);
			fast = true;
			Toast.makeText(context, "Realtime Database", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public static class Holder extends RecyclerView.ViewHolder {
		ImageView img;
		TextView title;
		CheckBox checkBox;
		CardView card;

		public Holder(View itemView) {
			super(itemView);
			card = (CardView) itemView.findViewById(R.id.card);
			img = (ImageView) itemView.findViewById(R.id.item_img);
			title = (TextView) itemView.findViewById(R.id.item_title);
			checkBox = (CheckBox) itemView.findViewById(R.id.item_check);
		}
	}
}