package com.ritvi.kaajneeti.fragment.contribute;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.twitter.sdk.android.core.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
public class ContributeAmountFragment extends Fragment {

    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.btn_50)
    Button btn_50;
    @BindView(R.id.btn_100)
    Button btn_100;
    @BindView(R.id.btn_500)
    Button btn_500;
    @BindView(R.id.check_include_wallet)
    CheckBox check_include_wallet;
    @BindView(R.id.btn_pay)
    Button btn_pay;

    UserProfilePOJO userProfilePOJO;

    public ContributeAmountFragment(UserProfilePOJO userProfilePOJO) {
        this.userProfilePOJO = userProfilePOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_contribute_amount, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(getActivity().getApplicationContext())
                .load(userProfilePOJO.getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);
        tv_profile_name.setText(userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName());

        btn_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAmount("50");
            }
        });

        btn_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAmount("100");
            }
        });

        btn_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAmount("500");
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.replaceFragmentinFrameHome(new CompleteContributionFragment(userProfilePOJO, et_amount.getText().toString()), "CompleteContributionFragment");
            }
        });

    }

    public void addAmount(String amount) {
        try {
            int entered_amount = Integer.parseInt(amount);
            if (et_amount.getText().toString().length() == 0) {
                et_amount.setText(amount);
            } else {
                int previous_amount = Integer.parseInt(et_amount.getText().toString());
                et_amount.setText(String.valueOf(previous_amount + entered_amount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
