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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.CustomAutoCompleteAdapter;
import com.ritvi.kaajneeti.adapter.TagPeopleAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
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
            peopleTagged.addAll((List<UserProfilePOJO>) bundle.getSerializable("taggedpeople"));
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

    public boolean ifAlreadyTagged(UserProfilePOJO userInfoPOJO) {
        for (UserProfilePOJO userProfilePOJO : peopleTagged) {
            String id = userInfoPOJO.getUserProfileId();

            if (id.equals(userProfilePOJO.getParentUserId())) {
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
        tagSearchPeopleAdapter = new CustomAutoCompleteAdapter(TagPeopleActivity.this, userProfilePOJOS);
        act_search.setAdapter(tagSearchPeopleAdapter);
    }


    CustomAutoCompleteAdapter tagSearchPeopleAdapter;
    ArrayList<UserProfilePOJO> userProfilePOJOS = new ArrayList<>();
    List<UserProfilePOJO> peopleTagged = new ArrayList<>();

    public void searchUser() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("search", act_search.getText().toString()));
        new WebServiceBaseResponseList<UserProfilePOJO>(nameValuePairs, this, new ResponseListCallback<UserProfilePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<UserProfilePOJO> responseListPOJO) {
                userProfilePOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    userProfilePOJOS.addAll(responseListPOJO.getResultList());
                    Log.d(TagUtils.getTag(), "user size:-" + userProfilePOJOS.size());
                    tagSearchPeopleAdapter = new CustomAutoCompleteAdapter(TagPeopleActivity.this, userProfilePOJOS);
                    act_search.setAdapter(tagSearchPeopleAdapter);
                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }
            }
        }, UserProfilePOJO.class, "CALL_SEARCH_PEOPLE_API", false).execute(WebServicesUrls.SEARCH_FOLLOWING_FOLLOWER_FRIENDS);
//        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
//            @Override
//            public void onGetMsg(String apicall, String response) {
//                try {
//                    ResponsePOJO<SearchUserResultPOJO> responsePOJO = new Gson().fromJson(response, new TypeToken<ResponsePOJO<SearchUserResultPOJO>>() {
//                    }.getType());
//
////                    tagSearchPeopleAdapter.notifyDataSetChanged();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }, "CALL_SEARCH_PEOPLE_API", false).execute(WebServicesUrls.SEARCH_USER_PROFILE);
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
