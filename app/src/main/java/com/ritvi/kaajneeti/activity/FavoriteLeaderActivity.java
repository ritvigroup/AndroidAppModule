package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.FavoriteLeaderAdapter;
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

public class FavoriteLeaderActivity extends LocalizationActivity implements WebServicesCallBack {

    private static final String CALL_ALL_LEADER = "call_all_leader";
    @BindView(R.id.rv_leader)
    RecyclerView rv_leader;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    List<FavoriteResultPOJO> leaderPOJOS = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_leader);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Favourite Leader");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        attachAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        callLeaderAPI();
    }

    FavoriteLeaderAdapter leaderAdapter;

    public void attachAdapter() {
        leaderAdapter = new FavoriteLeaderAdapter(this, null, leaderPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_leader.setHasFixedSize(true);
        rv_leader.setAdapter(leaderAdapter);
        rv_leader.setLayoutManager(linearLayoutManager);
        rv_leader.setItemAnimator(new DefaultItemAnimator());

    }

    public void callLeaderAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePojo.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePojo.getCitizenProfilePOJO().getUserProfileId()));
        new WebServiceBase(nameValuePairs, this, this, CALL_ALL_LEADER, true).execute(WebServicesUrls.GET_MY_FAVORITE_LEADER);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_favorite_leader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_all:
                startActivity(new Intent(FavoriteLeaderActivity.this, AllLeaderActivity.class));
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


    public void parseALLLeaderResponse(String response) {
        leaderPOJOS.clear();
        try {
            ResponseListPOJO<FavoriteResultPOJO> responsePOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<FavoriteResultPOJO>>() {
            }.getType());
            leaderPOJOS.clear();
            if (responsePOJO.getStatus().equals("success")) {
                leaderPOJOS.addAll(responsePOJO.getResultList());
                rv_leader.setVisibility(View.VISIBLE);
            } else {
                rv_leader.setVisibility(View.GONE);
                ToastClass.showShortToast(getApplicationContext(), "No Leader Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        leaderAdapter.notifyDataSetChanged();

    }
}
