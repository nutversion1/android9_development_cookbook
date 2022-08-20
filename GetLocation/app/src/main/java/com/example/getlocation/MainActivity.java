package com.example.getlocation;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DateFormat;

public class MainActivity extends AppCompatActivity {
    private final int  REQUEST_RESOLVE_GOOGLE_CLIENT_ERROR = 1;
    boolean mResolvingError;
    GoogleApiClient mGoogleApiClient;

    GoogleApiClient.ConnectionCallbacks mConnectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            Toast.makeText(MainActivity.this,
                    "on connected",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };

    GoogleApiClient.OnConnectionFailedListener mOnConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Toast.makeText(MainActivity.this,
                    connectionResult.toString(),
                    Toast.LENGTH_SHORT).show();

            if(mResolvingError){
                return;
            }else if(connectionResult.hasResolution()){
                mResolvingError = true;

                try {
                    connectionResult.startResolutionForResult(MainActivity.this,
                            REQUEST_RESOLVE_GOOGLE_CLIENT_ERROR);
                } catch (IntentSender.SendIntentException e) {
                  mGoogleApiClient.connect();
                }
            }else{
                showGoogleAPIErrorDialog(connectionResult.getErrorCode());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            getLocation();
        }else{
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {ACCESS_COARSE_LOCATION},
                    1);
        }

        setupGoogleApiClient();
    }

    private void showGoogleAPIErrorDialog(int errorCode) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        Dialog errorDialog = googleApiAvailability.getErrorDialog(
                this,
                errorCode,
                REQUEST_RESOLVE_GOOGLE_CLIENT_ERROR);

        errorDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_RESOLVE_GOOGLE_CLIENT_ERROR) {
            mResolvingError = false;
            if (requestCode == RESULT_OK &&
                    !mGoogleApiClient.isConnecting() &&
                    !mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }
        }
    }

    protected void setupGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(mConnectionCallbacks)
                .addOnConnectionFailedListener(mOnConnectionFailedListener)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }



    private void getLocation() throws SecurityException {
        LocationServices.getFusedLocationProviderClient(this).getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        final TextView textView = findViewById(R.id.textView);

                        if(location != null){
                            textView.setText(DateFormat.getTimeInstance()
                                    .format(location.getTime()) + "\n"
                            + "latitude:"+location.getLatitude() + "\n"
                            + "longtitude:"+location.getLongitude());
                        }else{
                            Toast.makeText(MainActivity.this,
                                    "location null",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}