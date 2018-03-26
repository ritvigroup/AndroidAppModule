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
import com.ritvi.kaajneeti.pojo.user.favorite.FavoriteResultPOJO;
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

    String leader_id = "";

    List<FavoriteResultPOJO> leaderPOJOS = new ArrayList<>();

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
                    leader_id = leaderPOJOS.get(i).getUserProfileDetailPOJO().getUserProfileLeaderPOJO().getUserProfileId();
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

//    public void checkValidation() {
//        if (et_name.getText().toString().length() > 0 && et_father_name.getText().toString().length() > 0 &&
//                et_mobile_number.getText().toString().length() > 0 && auto_fav_list.getText().toString().length() > 0 &&
//                leader_id.length() > 0) {
//            CommunicationSubmittionPOJO communicationSubmittionPOJO = new CommunicationSubmittionPOJO();
//            communicationSubmittionPOJO.setUser_id(Pref.GetUserProfile(getApplicationContext()).getCitizenId());
//            communicationSubmittionPOJO.setC_name(et_name.getText().toString());
//            communicationSubmittionPOJO.setC_gender(((RadioButton) findViewById(rg_gender.getCheckedRadioButtonId())).getText().toString());
//            communicationSubmittionPOJO.setSelf_other_group("1");
//            communicationSubmittionPOJO.setC_father_name(et_father_name.getText().toString());
//            communicationSubmittionPOJO.setC_mobile(et_mobile_number.getText().toString());
//            communicationSubmittionPOJO.setC_email(et_email.getText().toString());
//            communicationSubmittionPOJO.setC_aadhaar_number(et_aadhar.getText().toString());
//            communicationSubmittionPOJO.setLeader_id(leader_id);
//            Intent intent = new Intent(AddCommunication.this, AddCommunicationAddressActivity.class);
//            intent.putExtra("complaintPOJO", communicationSubmittionPOJO);
//            startActivity(intent);
//        } else {
//            ToastClass.showShortToast(getApplicationContext(), "Please Enter Mandatory Fields");
//        }
//    }
//
//    public void autoFillForm() {
//        UserProfilePOJO userProfilePOJO = Pref.GetUserProfile(getApplicationContext());
//        setField(userProfilePOJO.getFullname(), et_name);
//        setField(userProfilePOJO.getMobile(), et_mobile_number);
//        setField(userProfilePOJO.getEmail(), et_email);
//
//        String gender = userProfilePOJO.getGender();
//        if (gender != null && gender.length() > 0) {
//            switch (gender.toLowerCase().toString()) {
//                case "male":
//                    rb_male.setChecked(true);
//                    break;
//                case "female":
//                    rb_female.setChecked(true);
//                    break;
//                case "other":
//                    rb_other.setChecked(true);
//                    break;
//            }
//        }
//    }
//
//    public void setField(String text, EditText editText) {
//        if (text != null && text.length() > 0) {
//            editText.setText(text);
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        callLeaderAPI();
    }

    public void callLeaderAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePojo.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePojo.getCitizenProfilePOJO().getUserProfileId()));
//        new WebServiceBase(nameValuePairs, this, this, CALL_ALL_LEADER, true).execute(WebServicesUrls.GET_MY_FAVORITE_LEADER);
        new WebServiceBaseResponseList<FavoriteResultPOJO>(nameValuePairs, this, new ResponseListCallback<FavoriteResultPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<FavoriteResultPOJO> responseListPOJO) {
                leaderPOJOS.clear();
                if (responseListPOJO.getStatus().equals("success")) {
                    leaderPOJOS.addAll(responseListPOJO.getResultList());
                    adapter = new CustomAutoCompleteAdapter(AddCommunication.this, (ArrayList<FavoriteResultPOJO>) leaderPOJOS);
                    auto_fav_list.setAdapter(adapter);
                }
            }
        },FavoriteResultPOJO.class,"CALL_LEADER_API",true).execute(WebServicesUrls.GET_MY_FAVORITE_LEADER);
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
            ResponseListPOJO<FavoriteResultPOJO> responsePOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<FavoriteResultPOJO>>() {
            }.getType());
            leaderPOJOS.clear();
            if (responsePOJO.getStatus().equals("success")) {
                leaderPOJOS.addAll(responsePOJO.getResultList());
                adapter = new CustomAutoCompleteAdapter(this, (ArrayList<FavoriteResultPOJO>) leaderPOJOS);
                auto_fav_list.setAdapter(adapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
