package com.example.alzon.final_ser.startpage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.alzon.final_ser.R;
import com.example.alzon.final_ser.homepage.homepage;
import com.example.alzon.final_ser.modelclass.Upload;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

/**
 * Created by dazs on 16-05-2018.
 */

class hitsAdapter extends BaseAdapter {
    private Context mCtx;
    private List<Upload> heros;

    public hitsAdapter(Context mCtx, List<Upload> heros){
        this.mCtx = mCtx;
        this.heros = heros;
    }
    @Override
    public int getCount() {
        return heros.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Upload hero = heros.get(position);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_flip, null);
        //TextView textView = (TextView) view.findViewById(R.id.textView);
        ImageView imageView = (ImageView) view.findViewById(R.id.morning);
        //textView.setText(hero.getName());
imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent in=new Intent(mCtx,homepage.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(in);
    }
});
        Glide.with(mCtx).load(hero.getBannerurl()).into(imageView);
        return view;
    }
    public void onDataChange(DataSnapshot snapshot) {
        //dismissing the progress dialog
        //progressDialog.dismiss();

        //iterating through all the values in database
        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
            Upload upload = postSnapshot.getValue(Upload.class);
            heros.add(upload);
        }
    }
}