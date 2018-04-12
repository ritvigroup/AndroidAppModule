package com.ritvi.kaajneeti.fragment.myconnection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.SentRequestAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 05-03-2018.
 */

public class OutgoingFragment extends Fragment {
    @BindView(R.id.rv_complaints)
    RecyclerView rv_complaints;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_outgoing, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        callAPI();
    }

    boolean is_initialize=false;
    public void initialize(){
        if(!is_initialize){

//            callAPI();
//            is_initialize=true;
        }
    }

    public void callAPI() {


        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        new WebServiceBaseResponseList<OutGoingRequestPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<OutGoingRequestPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<OutGoingRequestPOJO> responseListPOJO) {
                userProfilePOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    userProfilePOJOS.addAll(responseListPOJO.getResultList());
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                }
                searchUserProfileAdapter.notifyDataSetChanged();
            }
        },OutGoingRequestPOJO.class,"CALL_ALL_REQUEST_API",false).execute(WebServicesUrls.OUTGOING_FRIEND_REQUEST);
    }

    SentRequestAdapter searchUserProfileAdapter;
    List<OutGoingRequestPOJO> userProfilePOJOS=new ArrayList<>();
    public void attachAdapter() {
        searchUserProfileAdapter = new SentRequestAdapter(getActivity(), this, userProfilePOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_complaints.setHasFixedSize(true);
        rv_complaints.setAdapter(searchUserProfileAdapter);
        rv_complaints.setLayoutManager(linearLayoutManager);
        rv_complaints.setItemAnimator(new DefaultItemAnimator());
    }
}
