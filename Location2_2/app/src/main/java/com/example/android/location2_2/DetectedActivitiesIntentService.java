package com.example.android.location2_2;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zacharytamas on 8/6/15.
 */
public class DetectedActivitiesIntentService extends IntentService {
    protected static final String TAG = "detection_is";
    public static final String DETECTED_ACTIVITIES_EXTRA = "detected_activities_extra";

    public DetectedActivitiesIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult recognitionResult = ActivityRecognitionResult.extractResult(intent);

        Intent newIntent = new Intent(Constants.BROADCAST_ACTION);

        ArrayList<DetectedActivity> activities = (ArrayList) recognitionResult.getProbableActivities();
        newIntent.putExtra(DETECTED_ACTIVITIES_EXTRA, activities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
