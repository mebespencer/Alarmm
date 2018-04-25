package com.example.polarlet.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.security.Provider;
import java.util.Locale;

public class RingtoneService extends Service {

    MediaPlayer vroom;

    boolean isRunning = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("LocalService", "Received start id " + startId + ": " +intent);

        String state = intent.getExtras().getString("extra");

        Log.e("Extra is", state);

        switch (state) {
            case "on":
                startId = 1;
                break;
            case "off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if(!isRunning && startId == 1) {
            vroom = MediaPlayer.create(this, R.raw.paganizonda);
            vroom.start();
            vroom.setLooping(true);
            isRunning = true;
        } else if(isRunning && startId == 0) {
            Log.e("stop pressed","attempting to stop alarm");
            vroom.setLooping(false);
            vroom.stop();
            vroom.reset();
            isRunning = false;
        }



        /*
        final TextToSpeech ttsobj =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });

        ttsobj.setLanguage(Locale.UK);
        ttsobj.speak("wake up wake up wake up", TextToSpeech.QUEUE_FLUSH, null);
        Log.e("LocalService", "the message been said");
        */
        return  START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "on destroy called", Toast.LENGTH_SHORT).show();
    }
}
