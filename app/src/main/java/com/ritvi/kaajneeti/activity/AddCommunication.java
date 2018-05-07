package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.adapter.CustomAutoCompleteAdapter;
import com.ritvi.kaajneeti.adapter.ViewPagerAdapter;
import com.ritvi.kaajneeti.fragment.adcommunication.ComplaintFragment;
import com.ritvi.kaajneeti.fragment.adcommunication.InformationFragment;
import com.ritvi.kaajneeti.fragment.adcommunication.SuggestionFragment;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.views.CustomViewPager;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCommunication extends LocalizationActivity implements WebServicesCallBack {
    private static final String CALL_ALL_LEADER = "call_all_leader";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_favorite_leader_add)
    ImageView iv_favorite_leader_add;
    @BindView(R.id.auto_fav_list)
    AutoCompleteTextView auto_fav_list;

    @BindView(R.id.rg_comm_type)
    RadioGroup rg_comm_type;
    @BindView(R.id.rb_complaint)
    RadioButton rb_complaint;
    @BindView(R.id.rv_suggestion)
    RadioButton rv_suggestion;
    @BindView(R.id.rb_information)
    RadioButton rb_information;

    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    public String leader_id = "";

    List<UserProfilePOJO> leaderPOJOS = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_communication);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Add Communication");

        iv_favorite_leader_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddCommunication.this, FavoriteLeaderActivity.class));
            }
        });

//        autoFillForm();

//        btn_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkValidation();
//            }
//        });

        auto_fav_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (leaderPOJOS.size() > 0) {
                    leader_id = leaderPOJOS.get(i).getUserProfileId();
                }
            }
        });
        setupViewPager(viewPager);
        rg_comm_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rg_comm_type.getCheckedRadioButtonId() != -1) {
                    RadioButton radioButton = findViewById(rg_comm_type.getCheckedRadioButtonId());
                    switch (radioButton.getText().toString().toLowerCase()) {
                        case "complaint":
                            viewPager.setCurrentItem(0);
                            break;
                        case "suggestion":
                            viewPager.setCurrentItem(1);
                            break;
                        case "information":
                            viewPager.setCurrentItem(2);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        rb_complaint.setChecked(true);
        viewPager.setPagingEnabled(false );
    }



    ComplaintFragment complaintFragment;
    SuggestionFragment suggestionFragment;
    InformationFragment informationFragment;

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(complaintFragment = new ComplaintFragment(), "Complaint");
        adapter.addFrag(suggestionFragment = new SuggestionFragment(), "Suggestion");
        adapter.addFrag(informationFragment = new InformationFragment(), "Information");
        viewPager.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        callLeaderAPI();
    }

    public void callLeaderAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
//        new WebServiceBase(nameValuePairs, this, this, CALL_ALL_LEADER, true).execute(WebServicesUrls.GET_MY_FAVORITE_LEADER);
        new WebServiceBaseResponseList<UserProfilePOJO>(nameValuePairs, this, new ResponseListCallback<UserProfilePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<UserProfilePOJO> responseListPOJO) {
                leaderPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    leaderPOJOS.addAll(responseListPOJO.getResultList());
                    adapter = new CustomAutoCompleteAdapter(AddCommunication.this, (ArrayList<UserProfilePOJO>) leaderPOJOS);
                    auto_fav_list.setAdapter(adapter);
                }
            }
        },UserProfilePOJO.class,"CALL_LEADER_API",true).execute(WebServicesUrls.GET_MY_FAVORITE_LEADER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(), apicall + ":-" + response);
        switch (apicall) {
            case CALL_ALL_LEADER:
                parseALLLeaderResponse(response);
                break;
        }
    }

    CustomAutoCompleteAdapter adapter = null;

    public void parseALLLeaderResponse(String response) {
        leaderPOJOS.clear();
        try {
            ResponseListPOJO<UserProfilePOJO> responsePOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<UserProfilePOJO>>() {
            }.getType());
            leaderPOJOS.clear();
            if (responsePOJO.isSuccess()) {
                leaderPOJOS.addAll(responsePOJO.getResultList());
                adapter = new CustomAutoCompleteAdapter(this, (ArrayList<UserProfilePOJO>) leaderPOJOS);
                auto_fav_list.setAdapter(adapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
