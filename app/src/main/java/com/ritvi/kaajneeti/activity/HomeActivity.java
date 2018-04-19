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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.ritvi.kaajneeti.fragment.RewardsFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.ContributeFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.InvestigateFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.KaajFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.MyConnectionFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.WalletFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.WithdrawalFragment;
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
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_search_close)
    ImageView iv_search_close;
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

//        iv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
//                overridePendingTransition(R.anim.enter, R.anim.exit);
//            }
//        });

        iv_search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_search.getText().toString().length() > 0) {
                    iv_search_close.setVisibility(View.VISIBLE);
                } else {
                    iv_search_close.setVisibility(View.GONE);
                }
            }
        });

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
                startActivity(new Intent(HomeActivity.this, AddPostActivity.class));
            }
        });
        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,NotificationActivity.class));
            }
        });
        iv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,SettingsActivity.class));
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
        tv_header_title.setText(Constants.userInfoPOJO.getUserName());

        ImageView cv_profile_pic = headerLayout.findViewById(R.id.cv_profile_pic);

        Glide.with(getApplicationContext())
                .load(Constants.userInfoPOJO.getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this, ProfilePageActivity.class));
                startActivity(new Intent(HomeActivity.this, ProfileDescriptionActivity.class).putExtra("userInfo",Constants.userInfoPOJO));
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
                startActivity(new Intent(this, AddPostActivity.class));
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
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
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
        transaction.add(R.id.frame_frag, walletFragment , "walletFragment");
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


    public void showContributeFragment(){
        ContributeFragment contributeFragment = new ContributeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_frag, contributeFragment, "contributeFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(contributeFragment);
    }
}
