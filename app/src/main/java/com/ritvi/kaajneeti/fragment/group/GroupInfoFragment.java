package com.ritvi.kaajneeti.fragment.group;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.GroupParticipantsAdapter;
import com.ritvi.kaajneeti.pojo.GroupPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class GroupInfoFragment extends Fragment{

    GroupPOJO groupPOJO;
    public GroupInfoFragment(GroupPOJO groupPOJO){
        this.groupPOJO=groupPOJO;
    }

    @BindView(R.id.rv_participants)
    RecyclerView rv_participants;
    @BindView(R.id.cv_exit_group)
    CardView cv_exit_group;
    @BindView(R.id.expandedImage)
    ImageView expandedImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_group_info,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attachPariticipantsAdapter();
//
//        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);

        AppBarLayout mAppBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                } else if (isShow) {
                    isShow = false;
                }
            }
        });

        cv_exit_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (groupPOJO != null && groupPOJO.getGroupMembers() != null) {
            participantsPOJO.addAll(groupPOJO.getGroupMembers());
            sentRequestAdapter.notifyDataSetChanged();
            toolbar.setTitle(groupPOJO.getFriendGroupName());
            Glide.with(getActivity().getApplicationContext())
                    .load(groupPOJO.getFriendGroupPhoto())
                    .placeholder(R.drawable.ic_default_pic)
                    .dontAnimate()
                    .into(expandedImage);
        }

    }


    List<UserProfilePOJO> participantsPOJO = new ArrayList<>();
    GroupParticipantsAdapter sentRequestAdapter;

    public void attachPariticipantsAdapter() {

        sentRequestAdapter = new GroupParticipantsAdapter(getActivity(), this, participantsPOJO);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_participants.setHasFixedSize(true);
        rv_participants.setAdapter(sentRequestAdapter);
        rv_participants.setLayoutManager(linearLayoutManager);
        rv_participants.setItemAnimator(new DefaultItemAnimator());
    }


}
