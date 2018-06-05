package com.example.alzon.final_ser.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.vansuita.gaussianblur.GaussianBlur;


import com.example.alzon.final_ser.R;
import com.squareup.picasso.Picasso;
import com.vansuita.gaussianblur.GaussianBlur;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.blurry.Blurry;

public class player extends AppCompatActivity {
    ImageView imageView, pause, next, prev;
    CircleImageView img;
    SeekBar seek;
    private static final float BLUR_RADIUS = 25f;
    Bitmap image;
    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;

    private Handler myHandler = new Handler();;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    int position;
    TextView tx,tx2,tx3;
    SharedPreferences sp;
    String picurl,nextlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);
        imageView = findViewById(R.id.img);
        img=findViewById(R.id.profile_image);
       tx =findViewById(R.id.textView);
       seek=findViewById(R.id.seekBar2);
       tx2=findViewById(R.id.textView2);
       tx3=findViewById(R.id.textView3);
        finalTime = player_adapter.mediaPlayer.getDuration();
        startTime = player_adapter.mediaPlayer.getCurrentPosition();
        sp = player.this.getApplicationContext().getSharedPreferences("facebook", Context.MODE_PRIVATE);
        picurl= sp.getString("pic", null);
        nextlink=sp.getString("next",null);
      //  nameurl = sh.getString("nam", null);
        if (oneTimeOnly == 0) {
            seek.setMax((int) finalTime);
            oneTimeOnly = 1;
        }
        tx2.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        tx3.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) player_adapter.mediaPlayer.getDuration()),
                TimeUnit.MILLISECONDS.toSeconds((long) player_adapter.mediaPlayer.getDuration()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                player_adapter.mediaPlayer.getDuration()))));

        seek.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);
        tx.setText(player_adapter.uploads.get(position).getName());
        pause = findViewById(R.id.pause);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        findViewById(R.id.profile_image).startAnimation(rotateAnimation);
        new GetImageFromURL(imageView).execute();
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player_adapter.mediaPlayer.isPlaying()) {
                    player_adapter.mediaPlayer.stop();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player_adapter.mediaPlayer != null && player_adapter.mediaPlayer.isPlaying()) {
                    player_adapter.mediaPlayer.stop();
                    player_adapter.mediaPlayer.reset();
                    player_adapter.mediaPlayer.release();
                    player_adapter.mediaPlayer = null;
                }

                Log.v("abcd",nextlink);
                player_adapter.mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(nextlink));
                player_adapter.mediaPlayer.start();
                tx.setText(player_adapter.uploads.get(position+1).getName());
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player_adapter.mediaPlayer != null && player_adapter.mediaPlayer.isPlaying()) {
                    player_adapter.mediaPlayer.stop();
                    player_adapter.mediaPlayer.reset();
                    player_adapter.mediaPlayer.release();
                    player_adapter.mediaPlayer = null;
                }

                tx.setText(player_adapter.uploads.get(position-1).getName());
                player_adapter.mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(player_adapter.uploads.get(position - 1).getAudiourl()));
                player_adapter.mediaPlayer.start();
            }
        });

        Intent in = getIntent();
        Bundle bn = in.getExtras();
        if (bn != null) {
            position = bn.getInt("position", 0);

        }
       Picasso.get().load(picurl).into(img);

    }

    public class GetImageFromURL extends AsyncTask<Void, Void, Bitmap> {
        ImageView imgV;


        public GetImageFromURL(ImageView imgV) {
            this.imgV = imgV;
        }

        @Override

        protected Bitmap doInBackground(Void... voids) {
            Bitmap bt = getBitmapFromURL(picurl);

            return bt;
            //   }

        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            super.onPostExecute(bmp);
//Bitmap bt=getBitmapFromURL("https://firebasestorage.googleapis.com/v0/b/final-b9f81.appspot.com/o/images%2F1505451438843.jpeg?alt=media&token=d5801ef5-1128-49a2-96e2-32ca7606f770");
            BitmapDrawable back = new BitmapDrawable(getResources(), bmp);
            imageView.setBackground(back);
            Blurry.with(player.this).from(bmp).into(imageView);
        }

        public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = player_adapter.mediaPlayer.getCurrentPosition();
            tx2.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seek.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };
}



