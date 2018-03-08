package com.ritvi.kaajneeti;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCenter.start(getApplication(), "3e173e70-d1bd-4fc7-a9be-0c20d0d762b5",
                Analytics.class, Crashes.class);
    }
}
