package com.ritvi.kaajneeti;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate;
import com.github.tamir7.contacts.Contacts;

/**
 * Created by sunil on 08-03-2018.
 */

public class MainApplication extends Application {
    LocalizationApplicationDelegate localizationDelegate = new LocalizationApplicationDelegate(this);

    @Override
    public void onCreate() {
        super.onCreate();
        Contacts.initialize(this);
//        final TwitterAuthConfig authConfig = new TwitterAuthConfig("odTUxR2y7jhDIb1ImhiGE4VDY", "FFKtAo7BeyDoEoUeRXZUq1FwHAjCHutOXZc4gcimEmG4cOMWKV");
//
//        Fabric.with(this, new Twitter(authConfig));
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

}
