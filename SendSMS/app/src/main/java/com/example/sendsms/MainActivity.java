package com.example.sendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final int SEND_SMS_PERMISSION_REQUEST_CDOE = 1;

    Button mButtonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSend = findViewById(R.id.buttonSend);
        mButtonSend.setEnabled(false);

        if(checkPermission(Manifest.permission.SEND_SMS)){
            mButtonSend.setEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CDOE);
        }
    }

    public void send(View view){
        String phoneNumber = ((EditText) findViewById(R.id.editTextNumber)).getText().toString();
        String msg = ((EditText) findViewById(R.id.editTextMsg)).getText().toString();

        if(phoneNumber == null || phoneNumber.length() == 0 ||
                msg == null || msg.length() == 0 ){
            return;
        }

        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();

            //smsManager.sendTextMessage(phoneNumber, null, msg, null, null);

            ArrayList<String> messages = smsManager.divideMessage(msg);
            smsManager.sendMultipartTextMessage(phoneNumber, null, messages, null, null);
        }else{
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission(String permission){
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);

        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CDOE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mButtonSend.setEnabled(true);
                }
            }

            return;
        }
    }
}