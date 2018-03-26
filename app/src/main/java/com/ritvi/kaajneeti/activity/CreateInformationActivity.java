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
import com.ritvi.kaajneeti.fragment.adcommunication.InformationFragment;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.user.favorite.FavoriteResultPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateInformationActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_favorite_leader_add)
    ImageView iv_favorite_leader_add;
    @BindView(R.id.auto_fav_list)
    AutoCompleteTextView auto_fav_list;
    List<FavoriteResultPOJO> leaderPOJOS = new ArrayList<>();
    public String leader_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_information);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        showInformationFragment();
        iv_favorite_leader_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateInformationActivity.this, FavoriteLeaderActivity.class));
            }
        });

        auto_fav_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (leaderPOJOS.size() > 0) {
                    leader_id = leaderPOJOS.get(i).getUserProfileDetailPOJO().getUserProfileLeaderPOJO().getUserProfileId();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        callLeaderAPI();
    }

    CustomAutoCompleteAdapter adapter = null;
    public void callLeaderAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePojo.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePojo.getCitizenProfilePOJO().getUserProfileId()));
//        new WebServiceBase(nameValuePairs, this, this, CALL_ALL_LEADER, true).execute(WebServicesUrls.GET_MY_FAVORITE_LEADER);
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    ResponseListPOJO<FavoriteResultPOJO> responseListPOJO = (ResponseListPOJO<FavoriteResultPOJO>) new Gson().fromJson(response, new TypeToken<ResponseListPOJO<FavoriteResultPOJO>>() {
                    }.getType());
                    leaderPOJOS.clear();
                    if (responseListPOJO.getStatus().equals("success")) {
                        leaderPOJOS.addAll(responseListPOJO.getResultList());
                        adapter = new CustomAutoCompleteAdapter(CreateInformationActivity.this, (ArrayList<FavoriteResultPOJO>) leaderPOJOS);
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

    public void showInformationFragment() {
        InformationFragment informationFragment = new InformationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, informationFragment, "informationFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
