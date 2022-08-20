package com.example.alarms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    AlarmManager alarmManager;
    PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(
                Context.ALARM_SERVICE);
    }

    public void setAlarm(View view){
        Intent intentToFire = new Intent(
                getApplicationContext(),
                AlarmBroadcastReceiver.class);

        intentToFire.setAction(
                AlarmBroadcastReceiver.ACTION_ALARM);

        alarmIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                0,
                intentToFire,
                0);



        long seconds = SystemClock.elapsedRealtime() + 10 * 1000;

        alarmManager.set(
                AlarmManager.ELAPSED_REALTIME,
                seconds,
                alarmIntent);

//        alarmManager.setRepeating(
//                AlarmManager.ELAPSED_REALTIME,
//                seconds,
//                seconds,
//                alarmIntent);
    }

    public void cancelAlarm(View view){
        alarmManager.cancel(alarmIntent);
    }
}