package com.example.alzon.final_ser.homepage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alzon.final_ser.R;
import com.example.alzon.final_ser.modelclass.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Belal on 2/23/2017.
 */

public class player_adapter extends RecyclerView.Adapter<player_adapter.ViewHolder> {
    private DatabaseReference mDatabase;
String piclink;
String nameLink,nextlink;

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    public static MediaPlayer mediaPlayer;
    private Context context;
    public static  List<Upload> uploads;
    int a;
SharedPreferences sharedpreferences_fb;


    public player_adapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.get().load(uploads.get(position).getUrl()).into(holder.imageView);

        holder.textViewName.setText(uploads.get(position).getName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(uploads.get(position).getUrl()).into(player_activity.img);

                piclink=uploads.get(position).getUrl();
nameLink=uploads.get(position).getName();
nextlink=uploads.get(position+1).getAudiourl();
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                sharedpreferences_fb = context.getApplicationContext().getSharedPreferences("facebook", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences_fb.edit();
                editor.putInt("position", position);
                editor.putString("pic",piclink);
                editor.putString("nam",nameLink);
                editor.putString("next",nextlink);
                editor.commit();


                mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(uploads.get(position).getAudiourl()));
                mediaPlayer.start();
                player_activity.crd.setVisibility(View.VISIBLE);
                player_activity.pausee.setVisibility(View.VISIBLE);
                NotificationGenerator.customBigNotification(context);

            }
        });
//        holder.card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewName,albumname;
        public ImageView imageView;
        public CardView card;


        ViewHolder(View itemView) {
            super(itemView);


           textViewName = (TextView) itemView.findViewById(R.id.songname);
            albumname = (TextView) itemView.findViewById(R.id.albumname);
card=(CardView)itemView.findViewById(R.id.cardv);

            imageView = (ImageView) itemView.findViewById(R.id.album);
            //textViewName.setOnClickListener(this);
//            card = (CardView) itemView.findViewById(R.id.cardView3);
//            card.setOnClickListener(this);
//            a=getAdapterPosition();


        }
        public void onDataChange(DataSnapshot snapshot) {
            //dismissing the progress dialog
            progressDialog.dismiss();

            //iterating through all the values in database
            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                Upload upload = postSnapshot.getValue(Upload.class);
                uploads.add(upload);
            }
        }

        @Override
        public void onClick(View v) {

        }


//        @Override
//        public void onClick(View v) {
//
//        }
    }
}