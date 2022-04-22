package com.nut.directreply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String KEY_REPLY_TEXT = "KEY_REPLY_TEXT";
    private final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent() != null){
            Toast.makeText(this, getReplyText(getIntent()), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Toast.makeText(this, getReplyText(getIntent()), Toast.LENGTH_SHORT).show();
    }

    public void onClickSend(View view){
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY_TEXT).setLabel("Reply123").build();

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_revert,
                "Reply123", pendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getChannelId())
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Reply")
                .setContentText("Content")
                .addAction(action);
    }

    private String getChannelId(){
        final String channelId = "directreply";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("All app notification");
            channel.enableVibration(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        return channelId;
    }

    private CharSequence getReplyText(Intent intent){
        Bundle notificationReply = RemoteInput.getResultsFromIntent(intent);

        if(notificationReply != null){
            return notificationReply.getCharSequence(KEY_REPLY_TEXT);
        }

        return null;
    }
}