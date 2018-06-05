package com.example.alzon.final_ser;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
LinearLayout ln;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_demo);
//        ln=findViewById(R.id.asd);
//        final int N = 10; // total number of textviews to add
//
//        final TextView[] myTextViews = new TextView[N]; // create an empty array;
//
//        for (int i = 0; i < N; i++) {
//            // create a new textview
//            final TextView rowTextView = new TextView(this);
//
//            // set some properties of rowTextView or something
//            rowTextView.setText("This is row #" + i);
//
//            // add the textview to the linearlayout
//            ln.addView(rowTextView);
//
//            // save a reference to the textview for later
//            myTextViews[i] = rowTextView;
//            rowTextView.setBackgroundResource(R.drawable.album_cover);
//            Object tag=rowTextView.getTag();
//
//        }
    }
}