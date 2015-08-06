package com.example.android.location2_2;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by zacharytamas on 8/6/15.
 */
public class DetectedActivitiesIntentService extends IntentService {
    protected static final String TAG = "detection_is";

    DetectedActivitiesIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
