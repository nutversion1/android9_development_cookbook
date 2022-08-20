package com.example.geofence;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceIntentService extends IntentService {
    public GeofenceIntentService() {
        super("GeofenceIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if(geofencingEvent.hasError()){
            Toast.makeText(getApplicationContext(),
                    "geofence error: "+geofencingEvent.getErrorCode(),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL){
            Toast.makeText(getApplicationContext(),
                    "GEOFENCE_TRANSITION_DWELL",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
