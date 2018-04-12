package com.ritvi.kaajneeti.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.LeaderAdapter;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.user.SearchUserResultPOJO;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllLeaderActivity extends LocalizationActivity{

    private static final String CALL_ALL_LEADER = "call_all_leader";
    @BindView(R.id.rv_leader)
    RecyclerView rv_leader;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    List<UserInfoPOJO> leaderPOJOS = new ArrayList<>();
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_search_close)
    ImageView iv_search_close;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_leader);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Leader");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        attachAdapter();

        iv_search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });
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
                    callLeaderAPI();
                } else {
                    iv_search_close.setVisibility(View.GONE);
                }
            }
        });


        callLeaderAPI();
    }

    LeaderAdapter leaderAdapter;

    public void attachAdapter() {
        leaderAdapter = new LeaderAdapter(this, null, leaderPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_leader.setHasFixedSize(true);
        rv_leader.setAdapter(leaderAdapter);
        rv_leader.setLayoutManager(linearLayoutManager);
        rv_leader.setItemAnimator(new DefaultItemAnimator());

    }

    public void callLeaderAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("search", et_search.getText().toString()));
//        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
//            @Override
//            public void onGetMsg(String apicall, String response) {
//                leaderPOJOS.clear();
//                try {
//                    ResponsePOJO<SearchUserResultPOJO> responsePOJO = new Gson().fromJson(response, new TypeToken<ResponsePOJO<SearchUserResultPOJO>>() {
//                    }.getType());
//                    leaderPOJOS.clear();
//
//                    if (responsePOJO.isSuccess()) {
//                        leaderPOJOS.addAll(responsePOJO.getResult().getLeaderUserInfoPOJOS());
//                    } else {
//                        ToastClass.showShortToast(getApplicationContext(), responsePOJO.getMessage());
//                    }
//                    leaderAdapter.notifyDataSetChanged();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                leaderAdapter.notifyDataSetChanged();
//            }
//        }, CALL_ALL_LEADER, false).execute(WebServicesUrls.SEARCH_LEADER_PROFILE);



        new WebServiceBaseResponse<SearchUserResultPOJO>(nameValuePairs, this, new ResponseCallBack<SearchUserResultPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<SearchUserResultPOJO> responsePOJO) {
                leaderPOJOS.clear();
                if (responsePOJO.isSuccess()) {
                    leaderPOJOS.addAll(responsePOJO.getResult().getLeaderUserInfoPOJOS());
                    rv_leader.setVisibility(View.VISIBLE);
                } else {
                    rv_leader.setVisibility(View.GONE);
                    ToastClass.showShortToast(getApplicationContext(), "No Leader Found");
                }
                leaderAdapter.notifyDataSetChanged();
            }
        },SearchUserResultPOJO.class,CALL_ALL_LEADER,false).execute(WebServicesUrls.SEARCH_LEADER_PROFILE);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




}
