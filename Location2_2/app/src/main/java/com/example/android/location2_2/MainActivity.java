package com.example.android.location2_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    protected ActivityDetectionBroadcastReceiver mBroadcastReceiver;
    private TextView mStatus;
    private Button mRequestButton;
    private Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatus = (TextView) findViewById(R.id.detectedActivities);
        mRequestButton = (Button) findViewById(R.id.request_activity_updates_button);
        mCancelButton = (Button) findViewById(R.id.remove_activity_updates_button);
        mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();

        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(Constants.BROADCAST_ACTION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<DetectedActivity> activities = intent.getParcelableArrayListExtra(
                    DetectedActivitiesIntentService.DETECTED_ACTIVITIES_EXTRA);

            String strStatus = "";

            for (DetectedActivity detectedActivity : activities) {
                strStatus += getActivityString(detectedActivity) + "\n";
            }

            mStatus.setText(strStatus);

        }

    }

    private String getActivityString(DetectedActivity detectedActivity) {
        Resources resources = this.getResources();
        String activity;
        switch (detectedActivity.getType()) {
            case DetectedActivity.IN_VEHICLE:
                activity = resources.getString(R.string.in_vehicle);
            case DetectedActivity.ON_BICYCLE:
                activity = resources.getString(R.string.on_bicycle);
            case DetectedActivity.ON_FOOT:
                activity = resources.getString(R.string.on_foot);
            case DetectedActivity.RUNNING:
                activity = resources.getString(R.string.running);
            case DetectedActivity.STILL:
                activity = resources.getString(R.string.still);
            default:
                activity = resources.getString(R.string.unidentifiable_activity);
        }

        return activity + " " + detectedActivity.getConfidence() + "%";
    }
}
