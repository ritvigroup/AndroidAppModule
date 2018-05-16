package com.ritvi.kaajneeti.fragment.help;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.fragment.setting.AppSettingFragment;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpSupportFragment extends Fragment {


    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.ll_report_problem)
    LinearLayout ll_report_problem;
    @BindView(R.id.ll_terms)
    LinearLayout ll_terms;
    @BindView(R.id.ll_help_center)
    LinearLayout ll_help_center;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_help_support, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        ll_report_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.replaceFragmentinFrameHome(new ReportProblemFragment(),"ReportProblemFragment");
                }
            }
        });
        ll_help_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.replaceFragmentinFrameHome(new HelpCenterFragment(),"HelpCenterFragment");
                }
            }
        });

        ll_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.replaceFragmentinFrameHome(new TermsConditionFragment(),"TermsConditionFragment");
                }
            }
        });
    }
}
