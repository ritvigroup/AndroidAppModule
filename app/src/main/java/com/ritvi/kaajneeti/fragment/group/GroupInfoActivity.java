package com.ritvi.kaajneeti.fragment.group;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class GroupInfoActivity extends AppCompatActivity {
    private GroupPOJO groupPOJO;

    @BindView(R.id.rv_participants)
    RecyclerView rv_participants;
    @BindView(R.id.cv_exit_group)
    CardView cv_exit_group;
    @BindView(R.id.expandedImage)
    ImageView expandedImage;
    @BindView(R.id.iv_participant_search)
    ImageView iv_participant_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        ButterKnife.bind(this);
        attachPariticipantsAdapter();
        groupPOJO = (GroupPOJO) getIntent().getSerializableExtra("groupPOJO");

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
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
            getSupportActionBar().setTitle(groupPOJO.getFriendGroupName());
            Glide.with(getApplicationContext())
                    .load(groupPOJO.getFriendGroupPhoto())
                    .placeholder(R.drawable.ic_default_pic)
                    .dontAnimate()
                    .into(expandedImage);
        }

        iv_participant_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchParticipantsFragment searchParticipantsFragment=new SearchParticipantsFragment(groupPOJO);
                addFragmentinFrameHome(searchParticipantsFragment,"searchParticipantsFragment");
            }
        });
    }

    List<UserProfilePOJO> participantsPOJO = new ArrayList<>();
    GroupParticipantsAdapter sentRequestAdapter;

    public void attachPariticipantsAdapter() {
        sentRequestAdapter = new GroupParticipantsAdapter(this, null, participantsPOJO);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_participants.setHasFixedSize(true);
        rv_participants.setAdapter(sentRequestAdapter);
        rv_participants.setLayoutManager(linearLayoutManager);
        rv_participants.setItemAnimator(new DefaultItemAnimator());
    }


    public void addFragmentinFrameHome(Fragment fragment, String fragment_name) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_main, fragment)
                .addToBackStack(fragment_name)
                .commit();
    }

    public void replaceFragmentinFrameHome(Fragment fragment, String fragment_name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_main, fragment)
                .addToBackStack(fragment_name)
                .commit();
    }


}
