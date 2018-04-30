package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.pojo.event.EventAttachment;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.ritvi.kaajneeti.webservice.WebUploadService;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationSubmittedActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.frame_attach)
    FrameLayout frame_attach;
    @BindView(R.id.et_subject)
    EditText et_subject;
    @BindView(R.id.et_description)
    EditText et_description;

    String applicant_name="";
    String applicant_father_name="";
    String applicant_mobile="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_submitted);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            applicant_name=bundle.getString("applicant_name");
            applicant_father_name=bundle.getString("applicant_father_name");
            applicant_mobile=bundle.getString("applicant_mobile");
        }

        showAttachFragment();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInformation();
            }
        });
    }

    AttachFragment attachFragment;
    public void showAttachFragment() {
        attachFragment = new AttachFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_attach, attachFragment, "attachFragment");
        transaction.addToBackStack(null);
        transaction.commit();
//        fragmentList.add(rewardsFragment);
    }


    public void saveInformation() {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("", ""));
            reqEntity.addPart("user_profile_id", new StringBody(Constants.userProfilePOJO.getUserProfileId()));
            reqEntity.addPart("information_subject", new StringBody(et_subject.getText().toString()));
            reqEntity.addPart("information_description", new StringBody(et_description.getText().toString()));
            reqEntity.addPart("applicant_name", new StringBody(applicant_name));
            reqEntity.addPart("applicant_father_name", new StringBody(applicant_father_name));
            reqEntity.addPart("applicant_mobile", new StringBody(""));
            reqEntity.addPart("applicant_email", new StringBody(Constants.userProfilePOJO.getEmail()));

            int count = 0;

            for (EventAttachment eventAttachment : attachFragment.getEventAttachments()) {
                Log.d(TagUtils.getTag(), "attachment:-" + eventAttachment.toString());

                if (eventAttachment.getType().equals(Constants.EVENT_IMAGE_ATTACH)) {
                    reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(eventAttachment.getFile_path())));
                    reqEntity.addPart("thumb[" + (count) + "]", new StringBody(""));
                } else if (eventAttachment.getType().equals(Constants.EVENT_VIDEO_ATTACH)) {
                    reqEntity.addPart("file[" + (count) + "]", new FileBody(new File(eventAttachment.getFile_path())));
                    reqEntity.addPart("thumb[" + (count) + "]", new FileBody(new File(eventAttachment.getThumb_path())));
                }
                count++;
            }

            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    Log.d(TagUtils.getTag(), apicall + " :- " + response);
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                        if(jsonObject.optString("status").equals("success")){
                            startActivity(new Intent(InformationSubmittedActivity.this,ApplicationSubmittedActivity.class).putExtra("comp_type","information"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, "CREATE_INFORMATION", true).execute(WebServicesUrls.SAVE_INFORMATION);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
