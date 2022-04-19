package com.nut.lightsactionsound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private CameraManager mCameraManager;
    private String mCameraId = null;
    private ToggleButton mButtonLights;

    final String CHANNEL_ID = "notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonLights = findViewById(R.id.buttonLights);


        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        mCameraId = getCameraId();
        if(mCameraId == null){
            mButtonLights.setEnabled(false);
        }else{
            mButtonLights.setEnabled(true);
        }

    }

    private String getCameraId(){
        try {
            String[] ids = mCameraManager.getCameraIdList();

            for(String id : ids){
                CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                Integer facingDirection = c.get(CameraCharacteristics.LENS_FACING);

                if(flashAvailable != null
                        && flashAvailable
                        && facingDirection != null
                        && facingDirection == CameraCharacteristics.LENS_FACING_BACK){
                    return id;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void clickLights(View view){

        try {
            mCameraManager.setTorchMode(mCameraId, mButtonLights.isChecked());
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    public void clickVibrate(View view){
        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(1000);
    }

    public void clickSound(View view){
        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notificationSoundUri);
        ringtone.play();
    }

    public void clickLightsActionSound(View view){
        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .build();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("All app notifications");
            channel.setSound(notificationSoundUri, audioAttributes);
            channel.setLightColor(Color.BLUE);
            channel.enableLights(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Lights, Action & Sound")
                .setSound(notificationSoundUri)
                .setLights(Color.BLUE, 500, 500)
                .setVibrate(new long[]{250,500,250,500,250,500})
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, notificationBuilder.build());
    }
}