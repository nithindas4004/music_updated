package com.example.alzon.final_ser.homepage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NotificationBroadcast extends BroadcastReceiver {

    @Override
    public  void onReceive(Context context, Intent intent){

        if (intent.getAction().equals(NotificationGenerator.NOTIFY_PLAY)){
            Log.v("ghf",intent.getAction());
            player_adapter.mediaPlayer.stop();
        } else if (intent.getAction().equals(NotificationGenerator.NOTIFY_PAUSE)){
            player_adapter.mediaPlayer.stop();
        }else if (intent.getAction().equals(NotificationGenerator.NOTIFY_NEXT)){
            player_adapter.mediaPlayer.stop();
        }else if (intent.getAction().equals(NotificationGenerator.NOTIFY_DELETE)){
            player_adapter.mediaPlayer.stop();
        }else if (intent.getAction().equals(NotificationGenerator.NOTIFY_PREVIOUS)){
            player_adapter.mediaPlayer.stop();
        }
    }
}