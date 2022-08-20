package com.example.blockedcalllist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.BlockedNumberContract;
import android.provider.Telephony;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText mEditTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextNumber = findViewById(R.id.editTextNumber);
    }

    public void onClickBlock(View view){
        String number = mEditTextNumber.getText().toString();
        if(number != null && number.length() > 0){
            blockNumber(number);
        }
    }

    public void onClickUnblock(View view){
        String number = mEditTextNumber.getText().toString();
        if(number != null && number.length() > 0){
            unblockNumber(number);
        }
    }

    public void onClickIsBlocked(View view){
        String number = mEditTextNumber.getText().toString();
        if(number != null && number.length() > 0){
            isBlocked(number);
        }
    }

    private void blockNumber(String number) {
        if(BlockedNumberContract.canCurrentUserBlockNumbers(this)){
            ContentValues values = new ContentValues();
            values.put(BlockedNumberContract.BlockedNumbers.COLUMN_E164_NUMBER, number);

            getContentResolver().insert(
                    BlockedNumberContract.BlockedNumbers.CONTENT_URI,
                    values);
        }
    }

    private void unblockNumber(String number) {
        if(BlockedNumberContract.canCurrentUserBlockNumbers(this)){
            ContentValues values = new ContentValues();
            values.put(BlockedNumberContract.BlockedNumbers.COLUMN_E164_NUMBER, number);

            Uri uri = getContentResolver().insert(BlockedNumberContract.BlockedNumbers.CONTENT_URI,
                    values);

            getContentResolver().delete(uri, null, null);
        }
    }

    private void isBlocked(String number) {
        if(BlockedNumberContract.canCurrentUserBlockNumbers(this)){
            boolean blocked = BlockedNumberContract.isBlocked(this, number);

            Toast.makeText(MainActivity.this,
                    number + " blocked: "+blocked,
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,
                    "Cannot perform",
                    Toast.LENGTH_SHORT).show();
        }
    }
}