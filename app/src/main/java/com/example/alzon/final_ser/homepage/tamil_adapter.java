package com.example.alzon.final_ser.homepage;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
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

/**
 * Created by Belal on 2/23/2017.
 */

public class tamil_adapter extends RecyclerView.Adapter<tamil_adapter.ViewHolder> {
    private DatabaseReference mDatabase;

public static int pos;
    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private static MediaPlayer mediaPlayer;
    private Context context;
    public static List<Upload> uploads;
    int a;



    public tamil_adapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_page, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.get().load(uploads.get(position).getAlbumurl()).into(holder.imageView);
        holder.textViewName.setText(uploads.get(position).getName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent act=new Intent(context,player_activity.class);
////                act.putExtra("textvalue",uploads.get(position).getName());
////                act.putExtra("position",position);
//
//                ActivityOptions activity=ActivityOptions.makeSceneTransitionAnimation((Activity) context,holder.imageView,
//                        "imageTransition");
//
//
//                context.startActivity(act,activity.toBundle());
                Intent in=new Intent(context,player_activity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                in.putExtra("textvalue",uploads.get(position).getName());
                in.putExtra("position",position);
                context.startActivity(in);

//                mediaPlayer = MediaPlayer.create(context, Uri.parse(uploads.get(position).getAudiourl()));
//                mediaPlayer.start();

            }
        });

    }


    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewName;
        public ImageView imageView;
        public CardView card;


        ViewHolder(View itemView) {
            super(itemView);


           textViewName = (TextView) itemView.findViewById(R.id.name);
            imageView = (ImageView) itemView.findViewById(R.id.album);
            //textViewName.setOnClickListener(this);
            card = (CardView) itemView.findViewById(R.id.card);

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