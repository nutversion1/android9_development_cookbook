package com.example.dialphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    PhoneStateListener mPhoneStateListener = new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String number) {
            String phoneState = number;

            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    phoneState += "idle\n";
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    phoneState += "ringing\n";
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    phoneState += "offhook\n";
                    break;
            }

            TextView textView = findViewById(R.id.textView);
            textView.append(phoneState);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(mPhoneStateListener,
                    PhoneStateListener.LISTEN_CALL_STATE);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void dialPhone(View view){
        String number = "0834545045";

//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        intent.setData(Uri.parse("tel:"+number));
//        startActivity(intent);

        if(checkPermission(Manifest.permission.CALL_PHONE)){
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CALL_PHONE},
                    1);
        }


    }

    private  boolean checkPermission(String permission){
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);

        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }
}