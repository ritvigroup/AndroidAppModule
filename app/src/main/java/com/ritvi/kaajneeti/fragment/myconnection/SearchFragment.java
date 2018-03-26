package com.ritvi.kaajneeti.fragment.myconnection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.SearchUserProfileAdapter;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.user.AllUserProfilePOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
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

public class SearchFragment extends Fragment {
    @BindView(R.id.rv_complaints)
    RecyclerView rv_complaints;
    @BindView(R.id.et_search)
    EditText et_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_all_report, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        rv_complaints.setVisibility(View.GONE);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_search.getText().toString().length()>0){
                    rv_complaints.setVisibility(View.VISIBLE);
                    searchUser();
                }else{
                    rv_complaints.setVisibility(View.GONE);
                }
            }
        });
    }

    public void searchUser() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePojo.getCitizenProfilePOJO().getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("search", et_search.getText().toString()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    ResponsePOJO<AllUserProfilePOJO> responsePOJO = new Gson().fromJson(response, new TypeToken<ResponsePOJO<AllUserProfilePOJO>>() {
                    }.getType());
                    userProfilePOJOS.clear();
                    if (responsePOJO.getStatus().equals("success")) {
                        userProfilePOJOS.addAll(responsePOJO.getResult().getCitizenProfilePOJOList());

                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                    }
                    searchUserProfileAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "CALL_SEARCH_PEOPLE_API", false).execute(WebServicesUrls.SEARCH_USER_PROFILE);
    }

    SearchUserProfileAdapter searchUserProfileAdapter;
    List<UserProfilePOJO> userProfilePOJOS=new ArrayList<>();
    public void attachAdapter() {
        searchUserProfileAdapter = new SearchUserProfileAdapter(getActivity(), this, userProfilePOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_complaints.setHasFixedSize(true);
        rv_complaints.setAdapter(searchUserProfileAdapter);
        rv_complaints.setLayoutManager(linearLayoutManager);
        rv_complaints.setItemAnimator(new DefaultItemAnimator());
    }
}
