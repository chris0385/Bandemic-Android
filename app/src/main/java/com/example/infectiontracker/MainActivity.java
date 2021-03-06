package com.example.infectiontracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private final boolean showContactLogger = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        if(showContactLogger) {
            startActivity(new Intent(this, ContactLogger.class));
        }


        //TODO: Query db and call setStatusNotInfected
        //and set last check time

    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        else {
            startTracingService();
        }
    }

    private void startTracingService() {
        Intent intent = new Intent(this, TracingService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    findViewById(R.id.card_permission_required).setVisibility(View.GONE);
                    // Start background service
                    startTracingService();
                } else {
                    // Don't start the discovery service
                    findViewById(R.id.card_permission_required).setVisibility(View.VISIBLE);
                    findViewById(R.id.ask_permission).setOnClickListener(view -> checkPermissions());
                }
                return;
            }
        }
    }

    /*
    private void setStatusNotInfected(String lastCheckTime) {
        findViewById(R.id.layout_not_infected1).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_not_infected2).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_time).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.main_status_last_check)).setText(lastCheckTime);
    }

    private void setStatusTrackingTimeNeeded() {
        findViewById(R.id.layout_not_infected1).setVisibility(View.GONE);
        findViewById(R.id.layout_not_infected2).setVisibility(View.GONE);
        findViewById(R.id.layout_time).setVisibility(View.VISIBLE);
    }*/

}
