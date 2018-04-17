package com.ritvi.kaajneeti.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.NotificationAdapter;
import com.ritvi.kaajneeti.fragment.analyze.ComplaintListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_notification)
    RecyclerView rv_notification;
    @BindView(R.id.frame_main)
    FrameLayout frame_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        attachAdapter();

        attachGroupComplaint();
    }

    public void attachGroupComplaint() {
        ComplaintListFragment complaintListFragment = new ComplaintListFragment(true);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, complaintListFragment, "complaintListFragment");
        transaction.addToBackStack(null);
        transaction.commit();
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
