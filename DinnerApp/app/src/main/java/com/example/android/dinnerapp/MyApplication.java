package com.example.android.dinnerapp;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by zacharytamas on 8/7/15.
 */
public class MyApplication extends Application {

    public Tracker mTracker;

    public void startTracking() {
        GoogleAnalytics ga = GoogleAnalytics.getInstance(this);
        ga.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        mTracker = ga.newTracker(R.xml.track_app);
        mTracker.enableAutoActivityTracking(true);
    }

    public Tracker getTracker() {
        if (mTracker == null) {
            startTracking();
        }
        return mTracker;
    }

}
