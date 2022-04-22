package com.nut.flashlightwithheadsup;

import static android.app.Notification.DEFAULT_VIBRATE;
import static androidx.core.app.NotificationCompat.PRIORITY_MAX;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private  static final String ACTION_STOP = "STOP";
    private CameraManager mCameraManager;
    private String mCameraId = null;
    private ToggleButton mButtonLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonLight = findViewById(R.id.buttonLight);
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        mCameraId = getCameraId();
        if(mCameraId == null){
            mButtonLight.setEnabled(false);
        }else{
            mButtonLight.setEnabled(true);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(ACTION_STOP.equals(intent.getAction())){
            setFlashlight(false);
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

    public void clickLight(View view){
        setFlashlight(mButtonLight.isChecked());

        if(mButtonLight.isChecked()){
            showNotification(view);
        }
    }

    private void setFlashlight(boolean enabled){
        mButtonLight.setEnabled(enabled);

        try {
            mCameraManager.setTorchMode(mCameraId, enabled);
        }catch (CameraAccessException e){
            e.printStackTrace();
        }
    }

    public void showNotification(View view){
        final String CHANNEL_ID = "flashlight";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Notifications",
                    NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("All app noti");
            channel.enableVibration(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent activityIntent = new Intent(this, MainActivity.class);
        activityIntent.setAction(ACTION_STOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Flashlight")
                .setContentText("Press to turn off the flashlight")
                .setSmallIcon( R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{DEFAULT_VIBRATE})
                .setPriority(PRIORITY_MAX)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());


    }


}