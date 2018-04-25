package com.example.polarlet.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("We are in the reciever", "ok");

        //get extra strings from intent

        String get_your_string = intent.getExtras().getString("extra");

        Log.e("The key is", get_your_string );

        Intent service_intent = new Intent(context, RingtoneService.class);

        service_intent.putExtra("extra", get_your_string);

        context.startService(service_intent);
    }
}
