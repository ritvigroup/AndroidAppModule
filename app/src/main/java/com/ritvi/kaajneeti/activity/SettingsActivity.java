package com.ritvi.kaajneeti.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_rate)
    LinearLayout ll_rate;
    @BindView(R.id.ll_share)
    LinearLayout ll_share;
    @BindView(R.id.ll_theme)
    LinearLayout ll_theme;
    @BindView(R.id.ll_change_language)
    LinearLayout ll_change_language;
    @BindView(R.id.ll_notification)
    LinearLayout ll_notification;
    @BindView(R.id.ll_aadhar)
    LinearLayout ll_aadhar;
    @BindView(R.id.ll_identity)
    LinearLayout ll_identity;
    @BindView(R.id.ll_profile_update)
    LinearLayout ll_profile_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        ll_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Kaajneeti");
                    String sAux = "\nGet All the political updates\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id="+getPackageName()+" \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        ll_aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,AadharIntegrationActivity.class));
            }
        });

        ll_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,ProfileInfoActivity.class));
            }
        });

        ll_identity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfileIdentityDialog();
            }
        });

        ll_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowThemeDialog();
            }
        });

    }

    public void showProfileIdentityDialog(){
        final Dialog dialog = new Dialog(SettingsActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_identity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setTitle("");
        dialog.show();
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ImageView iv_close=dialog.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void ShowThemeDialog(){
        final Dialog dialog1 = new Dialog(this,android.R.style.Theme_DeviceDefault_Dialog);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_theme);
        dialog1.setTitle("Default Location");
        dialog1.show();
        dialog1.setCancelable(true);


        TextView tv_blue= (TextView) dialog1.findViewById(R.id.tv_blue);
        TextView tv_red= (TextView) dialog1.findViewById(R.id.tv_red);
        TextView tv_green= (TextView) dialog1.findViewById(R.id.tv_green);
        TextView tv_orange= (TextView) dialog1.findViewById(R.id.tv_orange);
        TextView tv_pink= (TextView) dialog1.findViewById(R.id.tv_pink);
        TextView tv_indigo= (TextView) dialog1.findViewById(R.id.tv_indigo);
        TextView tv_brown= (TextView) dialog1.findViewById(R.id.tv_brown);
        TextView tv_blue_grey= (TextView) dialog1.findViewById(R.id.tv_blue_grey);
        TextView tv_falcon= (TextView) dialog1.findViewById(R.id.tv_falcon);
        TextView tv_light_blue= (TextView) dialog1.findViewById(R.id.tv_light_blue);
        Button cancel= (Button) dialog1.findViewById(R.id.cancel);

//        tv_blue.setOnClickListener(this);
//        tv_red.setOnClickListener(this);
//        tv_green.setOnClickListener(this);
//        tv_orange.setOnClickListener(this);
//        tv_pink.setOnClickListener(this);
//        tv_indigo.setOnClickListener(this);
//        tv_brown.setOnClickListener(this);
//        tv_blue_grey.setOnClickListener(this);
//        tv_falcon.setOnClickListener(this);
//        tv_light_blue.setOnClickListener(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
    }
}
