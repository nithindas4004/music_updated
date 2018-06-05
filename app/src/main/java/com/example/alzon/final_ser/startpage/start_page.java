package com.example.alzon.final_ser.startpage;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;

import com.example.alzon.final_ser.R;
import com.example.alzon.final_ser.modelclass.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class start_page extends AppCompatActivity {
    ImageView h_image1, h_image2;
    private AdapterViewFlipper adapterViewFlipper, hits, cover;
    private List<Upload> malayalam, lhits, lcover;
    private DatabaseReference mDatabase, mhits, mcover;
    public static final String DATABASE_PATH_UPLOADSimages = "images";
    public static final String DATABASE_PATH_hits = "images";
    public static final String DATABASE_PATH_cover = "images";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maiin);
        adapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.cover);
    //    hits = (AdapterViewFlipper) findViewById(R.id.hits);
//        hits.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in=new Intent(start_page.this,homepage.class);
//                startActivity(in);
//            }
//        });
       // cover = (AdapterViewFlipper) findViewById(R.id.cover);
//        adapterViewFlipper.setFlipInterval(3000);
//        adapterViewFlipper.setAutoStart(true);
//        adapterViewFlipper.setInAnimation(getApplicationContext(), R.animator.leftin);
//        adapterViewFlipper.setOutAnimation(getApplicationContext(), R.animator.rightout);        progressDialog = new ProgressDialog(this);
//        adapterViewFlipper.startFlipping();
        progressDialog = new ProgressDialog(this);
        malayalam = new ArrayList<>();
        lhits = new ArrayList<>();
        lcover = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADSimages);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                dismissing the progress dialog
                progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    malayalam.add(upload);

                }
                FlipperAdapter adapter = new FlipperAdapter(getApplicationContext(), malayalam);

//                //adding it to adapterview flipper
                adapterViewFlipper.setAdapter(adapter);
                adapterViewFlipper.setFlipInterval(1000);
                adapterViewFlipper.setAutoStart(true);
//                adapterViewFlipper.setInAnimation(getApplicationContext(), R.animator.leftin);
//                adapterViewFlipper.setOutAnimation(getApplicationContext(), R.animator.rightout);

                adapterViewFlipper.startFlipping();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        mhits = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_hits);
//        mhits.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
////                dismissing the progress dialog
//                progressDialog.dismiss();
//
//                //iterating through all the values in database
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    Upload upload = postSnapshot.getValue(Upload.class);
//                    lhits.add(upload);
//
//                }
//                hitsAdapter adapter = new hitsAdapter(getApplicationContext(), lhits);
//
//                //adding it to adapterview flipper
//                hits.setAdapter(adapter);
//                hits.setFlipInterval(1000);
//                hits.startFlipping();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
