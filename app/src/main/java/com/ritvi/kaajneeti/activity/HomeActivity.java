package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.tamir7.contacts.Contacts;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.ritvi.kaajneeti.MainApplication;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.Pref;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.activity.Profile.ProfileDescriptionActivity;
import com.ritvi.kaajneeti.adapter.ViewPagerAdapter;
import com.ritvi.kaajneeti.fragment.AllComplaintsFragment;
import com.ritvi.kaajneeti.fragment.CreateComplaintReplyFragment;
import com.ritvi.kaajneeti.fragment.RewardsFragment;
import com.ritvi.kaajneeti.fragment.analyze.ALLPostListFragment;
import com.ritvi.kaajneeti.fragment.analyze.AllEventFragment;
import com.ritvi.kaajneeti.fragment.analyze.AllPollFragment;
import com.ritvi.kaajneeti.fragment.analyze.InformationListFragment;
import com.ritvi.kaajneeti.fragment.analyze.SuggestionListFragment;
import com.ritvi.kaajneeti.fragment.complaint.ComplaintDetailFragment;
import com.ritvi.kaajneeti.fragment.complaint.ComplaintTrackFragment;
import com.ritvi.kaajneeti.fragment.group.CreateGroupFragment;
import com.ritvi.kaajneeti.fragment.group.SearchParticipantsFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.ContributeFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.InvestigateFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.KaajFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.MyConnectionFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.WalletFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.WithdrawalFragment;
import com.ritvi.kaajneeti.fragment.post.EventViewFragment;
import com.ritvi.kaajneeti.fragment.post.PostViewFragment;
import com.ritvi.kaajneeti.fragment.profile.UpdateAddressFragment;
import com.ritvi.kaajneeti.fragment.profile.UpdateEducationFragment;
import com.ritvi.kaajneeti.fragment.profile.UpdateUserProfileFragment;
import com.ritvi.kaajneeti.fragment.profile.UpdateWorkFragment;
import com.ritvi.kaajneeti.fragment.profile.UserProfileFragment;
import com.ritvi.kaajneeti.fragment.profile.friends.FriendsListFragment;
import com.ritvi.kaajneeti.fragment.promotion.AudienceFragment;
import com.ritvi.kaajneeti.fragment.promotion.EngagementFragment;
import com.ritvi.kaajneeti.fragment.promotion.PromotionAccountFragment;
import com.ritvi.kaajneeti.fragment.promotion.TitleAttachmentFragment;
import com.ritvi.kaajneeti.fragment.search.SearchFragment;
import com.ritvi.kaajneeti.fragment.setting.SettingFragment;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
import com.ritvi.kaajneeti.pojo.home.EventPOJO;
import com.ritvi.kaajneeti.pojo.home.PostPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.pojo.userdetail.AddressPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.EducationPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.WorkPOJO;
import com.ritvi.kaajneeti.testing.CreateExpressActivity;
import com.ritvi.kaajneeti.views.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    List<Fragment> fragmentList = new ArrayList<>();

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    private Toolbar toolbar;
    @BindView(R.id.ic_ham)
    ImageView ic_ham;
    //    @BindView(R.id.iv_search)
//    ImageView iv_search;

    @BindView(R.id.frame_main)
    FrameLayout frame_main;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    @BindView(R.id.ll_kaaj)
    LinearLayout ll_kaaj;
    @BindView(R.id.ll_unioun)
    LinearLayout ll_unioun;
    @BindView(R.id.ll_investigate)
    LinearLayout ll_investigate;
    @BindView(R.id.ll_withdrawal)
    LinearLayout ll_withdrawal;
    @BindView(R.id.iv_speaker)
    ImageView iv_speaker;
    @BindView(R.id.iv_notification)
    ImageView iv_notification;
    @BindView(R.id.iv_settings)
    ImageView iv_settings;
    @BindView(R.id.frame_search)
    FrameLayout frame_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Contacts.initialize(this);
        AppCenter.start(getApplication(), "43ff5ee0-d4c1-4ce4-bbd8-f5c988483281",
                Analytics.class, Crashes.class);

        ButterKnife.bind(this);
        settingNavDrawer();
        setupViewPager(viewPager);
        viewPager.setPagingEnabled(false);

        ll_kaaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        ll_unioun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        ll_investigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        ll_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });
        iv_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreateExpressActivity.class));
            }
        });
        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
            }
        });
        iv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            }
        });

        frame_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchFragment();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getApplication() instanceof MainApplication) {
            MainApplication application = (MainApplication) getApplication();
            Tracker mTracker = application.getDefaultTracker();
            Log.d(TagUtils.getTag(), "local class name:-" + this.getLocalClassName());
            mTracker.setScreenName(this.getLocalClassName());
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    KaajFragment kaajFragment;
    MyConnectionFragment myConnectionFragment;
    InvestigateFragment investigateFragment;
    WithdrawalFragment withdrawalFragment;

    private void setupViewPager(ViewPager viewPager) {

        kaajFragment = new KaajFragment();
        myConnectionFragment = new MyConnectionFragment();
        investigateFragment = new InvestigateFragment();
        withdrawalFragment = new WithdrawalFragment();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(kaajFragment, "Rural");
        adapter.addFrag(myConnectionFragment, "Urban");
        adapter.addFrag(investigateFragment, "Urban");
        adapter.addFrag(withdrawalFragment, "Urban");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        myConnectionFragment.initialize();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void settingNavDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setupDrawerContent(nvDrawer);

        View headerLayout = nvDrawer.inflateHeaderView(R.layout.home_nav_header);
        TextView tv_header_title = headerLayout.findViewById(R.id.tv_header_title);

        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                showUserProfileFragment(Constants.userProfilePOJO.getUserId(), Constants.userProfilePOJO.getUserProfileId());
            }
        });

        tv_header_title.setText(Constants.userProfilePOJO.getFirstName());

        ImageView cv_profile_pic = headerLayout.findViewById(R.id.cv_profile_pic);

        Glide.with(getApplicationContext())
                .load(Constants.userProfilePOJO.getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this, ProfilePageActivity.class));
                startActivity(new Intent(HomeActivity.this, ProfileDescriptionActivity.class).putExtra("userProfilePOJO", Constants.userProfilePOJO));
            }
        });


        setupDrawerToggle();
//        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(false);

        nvDrawer.setItemIconTintList(null);
        ic_ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here

            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawer.addDrawerListener(drawerToggle);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_explore:
                viewPager.setCurrentItem(0);
                break;
            case R.id.nav_connect:
//                showMyConnectionFragment();
                viewPager.setCurrentItem(1);
                break;
            case R.id.nav_act:
                startActivity(new Intent(this, CreateExpressActivity.class));
                break;
            case R.id.nav_analyze:
                viewPager.setCurrentItem(2);
                break;
            case R.id.nav_elect:
//                startActivity(new Intent(HomeActivity.this, FavoriteLeaderActivity.class));
//                startActivity(new Intent(HomeActivity.this, PayUMoneyIntegration.class));
                showContributeFragment();
                break;
            case R.id.nav_setting:
//                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                showSettingFragment();
                break;
            case R.id.nav_earn:
//                showEarnFragment();
                viewPager.setCurrentItem(3);
                break;
            case R.id.nav_wallet:
                showWalletFragment();
                break;
            case R.id.nav_logout:
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false);
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_COMPLETED, false);
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_SKIPPED, false);
                Pref.SetIntPref(getApplicationContext(), StringUtils.USER_TYPE, Constants.USER_TYPE_NONE);
                startActivity(new Intent(HomeActivity.this, SplashActivity.class));
                finishAffinity();
                break;
        }
        mDrawer.closeDrawers();
    }

    private void showWalletFragment() {
        WalletFragment walletFragment = new WalletFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_frag, walletFragment, "walletFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(myConnectionFragment);
    }

    public void showMyConnectionFragment() {
        MyConnectionFragment myConnectionFragment = new MyConnectionFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_frag, myConnectionFragment, "myConnectionFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(myConnectionFragment);
    }

    public void showEarnFragment() {
        RewardsFragment rewardsFragment = new RewardsFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_frag, rewardsFragment, "rewardsFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(rewardsFragment);
    }


    public void showContributeFragment() {
        ContributeFragment contributeFragment = new ContributeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_frag, contributeFragment, "contributeFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(contributeFragment);
    }

    public void showPostViewFragment(PostPOJO postPOJO) {
        PostViewFragment postViewFragment = new PostViewFragment(postPOJO);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, postViewFragment, "postViewFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(postViewFragment);
    }

    public void showEventViewFragment(EventPOJO eventPOJO) {
        EventViewFragment eventViewFragment = new EventViewFragment(eventPOJO);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, eventViewFragment, "eventViewFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(eventViewFragment);
    }

    UserProfileFragment userProfileFragment;

    public void showUserProfileFragment(String user_id, String user_profile_id) {
        userProfileFragment = new UserProfileFragment(user_id, user_profile_id);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, userProfileFragment, "userProfileFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(userProfileFragment);
    }

    UpdateUserProfileFragment updateUserProfileFragment;

    public void showProfileEditFragment(String user_id, String user_profile_id) {
        updateUserProfileFragment = new UpdateUserProfileFragment(user_id, user_profile_id);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, updateUserProfileFragment, "updateUserProfileFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(updateUserProfileFragment);
    }

    public void showUpdateAddressFragment(String user_id, String user_profile_id, AddressPOJO addressPOJO) {
        UpdateAddressFragment updateAddressFragment = new UpdateAddressFragment(user_id, user_profile_id, addressPOJO);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, updateAddressFragment, "updateAddressFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(updateAddressFragment);
    }

    public void showEducationUpdateFragment(String user_id, String user_profile_id, EducationPOJO educationPOJO) {
        UpdateEducationFragment updateEducation = new UpdateEducationFragment(user_id, user_profile_id, educationPOJO);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, updateEducation, "updateEducation");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(updateEducation);
    }

    public void showUpdateWorkFragment(String user_id, String user_profile_id, WorkPOJO workPOJO) {
        UpdateWorkFragment updateWorkFragment = new UpdateWorkFragment(user_id, user_profile_id, workPOJO);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, updateWorkFragment, "updateWorkFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(updateWorkFragment);
    }

    public void refreshUserProfileEditFragment() {
        if (updateUserProfileFragment != null) {
            updateUserProfileFragment.getAllProfileData();
        }
    }

    public void refreshUserProfileFragment() {
        if (userProfileFragment != null) {
            userProfileFragment.getAllProfileData();
        }
    }

    public void showSearchFragment() {
        SearchFragment searchFragment = new SearchFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, searchFragment, "searchFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(searchFragment);
    }

    public void showSettingFragment() {
        SettingFragment settingFragment = new SettingFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, settingFragment, "settingFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(settingFragment);
    }

    public void showComplaintDescription(ComplaintPOJO complaintPOJO) {
        ComplaintDetailFragment complaintDetailFragment = new ComplaintDetailFragment(complaintPOJO);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, complaintDetailFragment, "complaintDetailFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(complaintDetailFragment);
    }

    ComplaintTrackFragment complaintTrackFragment;

    public void showComplaintTrackFragment(ComplaintPOJO complaintPOJO) {
        complaintTrackFragment = new ComplaintTrackFragment(complaintPOJO);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, complaintTrackFragment, "complaintTrackFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(complaintTrackFragment);
    }

    public void refreshComplaintTrack() {
        if (complaintTrackFragment != null) {
            complaintTrackFragment.callAPI();
        }
    }

    public void showComplaintReplyFragment(ComplaintPOJO complaintPOJO) {
        CreateComplaintReplyFragment complaintReplyFragment = new CreateComplaintReplyFragment(complaintPOJO);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, complaintReplyFragment, "complaintReplyFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(complaintReplyFragment);
    }

    public void showEngagementFragment() {
        EngagementFragment engagementFragment = new EngagementFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, engagementFragment, "engagementFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(engagementFragment);
    }

    public void showPromotionAccountFragment() {
        PromotionAccountFragment promotionAccountFragment = new PromotionAccountFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, promotionAccountFragment, "promotionAccountFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(promotionAccountFragment);
    }

    public void showAudienceFragment() {
        AudienceFragment audienceFragment = new AudienceFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, audienceFragment, "audienceFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(audienceFragment);
    }

    public void showTitleAttachmentFragment() {
        TitleAttachmentFragment titleAttachmentFragment = new TitleAttachmentFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, titleAttachmentFragment, "titleAttachmentFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(titleAttachmentFragment);
    }

    public SearchParticipantsFragment searchParticipantsFragment;

    public void showSearchParticipantFragment() {
        searchParticipantsFragment = new SearchParticipantsFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, searchParticipantsFragment, "searchParticipantsFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(searchParticipantsFragment);
    }

    public void showCreateGroupFragment(List<UserProfilePOJO> userProfilePOJOS) {
        CreateGroupFragment createGroupFragment = new CreateGroupFragment(userProfilePOJOS);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, createGroupFragment, "createGroupFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(createGroupFragment);
    }

    public void showFriendListFragment() {
        FriendsListFragment friendsListFragment = new FriendsListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, friendsListFragment, "friendsListFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(friendsListFragment);
    }


    public void showComplaintListFragment() {
        AllComplaintsFragment complaintListFragment = new AllComplaintsFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, complaintListFragment, "complaintListFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showSuggestionListFragment() {
        SuggestionListFragment suggestionListFragment = new SuggestionListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, suggestionListFragment, "suggestionListFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showInformationFragment() {
        InformationListFragment informationListFragment = new InformationListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, informationListFragment, "informationListFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showAllPostFragment() {
        ALLPostListFragment allPostListFragment = new ALLPostListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, allPostListFragment, "allPostListFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showALLEventFragment() {
        AllEventFragment allEventFragment = new AllEventFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, allEventFragment, "allEventFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void shoAllPollFragment() {
        AllPollFragment allPollFragment = new AllPollFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_home, allPollFragment, "allPollFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
