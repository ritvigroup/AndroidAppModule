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
import android.widget.ImageView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.ViewPagerWithTitleAdapter;
import com.ritvi.kaajneeti.fragment.analyze.ComplaintListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 17-04-2018.
 */

public class AllComplaintsFragment extends Fragment {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_all_complaints, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewPager(viewPager);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    ComplaintListFragment selfcomplaintListFragment;
    ComplaintListFragment associatedcomplaintListFragment;

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getChildFragmentManager());

        selfcomplaintListFragment = new ComplaintListFragment(false);
        associatedcomplaintListFragment = new ComplaintListFragment(true);

        adapter.addFrag(selfcomplaintListFragment, "My Complaints");
        adapter.addFrag(associatedcomplaintListFragment, "Associated");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        tabs.setupWithViewPager(viewPager);
    }
}
