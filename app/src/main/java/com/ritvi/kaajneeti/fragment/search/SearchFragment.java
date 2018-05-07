package com.ritvi.kaajneeti.fragment.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.pojo.AllSearchPOJO;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager(viewPager);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        et_search.post(new Runnable() {
            @Override
            public void run() {
                et_search.requestFocus();
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(et_search, InputMethodManager.SHOW_IMPLICIT);
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
                    callSearchAPI(et_search.getText().toString());
                }
            }
        });
    }

    public void callSearchAPI(String key) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("q", key));
        nameValuePairs.add(new BasicNameValuePair("start", "0"));
        nameValuePairs.add(new BasicNameValuePair("end", "10"));
        nameValuePairs.add(new BasicNameValuePair("search_in", "all"));
        new WebServiceBaseResponse<AllSearchPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<AllSearchPOJO>() {

            @Override
            public void onGetMsg(ResponsePOJO<AllSearchPOJO> responsePOJO) {
                if (responsePOJO.isSuccess()) {
                    showData(responsePOJO.getResult());
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                }
            }
        }, AllSearchPOJO.class, "ALL_SEARCH_API", false).execute(WebServicesUrls.ALL_SEARCH_API);
    }

    public void showData(AllSearchPOJO allSearchPOJO) {
//        eventSearchActivityShowFragment.setRVdata(allSearchPOJO.getEventFeeds());
//        pollSearchActivityShowFragment.setRVdata(allSearchPOJO.getPollFeeds());
//        postSearchActivityShowFragment.setRVdata(allSearchPOJO.getPostFeeds());
//        complaintSearchActivityShowFragment.setRVdata(allSearchPOJO.getComplaintFeeds());
//        suggestionSearchActivityShowFragment.setRVdata(allSearchPOJO.getSuggestionFeeds());
//        searchUserProfileFragment.setRVdata(allSearchPOJO.getProfileFeeds());
    }

    SearchActivityShowFragment eventSearchActivityShowFragment;
    SearchActivityShowFragment pollSearchActivityShowFragment;
    SearchActivityShowFragment postSearchActivityShowFragment;
    SearchActivityShowFragment complaintSearchActivityShowFragment;
    SearchActivityShowFragment suggestionSearchActivityShowFragment;
    SearchPeopleFragment searchUserProfileFragment;

    private void setupViewPager(ViewPager viewPager) {

//        eventSearchActivityShowFragment = new SearchActivityShowFragment();
//        pollSearchActivityShowFragment = new SearchActivityShowFragment();
//        postSearchActivityShowFragment = new SearchActivityShowFragment();
//        complaintSearchActivityShowFragment = new SearchActivityShowFragment();
//        suggestionSearchActivityShowFragment = new SearchActivityShowFragment();
//        searchUserProfileFragment = new SearchPeopleFragment();
//
//        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getChildFragmentManager());
//        adapter.addFrag(searchUserProfileFragment, "People");
//        adapter.addFrag(postSearchActivityShowFragment, "Post");
//        adapter.addFrag(eventSearchActivityShowFragment, "Event");
//        adapter.addFrag(pollSearchActivityShowFragment, "Poll");
//        adapter.addFrag(complaintSearchActivityShowFragment, "Complaint");
//        adapter.addFrag(suggestionSearchActivityShowFragment, "Suggestion");
//        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(adapter.getCount());
//        tabs.setupWithViewPager(viewPager);

    }
}
