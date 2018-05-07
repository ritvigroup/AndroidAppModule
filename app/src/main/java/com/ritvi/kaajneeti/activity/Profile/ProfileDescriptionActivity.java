package com.ritvi.kaajneeti.activity.Profile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.HomeFeedAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.pojo.userdetail.ProfilePOJO;
import com.ritvi.kaajneeti.pojo.userdetail.ProfileResultPOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDescriptionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.tv_more)
    TextView tv_more;
    @BindView(R.id.tv_complaint)
    TextView tv_complaint;
    @BindView(R.id.tv_suggestion)
    TextView tv_suggestion;
    @BindView(R.id.tv_information)
    TextView tv_information;
    @BindView(R.id.rv_post)
    RecyclerView rv_post;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.ll_more_info)
    LinearLayout ll_more_info;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_mobile)
    TextView tv_mobile;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_work)
    TextView tv_work;
    @BindView(R.id.tv_education)
    TextView tv_education;
    @BindView(R.id.ll_transition)
    LinearLayout ll_transition;

    UserProfilePOJO userProfilePOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_description);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        userProfilePOJO = (UserProfilePOJO) getIntent().getSerializableExtra("userProfilePOJO");
        attachAdapter();
        if (userProfilePOJO != null) {
            tv_profile_name.setText(userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName());
            getAnalysisData(userProfilePOJO);
            callAPI();
            Glide.with(getApplicationContext())
                    .load(userProfilePOJO.getProfilePhotoPath())
                    .into(cv_profile_pic);
        }

        tv_more.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(ll_transition);
                if (ll_more_info.getVisibility() == View.VISIBLE) {
                    tv_more.setText("View more");
                    ll_more_info.setVisibility(View.GONE);
                } else {
                    tv_more.setText("View less");
                    ll_more_info.setVisibility(View.VISIBLE);
                }
            }
        });

        getAllProfileDetails();
    }

    ProfileResultPOJO profileResultPOJO;

    public void getAllProfileDetails() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBaseResponse<ProfileResultPOJO>(nameValuePairs, this, new ResponseCallBack<ProfileResultPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<ProfileResultPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        profileResultPOJO = responsePOJO.getResult();
                        setValues();
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responsePOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ProfileResultPOJO.class, "CALL_PROFILE_API", true).execute(WebServicesUrls.GET_MORE_PROFILE_DATA);
    }

    public void setValues() {
        if (profileResultPOJO != null && profileResultPOJO.getProfilePOJO() != null) {
            ProfilePOJO profilePOJO = profileResultPOJO.getProfilePOJO();
            tv_profile_name.setText(profilePOJO.getFirstName() + " " + profilePOJO.getLastName());
            tv_email.setText(profilePOJO.getEmail());
            tv_mobile.setText(profilePOJO.getMobile());
            String address = profilePOJO.getCity() + " " + profilePOJO.getState() + " " + profilePOJO.getCountry();
            tv_address.setText(address);
            String work = "";
            if (profileResultPOJO.getWorkPOJOList().size() > 0) {
                work = "Worked at " + profileResultPOJO.getWorkPOJOList().get(0).getWorkPosition();
                tv_work.setText(work);
            }

            String education = "";
            if (profileResultPOJO.getEducationPOJOS().size() > 0) {
                education = "Studied " + profileResultPOJO.getEducationPOJOS().get(0).getQualification() + " at " + profileResultPOJO.getEducationPOJOS().get(0).getQualificationUniversity();
                tv_education.setText(education);
            }
        }
    }


    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));

        new WebServiceBaseResponseList<FeedPOJO>(nameValuePairs, this, new ResponseListCallback<FeedPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO responseListPOJO) {
                feedPOJOS.clear();
                try {
                    if (responseListPOJO.isSuccess()) {
                        feedPOJOS.addAll(responseListPOJO.getResultList());
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                homeFeedAdapter.notifyDataSetChanged();
            }
        }, FeedPOJO.class, "call_complaint_list_api", true).execute(WebServicesUrls.ALL_POST);
    }


    HomeFeedAdapter homeFeedAdapter;
    List<FeedPOJO> feedPOJOS = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_post.setLayoutManager(linearLayoutManager);
        homeFeedAdapter = new HomeFeedAdapter(rv_post,this, null, feedPOJOS,getSupportFragmentManager());
        rv_post.setHasFixedSize(true);
        rv_post.setAdapter(homeFeedAdapter);
        rv_post.setItemAnimator(new DefaultItemAnimator());

    }


    public void getAnalysisData(UserProfilePOJO userProfilePOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        JSONObject resultJsonObject = jsonObject.optJSONObject("result");
                        String TotalEvent = resultJsonObject.optString("TotalEvent");
                        String TotalPoll = resultJsonObject.optString("TotalPoll");
                        String TotalPost = resultJsonObject.optString("TotalPost");
                        String TotalSuggestion = resultJsonObject.optString("TotalSuggestion");
                        String TotalInformation = resultJsonObject.optString("TotalInformation");
                        String TotalComplaint = resultJsonObject.optString("TotalComplaint");

                        tv_complaint.setText(TotalComplaint);
                        tv_information.setText(TotalInformation);
                        tv_suggestion.setText(TotalSuggestion);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_ALL_DATA", false).execute(WebServicesUrls.ALL_SUMMARY_DATA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (userProfilePOJO.getUserId().equals(Constants.userProfilePOJO.getUserId())) {
            getMenuInflater().inflate(R.menu.menu_edit, menu);//Menu Resource, Menu
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                startActivity(new Intent(ProfileDescriptionActivity.this, ProfileEditPageActivity.class).putExtra("userProfilePOJO", userProfilePOJO));
                return true;
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}
