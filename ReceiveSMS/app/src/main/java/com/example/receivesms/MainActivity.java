package com.example.receivesms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        if(!checkPermission(Manifest.permission.RECEIVE_SMS)){
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECEIVE_SMS},
                    0);
        }

        //
          TextView textView = findViewById(R.id.textView);

        try{
            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/"),
                    null, null, null, null);

            while(cursor.moveToNext()) {
                textView.append("From :" + cursor.getString(1) + " : " + cursor.getString(11) + "\n");
            }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }



    }

    private boolean checkPermission(String permission){
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);

        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }
}