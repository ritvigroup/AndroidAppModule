package com.ritvi.kaajneeti.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.gson.Gson;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.Pref;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.adapter.ViewPagerWithTitleAdapter;
import com.ritvi.kaajneeti.fragment.HomeFragment;
import com.ritvi.kaajneeti.pojo.user.ProfileRolePOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.AdapterWebService;
import com.ritvi.kaajneeti.webservice.MsgPassInterface;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderHomeActivity extends LocalizationActivity {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    private Toolbar toolbar;
    @BindView(R.id.ic_ham)
    ImageView ic_ham;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tv_title)
    TextView tv_title;

    Spinner spinner_profile;
    UserProfilePOJO userProfilePOJO;

    private int[] tabIcons = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    public ProfileRolePOJO leaderProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_home);
        ButterKnife.bind(this);

        userProfilePOJO = Pref.GetUserProfile(getApplicationContext());
        leaderProfile=Pref.getProfileRolePOJO(Pref.GetStringPref(getApplicationContext(), StringUtils.L_PROFILE_DETAIL,""));
        settingNavDrawer();
        setUpTabswithViewPager();
        setupTabIcons();
    }

    public void setUpTabswithViewPager() {
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
        tabs.getTabAt(2).setIcon(tabIcons[2]);
        tabs.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
//        final ComplaintFragment complaintFragment = new ComplaintFragment();
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), getResources().getString(R.string.hs_tab_home));
//        adapter.addFrag(complaintFragment, getResources().getString(R.string.hs_tab_complain));
        adapter.addFrag(new HomeFragment(), getResources().getString(R.string.hs_tab_campaign));
        adapter.addFrag(new HomeFragment(), getResources().getString(R.string.hs_tab_calendar));
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv_title.setText("Home");
                        break;
                    case 1:
                        tv_title.setText("Complain");
//                        complaintFragment.callLeaderComplaintAPI();

                        break;
                    case 2:
                        tv_title.setText("Compaign");
                        break;
                    case 3:
                        tv_title.setText("Calendar");
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

        ImageView cv_profile_pic=headerLayout.findViewById(R.id.cv_profile_pic);

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaderHomeActivity.this,ProfileInfoActivity.class).putExtra("user_type","leader"));
            }
        });

        spinner_profile.setSelection(1);
        spinner_profile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 1) {
                    switch (i) {
                        case 0:
                            showProfileChangeDialog(spinner_profile, "Citizen");
                            break;
                        case 1:

                            break;
                        case 2:

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
                callCitizenSwitchAPI();
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

    private void callCitizenSwitchAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "SWITCH_TO_CITIZEN"));
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
                        Pref.SetIntPref(getApplicationContext(), StringUtils.USER_TYPE, Constants.USER_TYPE_CITIZEN);
                        startActivity(new Intent(LeaderHomeActivity.this, CitizenHomeActivity.class));
                        finishAffinity();
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
            case R.id.nav_community:
                startActivity(new Intent(LeaderHomeActivity.this, AddCommunication.class));
                break;
            case R.id.nav_connection:
                startActivity(new Intent(LeaderHomeActivity.this, FavoriteLeaderActivity.class));
                break;
            case R.id.nav_logout:
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false);
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_COMPLETED, false);
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_SKIPPED, false);
                Pref.SetIntPref(getApplicationContext(), StringUtils.USER_TYPE, Constants.USER_TYPE_NONE);
                startActivity(new Intent(LeaderHomeActivity.this, SplashActivity.class));
                finishAffinity();
                break;
        }
        mDrawer.closeDrawers();
    }
}
