package com.ritvi.kaajneeti.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.ViewPagerWithTitleAdapter;
import com.ritvi.kaajneeti.fragment.report.AllReportFragment;
import com.ritvi.kaajneeti.fragment.report.ComplaintReportFragment;
import com.ritvi.kaajneeti.fragment.report.InfoReportFragment;
import com.ritvi.kaajneeti.fragment.report.SuggestionReportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Report");

        setUpTabswithViewPager();
    }

    public void setUpTabswithViewPager() {
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getSupportFragmentManager());
//        final ComplaintFragment complaintFragment = new ComplaintFragment();
        adapter.addFrag(new AllReportFragment(), "All");
        adapter.addFrag(new ComplaintReportFragment(), "Complaint");
        adapter.addFrag(new SuggestionReportFragment(), "Suggestion");
        adapter.addFrag(new InfoReportFragment(), "Information");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
