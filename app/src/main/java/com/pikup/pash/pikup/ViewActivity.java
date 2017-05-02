//package com.pikup.pash.pikup;
//
//import android.app.FragmentManager;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//public class ViewActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view);
//        // Toolbar
//        //toolbar = (Toolbar) findViewById(R.id.toolbar);
//        //setSupportActionBar(toolbar);
//        // Fragment
//        FragmentManager fm = getFragmentManager();
//        //ItemDetailFragment idtf = (ItemDetailFragment) fm.findFragmentByTag("idtf");
//        //if (idtf == null) {
//        ItemFeedFragment ilf = (ItemFeedFragment) fm.findFragmentByTag("ilf");
//        if (ilf == null) {
//            ilf = new ItemFeedFragment();
//            fm.beginTransaction()
//                    .add(R.id.frame, ilf, "ilf")
//                    .commit();
//        }
//    }
//}
package com.pikup.pash.pikup;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewActivity extends AppCompatActivity {
    private Button claimed;
    private FirebaseAuth auth;
    private Toolbar toolbar;

    // The activity used to hold the item feed fragment
    // Originally used fragments so we could also
    // show item details in the same activity,
    // but since we aren't doing that we could change it
    // to just this activity later
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fm = getFragmentManager();


        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = auth.getCurrentUser();

        // Check if fragment exists,
        // if not make it for the first time
        ItemFeedFragment ilf = (ItemFeedFragment) fm.findFragmentByTag("ilf");
        if (ilf == null) {
            ilf = new ItemFeedFragment();
            fm.beginTransaction()
                    .add(R.id.frame, ilf, "ilf")
                    .commit();
        }
    }
}
