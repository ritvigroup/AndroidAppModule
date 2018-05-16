package com.ritvi.kaajneeti.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.AlreadyTaggedPeopleAdapter;
import com.ritvi.kaajneeti.adapter.TagPeopleAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.testing.PayUMoneyIntegration;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagPeopleActivity extends AppCompatActivity {

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_search_close)
    ImageView iv_search_close;
    @BindView(R.id.tv_done)
    TextView tv_done;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_tag_people)
    RecyclerView rv_tag_people;
    @BindView(R.id.rv_tagged_people)
    RecyclerView rv_tagged_people;
    List<UserProfilePOJO> taggedUserProfilePOJOS=new ArrayList<>();
    List<UserProfilePOJO> searchUserProfilePOJOS=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_people);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            taggedUserProfilePOJOS.addAll((List<UserProfilePOJO>) bundle.getSerializable("taggedpeople"));
        }

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

                searchUser();
            }
        });

        iv_search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        attachAlreadyTaggedAdapter();
        attachAdapter();
        searchUser();
    }

    public void removeUser(UserProfilePOJO userProfilePOJO) {
        int position=-1;
        for(int i=0;i<taggedUserProfilePOJOS.size();i++){
            UserProfilePOJO userProfilePOJO1=taggedUserProfilePOJOS.get(i);
            if(userProfilePOJO1.getUserProfileId().equalsIgnoreCase(userProfilePOJO.getUserProfileId())){
                position=i;
            }
        }
        if(position!=-1){
            taggedUserProfilePOJOS.remove(position);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alreadyTaggedPeopleAdapter.notifyDataSetChanged();
                    userProfileAdapter.notifyDataSetChanged();
                }
            },500);

        }
    }
    public void addUser(UserProfilePOJO userProfilePOJO) {
        boolean is_present=false;
        for(int i=0;i<taggedUserProfilePOJOS.size();i++){
            UserProfilePOJO userProfilePOJO1=taggedUserProfilePOJOS.get(i);
            if(userProfilePOJO1.getUserProfileId().equalsIgnoreCase(userProfilePOJO.getUserProfileId())){
                is_present=true;
            }
        }
        if(!is_present){
            taggedUserProfilePOJOS.add(userProfilePOJO);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alreadyTaggedPeopleAdapter.notifyDataSetChanged();
                    userProfileAdapter.notifyDataSetChanged();
                }
            },500);
        }
        Log.d(TagUtils.getTag(),"already tagged people:-"+taggedUserProfilePOJOS.size());
    }
    AlreadyTaggedPeopleAdapter alreadyTaggedPeopleAdapter;
    public void attachAlreadyTaggedAdapter() {
        alreadyTaggedPeopleAdapter = new AlreadyTaggedPeopleAdapter(this, null, taggedUserProfilePOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_tagged_people.setHasFixedSize(true);
        rv_tagged_people.setAdapter(alreadyTaggedPeopleAdapter);
        rv_tagged_people.setLayoutManager(linearLayoutManager);
        rv_tagged_people.setItemAnimator(new DefaultItemAnimator());
    }

    public boolean ifAlreadyTagged(UserProfilePOJO userInfoPOJO) {
        for (UserProfilePOJO userProfilePOJO : taggedUserProfilePOJOS) {
            String id = userInfoPOJO.getUserProfileId();

            if (id.equals(userProfilePOJO.getParentUserId())) {
                return true;
            }

        }
        return false;
    }

    TagPeopleAdapter userProfileAdapter;

    public void attachAdapter() {
        userProfileAdapter = new TagPeopleAdapter(this, null, searchUserProfilePOJOS,taggedUserProfilePOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_tag_people.setHasFixedSize(true);
        rv_tag_people.setAdapter(userProfileAdapter);
        rv_tag_people.setLayoutManager(linearLayoutManager);
        rv_tag_people.setItemAnimator(new DefaultItemAnimator());
    }

    public void searchUser() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("search", et_search.getText().toString()));
        new WebServiceBaseResponseList<UserProfilePOJO>(nameValuePairs, this, new ResponseListCallback<UserProfilePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<UserProfilePOJO> responseListPOJO) {
                searchUserProfilePOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    searchUserProfilePOJOS.addAll(responseListPOJO.getResultList());
                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }
                userProfileAdapter.notifyDataSetChanged();
            }
        }, UserProfilePOJO.class, "CALL_SEARCH_PEOPLE_API", false).execute(WebServicesUrls.SEARCH_FOLLOWING_FOLLOWER_FRIENDS);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("taggedpeople", (Serializable) taggedUserProfilePOJOS);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
