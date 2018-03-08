package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Pref;
import com.ritvi.kaajneeti.Util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseLanguageActivity extends LocalizationActivity{

    @BindView(R.id.iv_next)
    ImageView iv_next;
    @BindView(R.id.spinner_langauage)
    Spinner spinner_langauage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        ButterKnife.bind(this);



        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner_langauage.getSelectedItem().toString().toLowerCase().equals("hindi")){
                    changeLocale("hi");
                }else{
                    changeLocale("en");
                }
            }
        });
    }


    public void changeLocale(final String lang) {
        Pref.SetStringPref(getApplicationContext(), StringUtils.SELECTED_LANGUAGE,lang);
        setLanguage(lang);
        startActivity(new Intent(ChooseLanguageActivity.this,TermsConditionActivity.class));
    }
}
