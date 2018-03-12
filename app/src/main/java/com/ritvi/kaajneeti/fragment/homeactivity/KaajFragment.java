package com.ritvi.kaajneeti.fragment.homeactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.AddPostActivity;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.testing.FacebookMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 12-03-2018.
 */

public class KaajFragment extends Fragment{

    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.tv_whats_mind)
    TextView tv_whats_mind;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;

    UserProfilePOJO userProfilePOJO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_kaaj,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FacebookMainActivity.class));
            }
        });


        tv_whats_mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddPostActivity.class));
            }
        });

        if(getActivity() instanceof HomeActivity){
            HomeActivity homeActivity= (HomeActivity) getActivity();
            userProfilePOJO=homeActivity.userProfilePOJO;

            inflateNewsFeeds();
            tv_profile_name.setText(userProfilePOJO.getFullname());

            Glide.with(getActivity().getApplicationContext())
                    .load(userProfilePOJO.getProfileImage())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);

        }
    }

    public void inflateNewsFeeds(){
        for(int i=0;i<10;i++){
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_news_feeds, null);

            CircleImageView cv_profile_pic=view.findViewById(R.id.cv_profile_pic);
            TextView tv_profile_name=view.findViewById(R.id.tv_profile_name);

            tv_profile_name.setText(userProfilePOJO.getFullname());
            Glide.with(getActivity().getApplicationContext())
                    .load(userProfilePOJO.getProfileImage())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);


            ll_scroll.addView(view);

        }

    }
}
