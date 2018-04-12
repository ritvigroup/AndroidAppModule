package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.adapter.CustomAutoCompleteAdapter;
import com.ritvi.kaajneeti.fragment.adcommunication.SuggestionFragment;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateSuggestionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_favorite_leader_add)
    ImageView iv_favorite_leader_add;
    @BindView(R.id.auto_fav_list)
    AutoCompleteTextView auto_fav_list;
    List<UserInfoPOJO> leaderPOJOS = new ArrayList<>();
    public String leader_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_suggestion);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        showSuggestionFragment();

        iv_favorite_leader_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateSuggestionActivity.this, FavoriteLeaderActivity.class));
            }
        });

        auto_fav_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (leaderPOJOS.size() > 0) {
                    leader_id = leaderPOJOS.get(i).getUserProfileLeader().getUserProfileId();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        callLeaderAPI();
    }
    CustomAutoCompleteAdapter adapter = null;
    public void callLeaderAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userInfoPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
//        new WebServiceBase(nameValuePairs, this, this, CALL_ALL_LEADER, true).execute(WebServicesUrls.GET_MY_FAVORITE_LEADER);
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    ResponseListPOJO<UserInfoPOJO> responseListPOJO = (ResponseListPOJO<UserInfoPOJO>) new Gson().fromJson(response, new TypeToken<ResponseListPOJO<UserInfoPOJO>>() {
                    }.getType());
                    leaderPOJOS.clear();
                    if (responseListPOJO.isSuccess()) {
                        leaderPOJOS.addAll(responseListPOJO.getResultList());
                        adapter = new CustomAutoCompleteAdapter(CreateSuggestionActivity.this, (ArrayList<UserInfoPOJO>) leaderPOJOS);
                        auto_fav_list.setAdapter(adapter);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "CALL_LEADER_API", true).execute(WebServicesUrls.GET_MY_FAVORITE_LEADER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSuggestionFragment() {
        SuggestionFragment suggestionFragment = new SuggestionFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, suggestionFragment, "suggestionFragment");
        transaction.addToBackStack(null);
        transaction.commit();
//        fragmentList.add(rewardsFragment);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
