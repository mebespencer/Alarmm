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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("LocalService", "Received start id " + startId + ": " +intent);

        /*
        final TextToSpeech ttsobj =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });

        ttsobj.setLanguage(Locale.UK);
        ttsobj.speak("neighbor", TextToSpeech.QUEUE_FLUSH, null);
        Log.e("LocalService", "neighbor hath been said");
        */

        vroom = MediaPlayer.create(this, R.raw.paganizonda);
        vroom.start();
        return  START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "on destroy called", Toast.LENGTH_SHORT).show();
    }
}
