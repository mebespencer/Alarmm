package com.example.polarlet.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.security.Provider;
import java.util.Locale;

import static java.util.Locale.UK;

public class RingtoneService extends Service {

    TextToSpeech tts;
    MediaPlayer vroom;
    String toSay;
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
            toSay = MainActivity.whatToSay;

            tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    Log.e("intialization", "tts object");
                    tts.setLanguage(UK);
                    Log.e("attempting to speak", toSay);
                    for (int i = 0; i < 100; i++) {
                        tts.speak(toSay, TextToSpeech.QUEUE_ADD, null, "wake up call");
                    }
                    Log.e("spoke message", toSay);
                }
            });
            /*
            vroom = MediaPlayer.create(this, R.raw.paganizonda);
            vroom.start();
            vroom.setLooping(true); */
            isRunning = true;

        } else if(isRunning && startId == 0) {
            tts.stop();
            /*
            Log.e("stop pressed","attempting to stop alarm");
            vroom.setLooping(false);
            vroom.stop();
            vroom.reset(); */
            isRunning = false;

        }

        return  START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "on destroy called", Toast.LENGTH_SHORT).show();
    }
}
