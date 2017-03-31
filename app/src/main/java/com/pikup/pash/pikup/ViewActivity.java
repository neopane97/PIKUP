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
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewActivity extends AppCompatActivity {
    private Button claimed;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        // Toolbar
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Fragment
        FragmentManager fm = getFragmentManager();
        //ItemDetailFragment idtf = (ItemDetailFragment) fm.findFragmentByTag("idtf");
        //if (idtf == null) {
        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = auth.getCurrentUser();


        /*********************************************/

//        claimed = (Button) findViewById(R.id.claim_checked_btn);
//
//        claimed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("message/rfc822");
//                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{user.getEmail()});
//                i.putExtra(Intent.EXTRA_SUBJECT, "I would like to come pick up your given (Free) item(s)");
//                i.putExtra(Intent.EXTRA_TEXT   , "Please reply to my email to negotiate a time and place for me to pickup your item." +
//                        " \n"+"\nI look forward hearing back from you\n"+"\nThank You");
//                try {
//                    startActivity(Intent.createChooser(i, "Send mail..."));
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(ViewActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        /***********************************************/
        ItemFeedFragment ilf = (ItemFeedFragment) fm.findFragmentByTag("ilf");
        if (ilf == null) {
            ilf = new ItemFeedFragment();
            fm.beginTransaction()
                    .add(R.id.frame, ilf, "ilf")
                    .commit();
        }
    }
}
