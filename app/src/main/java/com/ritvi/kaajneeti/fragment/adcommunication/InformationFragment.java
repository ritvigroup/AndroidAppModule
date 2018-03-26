package com.ritvi.kaajneeti.fragment.adcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.activity.InformationSubmittedActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 01-03-2018.
 */

public class InformationFragment extends Fragment{

    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.switch_identity)
    Switch switch_identity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_information,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), InformationSubmittedActivity.class);

                intent.putExtra("applicant_name", Constants.userProfilePojo.getUserName());
                intent.putExtra("applicant_father_name","");
                intent.putExtra("applicant_mobile",Constants.userProfilePojo.getUserMobile());
                startActivity(intent);
            }
        });
    }
}
