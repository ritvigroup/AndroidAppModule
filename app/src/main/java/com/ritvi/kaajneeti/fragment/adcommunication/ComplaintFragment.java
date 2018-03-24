package com.ritvi.kaajneeti.fragment.adcommunication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.ViewPagerAdapter;
import com.ritvi.kaajneeti.fragment.complaint.GroupComplaintFragment;
import com.ritvi.kaajneeti.fragment.complaint.OtherComplaintFragment;
import com.ritvi.kaajneeti.fragment.complaint.SelfComplaintFragment;
import com.ritvi.kaajneeti.views.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 07-02-2018.
 */

public class ComplaintFragment extends Fragment {

    @BindView(R.id.complaintViewPager)
    CustomViewPager complaintViewPager;
    @BindView(R.id.rb_self)
    RadioButton rb_self;
    @BindView(R.id.rb_other)
    RadioButton rb_other;
    @BindView(R.id.rb_group)
    RadioButton rb_group;
    @BindView(R.id.rg_comp_type)
    RadioGroup rg_comp_type;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_complaint, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        callComplaintAPI();
        setupViewPager(complaintViewPager);
        rg_comp_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedId = rg_comp_type.getCheckedRadioButtonId();
                switch (checkedId) {
                    case R.id.rb_self:
                        complaintViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_other:
                        complaintViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_group:
                        complaintViewPager.setCurrentItem(2);
                        break;
                }
            }
        });

        rb_self.setChecked(true);

    }

    SelfComplaintFragment selfComplaintFragment;
    GroupComplaintFragment groupComplaintFragment;
    OtherComplaintFragment otherComplaintFragment;

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(selfComplaintFragment = new SelfComplaintFragment(), "Self Complaint");
        adapter.addFrag(otherComplaintFragment = new OtherComplaintFragment(), "Group Complaint");
        adapter.addFrag(groupComplaintFragment = new GroupComplaintFragment(), "Other Complaint");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);

    }


}
