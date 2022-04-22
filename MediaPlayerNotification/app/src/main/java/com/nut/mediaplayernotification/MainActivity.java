package com.nut.mediaplayernotification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressWarnings("deprecation")
    public void showNotification(View view){
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Notification.Builder notificationBuilder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            notificationBuilder = new Notification.Builder(this)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setSmallIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .addAction(new Notification.Action.Builder(
                            Icon.createWithResource(this, android.R.drawable.ic_media_previous),
                            "Previous",
                            pendingIntent).build())
                    .addAction(new Notification.Action.Builder(
                            Icon.createWithResource(this, android.R.drawable.ic_media_pause),
                            "Pause",
                            pendingIntent).build())
                    .addAction(new Notification.Action.Builder(
                            Icon.createWithResource(this, android.R.drawable.ic_media_next),
                            "Next",
                            pendingIntent).build())
                    .setContentTitle("Music")
                    .setContentText("Now Playing...")
                    .setLargeIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setStyle(new Notification.MediaStyle().setShowActionsInCompactView(1));
        }else{
            notificationBuilder = new Notification.Builder(this)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setSmallIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .addAction(new Notification.Action.Builder(
                            android.R.drawable.ic_media_previous,
                            "Previous",
                            pendingIntent).build())
                    .addAction(new Notification.Action.Builder(
                            android.R.drawable.ic_media_pause,
                            "Pause",
                            pendingIntent).build())
                    .addAction(new Notification.Action.Builder(
                            android.R.drawable.ic_media_next,
                            "Next",
                            pendingIntent).build())
                    .setContentTitle("Music")
                    .setContentText("Now Playing...")
                    .setLargeIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setStyle(new Notification.MediaStyle().setShowActionsInCompactView(1));
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationBuilder.setChannelId(createChannel());
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private String createChannel() {
        final String channelId = "mediaPlayer";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("All app notification");
            channel.enableVibration(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        return channelId;
    }
}