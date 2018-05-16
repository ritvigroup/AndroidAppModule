package com.ritvi.kaajneeti.fragment.promotion;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EngagementFragment extends Fragment{

    @BindView(R.id.btn_ad_account)
    Button btn_ad_account;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_engagement,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_ad_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    PromotionAccountFragment promotionAccountFragment = new PromotionAccountFragment();
                    homeActivity.replaceFragmentinFrameHome(promotionAccountFragment,"promotionAccountFragment");
                }
            }
        });
    }
}
