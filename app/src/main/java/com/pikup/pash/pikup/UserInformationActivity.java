package com.pikup.pash.pikup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserInformationActivity extends AppCompatActivity {
    private static final String TAG = "UserInformationActivity";
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private String userId;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        mListView = (ListView) findViewById(R.id.listview);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userId = user.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void showData(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            UserInfo userInfo = new UserInfo();
            userInfo.setFirstName(ds.child(userId).getValue(UserInfo.class).getFirstName());
            userInfo.setLastName(ds.child(userId).getValue(UserInfo.class).getLastName());
            userInfo.setAddress(ds.child(userId).getValue(UserInfo.class).getAddress());
            userInfo.setState(ds.child(userId).getValue(UserInfo.class).getState());
            userInfo.setCity(ds.child(userId).getValue(UserInfo.class).getCity());
            userInfo.setZipCode(ds.child(userId).getValue(UserInfo.class).getZipCode());
            userInfo.setEmail(ds.child(userId).getValue(UserInfo.class).getEmail());
            userInfo.setDateOfBirth(ds.child(userId).getValue(UserInfo.class).getDateOfBirth());

            Log.d(TAG, "showData: FirstName: " + userInfo.getFirstName());
            Log.d(TAG, "showData: LastName: " + userInfo.getLastName());
            Log.d(TAG, "showData: Address: " + userInfo.getAddress());
            Log.d(TAG, "showData: State: " + userInfo.getState());
            Log.d(TAG, "showData: City: " + userInfo.getCity());
            Log.d(TAG, "showData: ZipCode: " + userInfo.getZipCode());
            Log.d(TAG, "showData: DateOfBirth: " + userInfo.getDateOfBirth());
            Log.d(TAG, "showData: Email: " + userInfo.getEmail());

            ArrayList<String> information = new ArrayList<>();
            information.add(userInfo.getFirstName());
            information.add(userInfo.getLastName());
            information.add(userInfo.getAddress());
            information.add(userInfo.getState());
            information.add(userInfo.getCity());
            information.add(userInfo.getZipCode());
            information.add(userInfo.getDateOfBirth());
            information.add(userInfo.getEmail());
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, information);
            mListView.setAdapter(adapter);
        }
    }
}
