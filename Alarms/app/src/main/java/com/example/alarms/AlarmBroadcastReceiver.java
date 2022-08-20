package com.example.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_ALARM = "NUT.ACTION_ALARM";
    public static final String BOOT_COMPLETED =  "NUT.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_ALARM.equals(intent.getAction())){
            Toast.makeText(
                    context,
                    ACTION_ALARM,
                    Toast.LENGTH_SHORT).show();
        }else if(BOOT_COMPLETED.equals(intent.getAction())){
            Toast.makeText(
                    context,
                    BOOT_COMPLETED,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
