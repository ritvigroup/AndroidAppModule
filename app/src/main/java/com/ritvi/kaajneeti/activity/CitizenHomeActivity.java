package com.ritvi.kaajneeti.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.github.tamir7.contacts.Contacts;
import com.google.gson.Gson;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.Pref;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.adapter.ViewPagerWithTitleAdapter;
import com.ritvi.kaajneeti.fragment.CalendarFragment;
import com.ritvi.kaajneeti.fragment.DashBoardFragment;
import com.ritvi.kaajneeti.fragment.HomeFragment;
import com.ritvi.kaajneeti.fragment.InfoDirectoryFragment;
import com.ritvi.kaajneeti.fragment.MyCommunicationFragment;
import com.ritvi.kaajneeti.fragment.MyConnectionFragment;
import com.ritvi.kaajneeti.fragment.NewsFragment;
import com.ritvi.kaajneeti.fragment.RewardsFragment;
import com.ritvi.kaajneeti.fragment.SocialBuzzFragment;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.testing.FacebookAppInvitationActivity;
import com.ritvi.kaajneeti.webservice.AdapterWebService;
import com.ritvi.kaajneeti.webservice.MsgPassInterface;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitizenHomeActivity extends LocalizationActivity {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    private Toolbar toolbar;
    @BindView(R.id.ic_ham)
    ImageView ic_ham;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_search)
    ImageView iv_search;

    Spinner spinner_profile;
    UserProfilePOJO userProfilePOJO;

    private int[] tabIcons = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    //    @Rule
//    public ReportHelper reportHelper = Factory.getReportHelper();
//
//    @After
//    public void TearDown(){
//        reportHelper.label("Stopping App");
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Contacts.initialize(this);

        AppCenter.start(getApplication(), "142bb43b-0f4a-446b-8815-0d3b6331d082",
                Analytics.class, Crashes.class);
        AppCenter.start(getApplication(), "142bb43b-0f4a-446b-8815-0d3b6331d082", Analytics.class, Crashes.class);

        userProfilePOJO = Pref.GetUserProfile(getApplicationContext());
        tv_title.setText("Home");
        settingNavDrawer();
        setUpTabswithViewPager();
//        setupTabIcons();

//        bmb.setButtonEnum(ButtonEnum.SimpleCircle);
//        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);
//        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
//        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
//            bmb.addBuilder(BuilderManager.getSimpleCircleButtonBuilder());
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CitizenHomeActivity.this, SearchActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    public void setUpTabswithViewPager() {
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(9);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
        tabs.getTabAt(2).setIcon(tabIcons[2]);
        tabs.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getSupportFragmentManager());
//        final ComplaintFragment complaintFragment = new ComplaintFragment();
        adapter.addFrag(new HomeFragment(), "Home");
        adapter.addFrag(new NewsFragment(), "News & Updates");
        adapter.addFrag(new DashBoardFragment(), "Dashboard");
        adapter.addFrag(new MyConnectionFragment(), "Connection");
        adapter.addFrag(new MyCommunicationFragment(), "Communication");
        adapter.addFrag(new CalendarFragment(), "Calendar");
        adapter.addFrag(new RewardsFragment(), "Reward");
        adapter.addFrag(new InfoDirectoryFragment(), "Info Directory");
        adapter.addFrag(new SocialBuzzFragment(), "Social Buzz");
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    changeThemeColor(position);
                }
                switch (position) {
                    case 0:
                        tv_title.setText("Home");
                        break;
                    case 1:
                        tv_title.setText("News & Updates");
                        break;
                    case 2:
                        tv_title.setText("Dashboard");
                        break;
                    case 3:
                        tv_title.setText("Connection");
                        break;
                    case 4:
                        tv_title.setText("Communication");
                        break;
                    case 5:
                        tv_title.setText("Calendar");
                        break;
                    case 6:
                        tv_title.setText("Reward");
                        break;
                    case 7:
                        tv_title.setText("Info Directory");
                        break;
                    case 8:
                        tv_title.setText("Social Buzz");
                        break;
                    default:
                        tv_title.setText("Home");
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
        spinner_profile = headerLayout.findViewById(R.id.spinner_profile);
        TextView tv_header_title = headerLayout.findViewById(R.id.tv_header_title);
        tv_header_title.setText(userProfilePOJO.getFullname());

        ImageView cv_profile_pic = headerLayout.findViewById(R.id.cv_profile_pic);

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CitizenHomeActivity.this, ProfilePageActivity.class).putExtra("user_type", "citizen"));
            }
        });

        spinner_profile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    switch (i) {
                        case 0:
                            break;
                        case 1:
                            showProfileChangeDialog(spinner_profile, "Leader");
                            break;
                        case 2:
                            showProfileChangeDialog(spinner_profile, "SubLeader");
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    public void showProfileChangeDialog(final Spinner spinner, String type) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Switch Profile");
        alertDialog.setMessage("Do you want to switch your profile to " + type + " ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                callLeaderSwitchAPI();
            }
        });

        alertDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                spinner.setSelection(0);
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void callLeaderSwitchAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "SWITCH_TO_LEADER"));
        nameValuePairs.add(new BasicNameValuePair("user_id", userProfilePOJO.getCitizenId()));
        new AdapterWebService(this, nameValuePairs, true, new MsgPassInterface() {
            @Override
            public void onMsgPassed(String response) {
                Log.d(TagUtils.getTag(), "api called:-" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("success")) {
                        String user_profile = jsonObject.optJSONObject("user_detail").optJSONObject("user_profile").toString();
                        Gson gson = new Gson();
                        UserProfilePOJO userProfilePOJO = gson.fromJson(user_profile, UserProfilePOJO.class);
                        Pref.SaveUserProfile(getApplicationContext(), userProfilePOJO);
                        Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, true);
                        if (userProfilePOJO.getFirstname().equals("") || userProfilePOJO.getMiddlename().equals("") ||
                                userProfilePOJO.getLastname().equals("") || userProfilePOJO.getFullname().equals("") ||
                                userProfilePOJO.getGender().equals("0") || userProfilePOJO.getDateOfBirth().equals("0000-00-00") ||
                                userProfilePOJO.getState().equals("")) {
                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_COMPLETED, false);
                            startActivity(new Intent(getApplicationContext(), ProfileInfoActivity.class));
                            finishAffinity();

                        } else {
                            Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_COMPLETED, true);
                            startActivity(new Intent(getApplicationContext(), CitizenHomeActivity.class));
                            finishAffinity();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).executeApi(WebServicesUrls.USER_ADMIN_PROCESS);
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

            case R.id.nav_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.nav_community:
//                startActivity(new Intent(CitizenHomeActivity.this, AddCommunication.class));
                viewPager.setCurrentItem(4);
                break;
            case R.id.nav_connection:
//                startActivity(new Intent(CitizenHomeActivity.this, FavoriteLeaderActivity.class));
                viewPager.setCurrentItem(3);
                break;
            case R.id.nav_news:
                viewPager.setCurrentItem(1);
                break;
            case R.id.nav_dashboard:
                viewPager.setCurrentItem(2);
                break;
            case R.id.nav_calendar:
                viewPager.setCurrentItem(5);
                break;
            case R.id.nav_reward:
                viewPager.setCurrentItem(6);
                break;
            case R.id.nav_info_directory:
                viewPager.setCurrentItem(7);
                break;
            case R.id.nav_social_buzz:
//                viewPager.setCurrentItem(8);
                startActivity(new Intent(CitizenHomeActivity.this, FacebookAppInvitationActivity.class));
                break;
            case R.id.nav_report:
                startActivity(new Intent(CitizenHomeActivity.this, ReportActivity.class));
                break;
            case R.id.nav_setting:
                startActivity(new Intent(CitizenHomeActivity.this, SettingActivity.class));
                break;
            case R.id.nav_help:
                startActivity(new Intent(CitizenHomeActivity.this, HelpSupportActivity.class));
                break;
            case R.id.nav_refer:
                startActivity(new Intent(CitizenHomeActivity.this, ReferActivity.class));
                break;
            case R.id.nav_logout:
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false);
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_COMPLETED, false);
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_SKIPPED, false);
                Pref.SetIntPref(getApplicationContext(), StringUtils.USER_TYPE, Constants.USER_TYPE_NONE);
                startActivity(new Intent(CitizenHomeActivity.this, SplashActivity.class));
                finishAffinity();
                break;
        }
        mDrawer.closeDrawers();
    }


    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            finish();
        } else {
            viewPager.setCurrentItem(0);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeThemeColor(int position) {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (position) {
                case 0:
                    window.setStatusBarColor(getColor(R.color.home_color_primary_dark));
                    toolbar.setBackgroundColor(getColor(R.color.home_color_primary));
                    break;
                case 1:
                    window.setStatusBarColor(getColor(R.color.news_color_primary_dark));
                    toolbar.setBackgroundColor(getColor(R.color.news_color_primary));
                    break;
                case 2:
                    window.setStatusBarColor(getColor(R.color.dashboard_color_primary_dark));
                    toolbar.setBackgroundColor(getColor(R.color.dashboard_color_primary));
                    break;
                case 3:
                    window.setStatusBarColor(getColor(R.color.connection_color_primary_dark));
                    toolbar.setBackgroundColor(getColor(R.color.connection_color_primary));
                    break;
                case 4:
                    window.setStatusBarColor(getColor(R.color.communication_color_primary_dark));
                    toolbar.setBackgroundColor(getColor(R.color.communication_color_primary));
                    break;
                case 5:
                    window.setStatusBarColor(getColor(R.color.calendar_color_primary_dark));
                    toolbar.setBackgroundColor(getColor(R.color.calendar_color_primary));
                    break;
                case 6:
                    window.setStatusBarColor(getColor(R.color.reward_color_primary_dark));
                    toolbar.setBackgroundColor(getColor(R.color.reward_color_primary));
                    break;
                case 7:
                    window.setStatusBarColor(getColor(R.color.info_color_primary_dark));
                    toolbar.setBackgroundColor(getColor(R.color.info_color_primary));
                    break;
                case 8:
                    window.setStatusBarColor(getColor(R.color.social_color_primary_dark));
                    toolbar.setBackgroundColor(getColor(R.color.social_color_primary));
                    break;
                default:
                    window.setStatusBarColor(getColor(R.color.home_color_primary_dark));
                    toolbar.setBackgroundColor(getColor(R.color.home_color_primary));
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    window.setStatusBarColor(getResources().getColor(R.color.home_color_primary_dark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.home_color_primary));
                    break;
                case 1:
                    window.setStatusBarColor(getResources().getColor(R.color.news_color_primary_dark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.news_color_primary));
                    break;
                case 2:
                    window.setStatusBarColor(getResources().getColor(R.color.dashboard_color_primary_dark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.dashboard_color_primary));
                    break;
                case 3:
                    window.setStatusBarColor(getResources().getColor(R.color.connection_color_primary_dark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.connection_color_primary));
                    break;
                case 4:
                    window.setStatusBarColor(getResources().getColor(R.color.communication_color_primary_dark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.communication_color_primary));
                    break;
                case 5:
                    window.setStatusBarColor(getResources().getColor(R.color.calendar_color_primary_dark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.calendar_color_primary));
                    break;
                case 6:
                    window.setStatusBarColor(getResources().getColor(R.color.reward_color_primary_dark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.reward_color_primary));
                    break;
                case 7:
                    window.setStatusBarColor(getResources().getColor(R.color.info_color_primary_dark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.info_color_primary));
                    break;
                case 8:
                    window.setStatusBarColor(getResources().getColor(R.color.social_color_primary_dark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.social_color_primary));
                    break;
                default:
                    window.setStatusBarColor(getResources().getColor(R.color.home_color_primary));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.home_color_primary));
                    break;
            }
        }

    }
}
