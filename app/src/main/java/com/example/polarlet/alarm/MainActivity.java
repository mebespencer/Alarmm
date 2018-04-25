package com.example.polarlet.alarm;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    // make alarm manager
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_alarm;
    Context context;
    PendingIntent pending_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.context = this;

        // initialize manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // initialize timepicker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker1);

        //initialize update box
        update_alarm = (TextView) findViewById(R.id.update_alarm);

        // create an instance of a calender
        final Calendar calendar = Calendar. getInstance();

        // create an intent to the Alarm Reciever class
        final Intent my_intent = new Intent(this.context, Alarm_reciever.class);

        //initialize set button
        Button switch_on = (Button) findViewById(R.id.switch_on);

        switch_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }

                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                // method that changes the update alarm textbox
                set_alarm_text("Alarm set to:" + hour_string + ":" + minute_string);

                // create a pending intent that delays the intent
                // until the specified calender time
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // set the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pending_intent);

                Log.e("fuckin", "intents created and allegedly pending");
            }
        });

        //initialize end button
        Button switch_off = (Button) findViewById(R.id.switch_off);

        // create an onClick listener to stop the alarm or undo an alarm

        switch_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // method that changes the update alarm textbox
                set_alarm_text("Alarm off!");

                // cancel it
                alarm_manager.cancel(pending_intent);
            }
        });
    }

    private void set_alarm_text(String output) {
        update_alarm.setText(output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
