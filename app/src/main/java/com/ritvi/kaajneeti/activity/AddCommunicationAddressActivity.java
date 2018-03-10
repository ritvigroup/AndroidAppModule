package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.adapter.ViewPagerAdapter;
import com.ritvi.kaajneeti.fragment.addcommunicationaddress.RuralAddressFragment;
import com.ritvi.kaajneeti.fragment.addcommunicationaddress.UrbanAddressFragment;
import com.ritvi.kaajneeti.pojo.communication.CommunicationSubmittionPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCommunicationAddressActivity extends LocalizationActivity implements WebServicesCallBack {

    private static final String CALL_FORM_SUBMITTION = "call_form_submittion";
    @BindView(R.id.addressViewPager)
    ViewPager addressViewPager;
    @BindView(R.id.rg_address)
    RadioGroup rg_address;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    CommunicationSubmittionPOJO communicationSubmittionPOJO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_communication_address);
        ButterKnife.bind(this);

        communicationSubmittionPOJO = (CommunicationSubmittionPOJO) getIntent().getSerializableExtra("complaintPOJO");

        setupViewPager(addressViewPager);
        rg_address.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_urban) {
                    addressViewPager.setCurrentItem(1);
                } else {
                    addressViewPager.setCurrentItem(0);
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (addressViewPager.getCurrentItem() == 0) {
//                    checkValidation(ruralAddressFragment.checkValidations());
//                } else {
//                    checkValidation(urbanAddressFragment.checkValidations());
//                }
                startActivity(new Intent(AddCommunicationAddressActivity.this,ApplicationSubmittedActivity.class).putExtra("comp_type","complaint"));
            }
        });

    }

    public void checkValidation(String... fields) {
        if (addressViewPager.getCurrentItem() == 0) {
            communicationSubmittionPOJO.setC_district(fields[0]);
            communicationSubmittionPOJO.setC_tehsil(fields[1]);
            communicationSubmittionPOJO.setC_thana(fields[2]);
            communicationSubmittionPOJO.setC_block(fields[3]);
            communicationSubmittionPOJO.setC_village_panchayat(fields[4]);
            communicationSubmittionPOJO.setC_village(fields[5]);
            communicationSubmittionPOJO.setC_department(fields[6]);
            communicationSubmittionPOJO.setC_subject(fields[7]);
            communicationSubmittionPOJO.setC_description(fields[8]);
        } else {
            communicationSubmittionPOJO.setC_district(fields[0]);
            communicationSubmittionPOJO.setC_tehsil(fields[1]);
            communicationSubmittionPOJO.setC_thana(fields[2]);
            communicationSubmittionPOJO.setC_town_area(fields[3]);
            communicationSubmittionPOJO.setC_ward(fields[4]);
            communicationSubmittionPOJO.setC_full_address(fields[5]);
            communicationSubmittionPOJO.setC_department(fields[6]);
            communicationSubmittionPOJO.setC_subject(fields[7]);
            communicationSubmittionPOJO.setC_description(fields[8]);
        }

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "SAVE_COMPLAINT"));
        nameValuePairs.add(new BasicNameValuePair("c_profile_id", communicationSubmittionPOJO.getUser_id()));
        nameValuePairs.add(new BasicNameValuePair("l_profile_id", communicationSubmittionPOJO.getLeader_id()));
        nameValuePairs.add(new BasicNameValuePair("self_other_group", communicationSubmittionPOJO.getSelf_other_group()));
        nameValuePairs.add(new BasicNameValuePair("c_name", communicationSubmittionPOJO.getC_name()));
        nameValuePairs.add(new BasicNameValuePair("c_father_name", communicationSubmittionPOJO.getC_father_name()));
        nameValuePairs.add(new BasicNameValuePair("c_gender", communicationSubmittionPOJO.getC_gender()));
        nameValuePairs.add(new BasicNameValuePair("c_mobile", communicationSubmittionPOJO.getC_mobile()));
        nameValuePairs.add(new BasicNameValuePair("c_email", communicationSubmittionPOJO.getC_email()));
        nameValuePairs.add(new BasicNameValuePair("c_aadhaar_number", communicationSubmittionPOJO.getC_aadhaar_number()));
        nameValuePairs.add(new BasicNameValuePair("c_district", communicationSubmittionPOJO.getC_district()));
        nameValuePairs.add(new BasicNameValuePair("c_tehsil", communicationSubmittionPOJO.getC_tehsil()));
        nameValuePairs.add(new BasicNameValuePair("c_thana", communicationSubmittionPOJO.getC_thana()));
        nameValuePairs.add(new BasicNameValuePair("c_block", communicationSubmittionPOJO.getC_block()));
        nameValuePairs.add(new BasicNameValuePair("c_village_panchayat", communicationSubmittionPOJO.getC_village_panchayat()));
        nameValuePairs.add(new BasicNameValuePair("c_village", communicationSubmittionPOJO.getC_village()));
        nameValuePairs.add(new BasicNameValuePair("c_town_area", communicationSubmittionPOJO.getC_town_area()));
        nameValuePairs.add(new BasicNameValuePair("c_ward", communicationSubmittionPOJO.getC_ward()));
        nameValuePairs.add(new BasicNameValuePair("c_full_address", communicationSubmittionPOJO.getC_full_address()));
        nameValuePairs.add(new BasicNameValuePair("c_department", communicationSubmittionPOJO.getC_department()));
        nameValuePairs.add(new BasicNameValuePair("c_subject", communicationSubmittionPOJO.getC_subject()));
        nameValuePairs.add(new BasicNameValuePair("c_description", communicationSubmittionPOJO.getC_description()));
        new WebServiceBase(nameValuePairs, this, this, CALL_FORM_SUBMITTION, true).execute(WebServicesUrls.COMPLAINT);
    }

    RuralAddressFragment ruralAddressFragment;
    UrbanAddressFragment urbanAddressFragment;

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(ruralAddressFragment = new RuralAddressFragment(), "Rural");
        adapter.addFrag(urbanAddressFragment = new UrbanAddressFragment(), "Urban");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(), apicall + ":-" + response);
        switch (apicall) {
            case CALL_FORM_SUBMITTION:
                parseComplaintResponse(response);
                break;
        }
    }

    public void parseComplaintResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("status").equals("success")){
                startActivity(new Intent(AddCommunicationAddressActivity.this, ApplicationSubmittedActivity.class).putExtra("complaint_id",jsonObject.optString("complaint_id")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
