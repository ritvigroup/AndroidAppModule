package com.ritvi.kaajneeti.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.adapter.ViewPagerWithTitleAdapter;
import com.ritvi.kaajneeti.fragment.SearchVoterFragment;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String CALL_SEARCH_API = "call_search_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                callSearchAPI();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setUpTabswithViewPager();
    }

    public void setUpTabswithViewPager() {
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        tabs.setupWithViewPager(viewPager);
    }


    public void callSearchAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("",""));
        nameValuePairs.add(new BasicNameValuePair("",""));
        nameValuePairs.add(new BasicNameValuePair("",""));
        new WebServiceBase(nameValuePairs,this,this,CALL_SEARCH_API,false).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(),apicall+":-"+response);
        switch (apicall){
            case CALL_SEARCH_API:
                parseSearchResponse(response);
                break;
        }
    }

    public void parseSearchResponse(String response){
        setupViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {

        List<String> leaderList=new ArrayList<>();
        leaderList.add("Gaurav Gautam");
        leaderList.add("Avni Gautam");
        leaderList.add("Vaidehi");
        leaderList.add("Rajesh");

        List<String> votetList=new ArrayList<>();
        votetList.add("Sunil Vishwakarma");
        votetList.add("Aarti Vishwakarma");
        votetList.add("Monu Vishwakarma");
        votetList.add("Neelam Vishwakarma");


        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getSupportFragmentManager());
        adapter.addFrag(new SearchVoterFragment(leaderList), "Leader");
        adapter.addFrag(new SearchVoterFragment(votetList), "Voter");
        viewPager.setAdapter(adapter);

    }


}
