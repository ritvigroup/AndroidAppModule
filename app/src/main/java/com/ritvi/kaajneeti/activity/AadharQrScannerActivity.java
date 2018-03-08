package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ritvi.kaajneeti.Util.TagUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class AadharQrScannerActivity extends LocalizationActivity {
    private IntentIntegrator qrScan;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dada) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, dada);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result not found", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Log.d(TagUtils.getTag(),"contents:-"+result.getContents());
                    XmlPullParserFactory pullParserFactory;
                    try {
                        pullParserFactory = XmlPullParserFactory.newInstance();
                        XmlPullParser parser = pullParserFactory.newPullParser();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, dada);
        }
    }
}