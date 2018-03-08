package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsConditionActivity extends LocalizationActivity {

    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        changeLocale("hi");
        setContentView(R.layout.activity_terms_condition_activityu);

        ButterKnife.bind(this);

        Resources res=getResources();
        DisplayMetrics displayMetrics=res.getDisplayMetrics();
        Configuration conf=res.getConfiguration();
        Log.d(TagUtils.getTag(),"locale language :- "+conf.locale.getLanguage());

//        webView.loadUrl("https://www.sc.com/in/terms-conditions-disclaimer/");
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TermsConditionActivity.this,SliderActivity.class));
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Change Locale
    public void changeLocale(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);//Set Selected Locale
//        saveLocale(lang);//Save the selected locale
        Locale.setDefault(myLocale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = myLocale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
//        updateTexts();//Update texts according to locale
    }
}
