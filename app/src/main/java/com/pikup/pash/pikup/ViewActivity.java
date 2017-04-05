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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Fragment
        FragmentManager fm = getFragmentManager();
        //ItemDetailFragment idtf = (ItemDetailFragment) fm.findFragmentByTag("idtf");
        //if (idtf == null) {

        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = auth.getCurrentUser();

        ItemFeedFragment ilf = (ItemFeedFragment) fm.findFragmentByTag("ilf");
        if (ilf == null) {
            ilf = new ItemFeedFragment();
            fm.beginTransaction()
                    .add(R.id.frame, ilf, "ilf")
                    .commit();
        }
    }
}
