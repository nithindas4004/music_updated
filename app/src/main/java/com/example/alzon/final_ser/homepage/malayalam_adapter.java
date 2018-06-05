package com.example.alzon.final_ser.homepage;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
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

/**
 * Created by Belal on 2/23/2017.
 */

public class malayalam_adapter extends RecyclerView.Adapter<malayalam_adapter.ViewHolder> {
    private DatabaseReference mDatabase;


    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private static MediaPlayer mediaPlayer;
    private Context context;
    public static List<Upload> uploads;
    int a;



    public malayalam_adapter(Context context, List<Upload> uploads) {
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.get().load(uploads.get(position).getAlbumurl()).into(holder.imageView);
        holder.textViewName.setText(uploads.get(position).getName());
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