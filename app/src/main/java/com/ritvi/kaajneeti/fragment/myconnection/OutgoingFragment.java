package com.ritvi.kaajneeti.fragment.myconnection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.SentRequestAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.user.RequestProfilePOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
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
    List<String> complaints = new ArrayList<>();

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

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePojo.getCitizenProfilePOJO().getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePojo.getCitizenProfilePOJO().getUserProfileId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                Log.d(TagUtils.getTag(),apicall+":-"+response);
                try {
                    ResponseListPOJO<RequestProfilePOJO> responsePOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<RequestProfilePOJO>>() {
                    }.getType());
                    userProfilePOJOS.clear();
                    if (responsePOJO.getStatus().equals("success")) {
                        userProfilePOJOS.addAll(responsePOJO.getResultList());

                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                    }
                    searchUserProfileAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "CALL_ALL_REQUEST_API", false).execute(WebServicesUrls.OUTGOING_FRIEND_REQUEST);
    }

    SentRequestAdapter searchUserProfileAdapter;
    List<RequestProfilePOJO> userProfilePOJOS=new ArrayList<>();
    public void attachAdapter() {
        searchUserProfileAdapter = new SentRequestAdapter(getActivity(), this, userProfilePOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_complaints.setHasFixedSize(true);
        rv_complaints.setAdapter(searchUserProfileAdapter);
        rv_complaints.setLayoutManager(linearLayoutManager);
        rv_complaints.setItemAnimator(new DefaultItemAnimator());
    }
}
