package com.ritvi.kaajneeti.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_notification)
    RecyclerView rv_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        attachAdapter();
    }


    List<String> comStringList = new ArrayList<>();
    NotificationAdapter complaitnAnalyzeAdapter;

    public void attachAdapter() {

        comStringList.add("Electricity Issues");
        comStringList.add("Water Issues");
        comStringList.add("Sewage Problems");

        complaitnAnalyzeAdapter = new NotificationAdapter(this, null, comStringList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_notification.setHasFixedSize(true);
        rv_notification.setAdapter(complaitnAnalyzeAdapter);
        rv_notification.setLayoutManager(linearLayoutManager);
        rv_notification.setItemAnimator(new DefaultItemAnimator());
    }
}
