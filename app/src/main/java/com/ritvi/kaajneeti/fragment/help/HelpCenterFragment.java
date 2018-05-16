package com.ritvi.kaajneeti.fragment.help;

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
import com.ritvi.kaajneeti.adapter.ViewPagerAdapter;
import com.ritvi.kaajneeti.fragment.homeactivity.InvestigateFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.KaajFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.MyConnectionFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.WithdrawalFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpCenterFragment extends Fragment{

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_help_center,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        FAQHelpCenterFragment faqHelpCenterFragment=new FAQHelpCenterFragment();
        CallHelpCenterFragment callHelpCenterFragment=new CallHelpCenterFragment();
        EmailHelpCenterFragment emailHelpCenterFragment=new EmailHelpCenterFragment();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(faqHelpCenterFragment, "FAQ");
        adapter.addFrag(callHelpCenterFragment, "CALL");
        adapter.addFrag(emailHelpCenterFragment, "EMAIL");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());

        tabs.setupWithViewPager(viewPager);
    }
}
