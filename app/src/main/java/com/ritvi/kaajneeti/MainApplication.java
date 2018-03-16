package com.ritvi.kaajneeti;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate;
import com.github.tamir7.contacts.Contacts;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by sunil on 08-03-2018.
 */

public class MainApplication extends Application {
    LocalizationApplicationDelegate localizationDelegate = new LocalizationApplicationDelegate(this);
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;
    @Override
    public void onCreate() {
        super.onCreate();
        Contacts.initialize(this);
        final TwitterAuthConfig authConfig = new TwitterAuthConfig("odTUxR2y7jhDIb1ImhiGE4VDY", "FFKtAo7BeyDoEoUeRXZUq1FwHAjCHutOXZc4gcimEmG4cOMWKV");

        Fabric.with(this, new Twitter(authConfig));
        sAnalytics = GoogleAnalytics.getInstance(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(localizationDelegate.attachBaseContext(base));

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localizationDelegate.onConfigurationChanged(this);
    }

    @Override
    public Context getApplicationContext() {
        return localizationDelegate.getApplicationContext(super.getApplicationContext());
    }
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }
}
