package com.example.alzon.final_ser.homepage;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alzon.final_ser.R;
import com.example.alzon.final_ser.modelclass.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class player_activity extends AppCompatActivity {
    public  String DATABASE_PATH_UPLOADSimages = "images/";
    private List<Upload> playerlist;
    GridLayoutManager grd;
    public static CardView crd;
    MediaPlayer mediaPlayer;
    public static ImageView pausee;
    private ProgressDialog progressDialog;
    RecyclerView rcv;
    private DatabaseReference mDatabase;
    SharedPreferences sh;
    String url,picurl,nameurl;
    int position;
     public  static ImageView img;
    private RecyclerView.Adapter adap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_activity);
        rcv=findViewById(R.id.play);
         img=(ImageView)findViewById(R.id.playing);
         crd=(CardView) findViewById(R.id.cardv);
         pausee=(ImageView)findViewById(R.id.pause);
        ImageView banner=(ImageView)findViewById(R.id.banner);
        TextView txt=(TextView)findViewById(R.id.albumname);
        Intent in=getIntent();
        Bundle bn=in.getExtras();
        sh = player_activity.this.getApplicationContext().getSharedPreferences("facebook", Context.MODE_PRIVATE);
        picurl = sh.getString("pic", null);
        nameurl = sh.getString("nam", null);
        crd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(player_activity.this, player.class);
                intent.putExtra("position",position);
                Bundle bundle = ActivityOptions.makeCustomAnimation(player_activity.this, R.anim.slide_in_up, R.anim.slide_out_up).toBundle();
                startActivity(intent, bundle);
            }
        });
        if (player_adapter.mediaPlayer == null ) {

            crd.setVisibility(View.GONE);
        }else {
            if (player_adapter.mediaPlayer!=null) {
                crd.setVisibility(View.VISIBLE);
            }
        }

        if(bn!=null)
        {
            url=bn.getString("textvalue");
            position=bn.getInt("position",0);
            DATABASE_PATH_UPLOADSimages = "images/"+url+"/songs";
        }

        Picasso.get().load(tamil_adapter.uploads.get(position).getBannerurl()).into(banner);
        Picasso.get().load(picurl).into(img);
        txt.setText(tamil_adapter.uploads.get(position).getName());
        progressDialog = new ProgressDialog(this);

        playerlist = new ArrayList<>();



        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADSimages);

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    playerlist.add(upload);

                }
                grd = new GridLayoutManager(player_activity.this, 1);
                rcv.setLayoutManager(grd);

            adap = new player_adapter(getApplicationContext(), playerlist);
                rcv.setAdapter(adap);
                adap.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
