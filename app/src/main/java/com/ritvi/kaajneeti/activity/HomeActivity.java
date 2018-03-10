package com.ritvi.kaajneeti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.tamir7.contacts.Contacts;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.Pref;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.fragment.RewardsFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.MyConnectionFragment;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.testing.FacebookMainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    List<Fragment> fragmentList=new ArrayList<>();

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    private Toolbar toolbar;
    @BindView(R.id.ic_ham)
    ImageView ic_ham;
    UserProfilePOJO userProfilePOJO;
//    @BindView(R.id.iv_search)
//    ImageView iv_search;
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.tv_whats_mind)
    TextView tv_whats_mind;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_search_close)
    ImageView iv_search_close;
    @BindView(R.id.frame_main)
    FrameLayout frame_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Contacts.initialize(this);
        AppCenter.start(getApplication(), "43ff5ee0-d4c1-4ce4-bbd8-f5c988483281",
                Analytics.class, Crashes.class);

        ButterKnife.bind(this);
        userProfilePOJO = Pref.GetUserProfile(getApplicationContext());
        settingNavDrawer();


//        iv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
//                overridePendingTransition(R.anim.enter, R.anim.exit);
//            }
//        });

        inflateNewsFeeds();


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
                if(et_search.getText().toString().length()>0){
                    iv_search_close.setVisibility(View.VISIBLE);
                }else{
                    iv_search_close.setVisibility(View.GONE);
                }
            }
        });

        tv_whats_mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,AddPostActivity.class));
            }
        });
        Log.d(TagUtils.getTag(),"user profile:-"+userProfilePOJO.toString());
        Glide.with(getApplicationContext())
                .load(userProfilePOJO.getProfileImage())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, FacebookMainActivity.class));
            }
        });

        tv_profile_name.setText(userProfilePOJO.getFullname());
    }

    public void inflateNewsFeeds(){
        for(int i=0;i<10;i++){
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_news_feeds, null);

            CircleImageView cv_profile_pic=view.findViewById(R.id.cv_profile_pic);
            TextView tv_profile_name=view.findViewById(R.id.tv_profile_name);

            tv_profile_name.setText(userProfilePOJO.getFullname());
            Glide.with(getApplicationContext())
                    .load(userProfilePOJO.getProfileImage())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);


            ll_scroll.addView(view);

        }

    }

    private void settingNavDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setupDrawerContent(nvDrawer);

        View headerLayout = nvDrawer.inflateHeaderView(R.layout.home_nav_header);
        TextView tv_header_title = headerLayout.findViewById(R.id.tv_header_title);
        tv_header_title.setText(userProfilePOJO.getFullname());

        ImageView cv_profile_pic = headerLayout.findViewById(R.id.cv_profile_pic);

        Glide.with(getApplicationContext())
                .load(userProfilePOJO.getProfileImage())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProfilePageActivity.class).putExtra("user_type", "citizen"));
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
                break;
            case R.id.nav_connect:
                showMyConnectionFragment();
                break;
            case R.id.nav_act:
                tv_whats_mind.callOnClick();
                break;
            case R.id.nav_analyze:
                break;
            case R.id.nav_elect:
                startActivity(new Intent(HomeActivity.this,FavoriteLeaderActivity.class));
                break;
            case R.id.nav_setting:
                startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                break;
            case R.id.nav_earn:
                showEarnFragment();
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

    public void showMyConnectionFragment() {
        MyConnectionFragment myConnectionFragment = new MyConnectionFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, myConnectionFragment, "myConnectionFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(myConnectionFragment);
    }

    public void showEarnFragment() {
        RewardsFragment rewardsFragment = new RewardsFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, rewardsFragment, "rewardsFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentList.add(rewardsFragment);
    }
}
