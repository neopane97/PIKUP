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
    private static final String TAG = "serInformationActivity";
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
  //  private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private String userId;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        listView = (ListView) findViewById(R.id.ListView);


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
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
            UserInfo userInfo = new UserInfo();

            userInfo.setFirstName((dataSnapshot1.child(userId).getValue(UserInfo.class).getFirstName()));
            userInfo.setLastName((dataSnapshot1.child(userId).getValue(UserInfo.class).getLastName()));
            userInfo.setAddress((dataSnapshot1.child(userId).getValue(UserInfo.class).getAddress()));
            userInfo.setState((dataSnapshot1.child(userId).getValue(UserInfo.class).getAddress()));
            userInfo.setState((dataSnapshot1.child(userId).getValue(UserInfo.class).getState()));
            userInfo.setCity((dataSnapshot1.child(userId).getValue(UserInfo.class).getCity()));
            userInfo.setZipCode((dataSnapshot1.child(userId).getValue(UserInfo.class).getZipCode()));
            userInfo.setEmailAddress((dataSnapshot1.child(userId).getValue(UserInfo.class).getEmailAddress()));

            Log.d(TAG, "showData: FirstName: " + userInfo.getFirstName());
            Log.d(TAG, "showData: LastName: " + userInfo.getLastName());
            Log.d(TAG, "showData: Address: " + userInfo.getAddress());
            Log.d(TAG, "showData: State: " + userInfo.getState());
            Log.d(TAG, "showData: City: " + userInfo.getCity());
            Log.d(TAG, "showData: ZipCode: " + userInfo.getZipCode());
            Log.d(TAG, "showData: DateOfBirth: " + userInfo.getDateOfBirth());
            Log.d(TAG, "showData: EmailAddress: " + userInfo.getEmailAddress());

            ArrayList<String> information = new ArrayList<String>();
            information.add(userInfo.getFirstName());
            information.add(userInfo.getLastName());
            information.add(userInfo.getAddress());
            information.add(userInfo.getState());
            information.add(userInfo.getCity());
            information.add(userInfo.getZipCode());
            information.add(userInfo.getDateOfBirth());
            information.add(userInfo.getEmailAddress());

            //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, information);
           // listView.setAdapter(adapter);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,information);

            listView.setAdapter(adapter)
            ;
           // ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                 //   getListView().getContext(),
                //    android.R.layout.simple_list_item_1,
                 //   usernames);
                    listView.setAdapter(adapter);
            //setListAdapter(adapter);


        }
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        firebaseAuth.addAuthStateListener(authStateListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (authStateListener != null){
//            firebaseAuth.removeAuthStateListener(authStateListener);
//        }
//    }
//
//    private void toastMessage(String message){
//        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
//    }
}
