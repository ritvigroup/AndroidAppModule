package com.ritvi.kaajneeti.fragment.myconnection;

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
 * Created by sunil on 28-03-2018.
 */

public class RequestFragment extends Fragment{

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_request,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpTabswithViewPager();
    }

    boolean is_initialized;
    public void initialize(){
        if(!is_initialized) {
            setUpTabswithViewPager();
//            is_initialized=true;
        }
    }


    public void setUpTabswithViewPager() {
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
    }

    IncomingRequestFragment incomingRequestFragment;
    OutgoingFragment outgoingFragment;
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getChildFragmentManager());

        adapter.addFrag(incomingRequestFragment=new IncomingRequestFragment(), "Incoming");
        adapter.addFrag(outgoingFragment=new OutgoingFragment(), "Outgoing");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());

    }
}
