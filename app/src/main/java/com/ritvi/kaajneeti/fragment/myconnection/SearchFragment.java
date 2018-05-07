package com.ritvi.kaajneeti.fragment.myconnection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.ViewPagerWithTitleAdapter;
import com.ritvi.kaajneeti.fragment.search.LeaderSearchFragment;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.user.SearchUserResultPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 05-03-2018.
 */

public class SearchFragment extends Fragment {

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    boolean is_initialized=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    public void initialize(){
        if(!is_initialized) {
            setUpTabswithViewPager();
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
                        searchUser();
                    } else {
                        citizenSearchFragment.setRv_users(new ArrayList<UserProfilePOJO>());
                        leaderSearchFragment.setRv_users(new ArrayList<UserProfilePOJO>());
                    }
                }
            });
            is_initialized=true;
        }
    }

    public void searchUser() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("search", et_search.getText().toString()));

        new WebServiceBaseResponse<SearchUserResultPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<SearchUserResultPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<SearchUserResultPOJO> responsePOJO) {
                if (responsePOJO.isSuccess()) {
                    Log.d(TagUtils.getTag(),"citizen list:-"+responsePOJO.getResult().getCitizenUserInfoPOJOS().size());
                    Log.d(TagUtils.getTag(),"leader list:-"+responsePOJO.getResult().getLeaderUserInfoPOJOS().size());
                    citizenSearchFragment.setRv_users(responsePOJO.getResult().getCitizenUserInfoPOJOS());
                    leaderSearchFragment.setRv_users(responsePOJO.getResult().getLeaderUserInfoPOJOS());
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                }
            }
        },SearchUserResultPOJO.class,"CALL_SEARCH_USER",false).execute(WebServicesUrls.SEARCH_USER_PROFILE);
    }

    public void setUpTabswithViewPager() {
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
    }

    LeaderSearchFragment citizenSearchFragment,leaderSearchFragment;
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getChildFragmentManager());

        citizenSearchFragment= new LeaderSearchFragment();
        leaderSearchFragment = new LeaderSearchFragment();

        adapter.addFrag(citizenSearchFragment, "Citizen");
        adapter.addFrag(leaderSearchFragment, "Leader");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());

    }

}
