package com.ritvi.kaajneeti.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.ViewPagerWithTitleAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 26-02-2018.
 */

public class NewsFragment extends Fragment{

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private int[] tabIcons = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_news,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpTabswithViewPager();
        setupTabIcons();
    }


    public void setUpTabswithViewPager() {
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
        tabs.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getChildFragmentManager());
        adapter.addFrag(new UpdatesFragment(), "UPDATES");
        adapter.addFrag(new PressCoverageFragment(), "PRESS COVERAGE");
        adapter.addFrag(new PrimeTimeFragment(), "PRIME TIME");
        viewPager.setAdapter(adapter);

    }
}
