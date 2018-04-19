package com.ritvi.kaajneeti.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.jmpergar.awesometext.AwesomeTextHandler;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.communication.FeelingPOJO;
import com.ritvi.kaajneeti.pojo.event.EventAttachment;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.testing.HashtagsSpanRenderer;
import com.ritvi.kaajneeti.testing.MentionSpanRenderer;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
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
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreatePostActivity extends AppCompatActivity {

    private static final String HASHTAG_PATTERN = "(#[\\p{L}0-9-_]+)";
    private static final String MENTION_PATTERN = "(@[\\p{L}0-9-_]+)";
    private static final int TAG_PEOPLE = 105;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;
    private int PICK_IMAGE_REQUEST = 102;
    private static final int CAMERA_REQUEST = 103;

    @BindView(R.id.btn_check_in)
    Button btn_check_in;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_profile_description)
    TextView tv_profile_description;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.spinner_feeling)
    Spinner spinner_feeling;
    @BindView(R.id.tv_tags)
    TextView tv_tags;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_description)
    EditText et_description;
    @BindView(R.id.spinner_public)
    Spinner spinner_public;

    String tag_people="";
    String check_in_place="";
    AwesomeTextHandler awesomeTextViewHandler;
    List<UserInfoPOJO> taggedUserProfilePOJOS=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        showAttachFragment();

        btn_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPlace();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
            }
        });
        getAllFeelings();

        awesomeTextViewHandler = new AwesomeTextHandler();
        awesomeTextViewHandler
                .addViewSpanRenderer(HASHTAG_PATTERN, new HashtagsSpanRenderer())
                .addViewSpanRenderer(MENTION_PATTERN, new MentionSpanRenderer())
                .setView(tv_tags);


        tv_tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(CreatePostActivity.this,TagPeopleActivity.class).putExtra("taggedpeople", (Serializable) taggedUserProfilePOJOS),TAG_PEOPLE);
            }
        });
    }



    public void savePost() {
        try {

            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            String activity="";
            if(spinner_feeling.getSelectedItemPosition()!=0){
                activity=feelingPOJOArrayList.get(spinner_feeling.getSelectedItemPosition()-1).getFeelingId();
            }

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("", ""));
            reqEntity.addPart("user_profile_id", new StringBody(Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
            reqEntity.addPart("title", new StringBody(et_title.getText().toString()));
            reqEntity.addPart("location", new StringBody(check_in_place));
            reqEntity.addPart("description", new StringBody(et_description.getText().toString()));
            reqEntity.addPart("url", new StringBody(""));
            reqEntity.addPart("feeling", new StringBody(activity));

            for(int i=0;i<taggedUserProfilePOJOS.size();i++) {
                if(taggedUserProfilePOJOS.get(i).getUserProfileCitizen()!=null){
                    reqEntity.addPart("post_tag["+i+"]", new StringBody(taggedUserProfilePOJOS.get(i).getUserProfileCitizen().getUserProfileId()));
                }else{
                    reqEntity.addPart("post_tag["+i+"]", new StringBody(taggedUserProfilePOJOS.get(i).getUserProfileLeader().getUserProfileId()));
                }
            }

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
//                            startActivity(new Intent(CreatePostActivity.this,ApplicationSubmittedActivity.class).putExtra("comp_type","information"));
                            ToastClass.showShortToast(getApplicationContext(),"Posted Successfully");
                            startActivity(new Intent(CreatePostActivity.this,HomeActivity.class));
                            finishAffinity();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, "CREATE_POST", true).execute(WebServicesUrls.SAVE_POST);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    public void getAllFeelings(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        new WebServiceBaseResponseList<FeelingPOJO>(nameValuePairs, this, new ResponseListCallback<FeelingPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<FeelingPOJO> responseListPOJO) {
                feelingPOJOArrayList.clear();
                if (responseListPOJO.isSuccess()) {
                    feelingPOJOArrayList.addAll(responseListPOJO.getResultList());

                    List<String> feelingStringList = new ArrayList<>();
                    feelingStringList.add("Select Activity");
                    for (FeelingPOJO feelingPOJO : feelingPOJOArrayList) {
                        feelingStringList.add(feelingPOJO.getFeelingName());
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, feelingStringList);
                    spinner_feeling.setAdapter(spinnerArrayAdapter);

                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }
            }
        },FeelingPOJO.class,"CALL_GET_ALL_BRANCH", true).execute(WebServicesUrls.GET_FEELINGS);

    }


    List<FeelingPOJO> feelingPOJOArrayList= new ArrayList<>();

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

    public void findPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkProfileDescription(){
        String description="<b> - is at "+check_in_place;
        tv_profile_description.setText(Html.fromHtml(description));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TagUtils.getTag(), "Place: " + place.getName());
                check_in_place= (String) place.getName();
                checkProfileDescription();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TagUtils.getTag(), status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }else if (requestCode == TAG_PEOPLE) {
            if(resultCode == Activity.RESULT_OK){
                taggedUserProfilePOJOS = (List<UserInfoPOJO>) data.getSerializableExtra("taggedpeople");
                if(taggedUserProfilePOJOS!=null){
                    awesomeTextViewHandler.setText(getStaggeredText());
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }else{
            attachFragment.onActivityResult(requestCode,resultCode,data);
            Log.d(TagUtils.getTag(),"on activity result:-"+requestCode);
        }
    }

    public String getStaggeredText(){
        String tagged_people="";
        for(UserInfoPOJO userInfoPOJO:taggedUserProfilePOJOS){
            UserProfilePOJO userProfilePOJO=null;
            if(userInfoPOJO.getUserProfileCitizen()!=null){
                userProfilePOJO=userInfoPOJO.getUserProfileCitizen();
            }
            if(userInfoPOJO.getUserProfileLeader()!=null){
                userProfilePOJO=userInfoPOJO.getUserProfileLeader();
            }

            if(userProfilePOJO!=null){
                if(userProfilePOJO.getFirstName()!=null||userProfilePOJO.getFirstName().equals("")
                        ||userProfilePOJO.getLastName()!=null||userProfilePOJO.getLastName().equals("")){
                    tagged_people+=" #"+userProfilePOJO.getFirstName()+""+userProfilePOJO.getLastName();
                }else{
                    tagged_people+=" #"+userInfoPOJO.getUserName();
                }
            }

        }
        return tagged_people;
    }

}
