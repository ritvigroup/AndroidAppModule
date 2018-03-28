package com.ritvi.kaajneeti.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.TagPeopleAdapter;
import com.ritvi.kaajneeti.adapter.TagSearchPeopleAdapter;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.user.SearchUserResultPOJO;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagPeopleActivity extends AppCompatActivity {

    @BindView(R.id.act_search)
    AutoCompleteTextView act_search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_search_close)
    ImageView iv_search_close;
    @BindView(R.id.rv_tag_people)
    RecyclerView rv_tag_people;

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
            peopleTagged.addAll((List<UserInfoPOJO>) bundle.getSerializable("taggedpeople"));
        }

        act_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (act_search.getText().toString().length() > 0) {
                    iv_search_close.setVisibility(View.VISIBLE);
                    searchUser();
                } else {
                    iv_search_close.setVisibility(View.GONE);
                }
            }
        });

        iv_search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_search.setText("");
            }
        });
        attachactAdapter();
        attachAdapter();
        act_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!ifAlreadyTagged(userProfilePOJOS.get(i))) {
                    peopleTagged.add(userProfilePOJOS.get(i));
                    tagPeopleAdapter.notifyDataSetChanged();
                    act_search.setText("");
                }
            }
        });
    }

    public boolean ifAlreadyTagged(UserInfoPOJO userInfoPOJO) {
        for (UserInfoPOJO userProfilePOJO : peopleTagged) {
            String id="";
            if(userInfoPOJO.getUserProfileCitizen()!=null){
                id=userInfoPOJO.getUserProfileCitizen().getUserProfileId();
            }else{
                id=userInfoPOJO.getUserProfileLeader().getUserProfileId();
            }

            if (userProfilePOJO.getUserProfileLeader()!=null&&id.equals(userProfilePOJO.getUserProfileLeader().getParentUserId())) {
                return true;
            }
            if (userProfilePOJO.getUserProfileCitizen()!=null&&id.equals(userProfilePOJO.getUserProfileCitizen().getParentUserId())) {
                return true;
            }
        }
        return false;
    }

    TagPeopleAdapter tagPeopleAdapter;

    public void attachAdapter() {
        tagPeopleAdapter = new TagPeopleAdapter(this, null, peopleTagged);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_tag_people.setHasFixedSize(true);
        rv_tag_people.setAdapter(tagPeopleAdapter);
        rv_tag_people.setLayoutManager(linearLayoutManager);
        rv_tag_people.setItemAnimator(new DefaultItemAnimator());

    }

    public void attachactAdapter() {
        tagSearchPeopleAdapter = new TagSearchPeopleAdapter(TagPeopleActivity.this, userProfilePOJOS);
        act_search.setAdapter(tagSearchPeopleAdapter);
    }


    TagSearchPeopleAdapter tagSearchPeopleAdapter;
    ArrayList<UserInfoPOJO> userProfilePOJOS = new ArrayList<>();
    List<UserInfoPOJO> peopleTagged = new ArrayList<>();

    public void searchUser() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("search", act_search.getText().toString()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    ResponsePOJO<SearchUserResultPOJO> responsePOJO = new Gson().fromJson(response, new TypeToken<ResponsePOJO<SearchUserResultPOJO>>() {
                    }.getType());
                    userProfilePOJOS.clear();
                    if (responsePOJO.isSuccess()) {
                        userProfilePOJOS.addAll(responsePOJO.getResult().getCitizenUserInfoPOJOS());
                        userProfilePOJOS.addAll(responsePOJO.getResult().getLeaderUserInfoPOJOS());

                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responsePOJO.getMessage());
                    }
                    tagSearchPeopleAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "CALL_SEARCH_PEOPLE_API", false).execute(WebServicesUrls.SEARCH_USER_PROFILE);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("taggedpeople", (Serializable) peopleTagged);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
