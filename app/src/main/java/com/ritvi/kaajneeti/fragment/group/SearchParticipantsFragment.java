package com.ritvi.kaajneeti.fragment.group;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.MyFriendListAdapter;
import com.ritvi.kaajneeti.adapter.ParticipatedAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchParticipantsFragment extends Fragment {

    @BindView(R.id.rv_users)
    RecyclerView rv_users;
    @BindView(R.id.rv_participants)
    RecyclerView rv_participants;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.btn_create)
    Button btn_create;

    private UserProfilePOJO participant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search_participants, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        attachParticipantAdapter();
        getAllUsers();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateGroup();
            }
        });
    }

    public void CreateGroup(){
//        if(participatedUserList.size()>=2) {
            if (getActivity() instanceof HomeActivity) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.showCreateGroupFragment(participatedUserList);
            }
//        }else{
//            ToastClass.showShortToast(getActivity().getApplicationContext(),"Please select atleast two users");
//        }
    }

    public void pressBack(){
        getActivity().onBackPressed();
    }

    public void getAllUsers() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBaseResponseList<UserProfilePOJO>(nameValuePairs, getActivity(), new ResponseListCallback<UserProfilePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<UserProfilePOJO> responseListPOJO) {
                userProfilePOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    userProfilePOJOS.addAll(responseListPOJO.getResultList());
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                }
                myFriendListAdapter.notifyDataSetChanged();
            }
        }, UserProfilePOJO.class, "CALL_ALL_REQUEST_API", false).execute(WebServicesUrls.MY_FRIENDS);
    }

    List<UserProfilePOJO> userProfilePOJOS = new ArrayList<>();
    MyFriendListAdapter myFriendListAdapter;

    public void attachAdapter() {
        myFriendListAdapter = new MyFriendListAdapter(getActivity(), this, userProfilePOJOS,true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_users.setHasFixedSize(true);
        rv_users.setAdapter(myFriendListAdapter);
        rv_users.setLayoutManager(linearLayoutManager);
        rv_users.setItemAnimator(new DefaultItemAnimator());
    }

    List<UserProfilePOJO> participatedUserList = new ArrayList<>();

    public void setParticipant(UserProfilePOJO participant) {
        this.participant = participant;
        participatedUserList.add(participant);
        participatedAdapter.notifyDataSetChanged();
    }

    public void deleteParticipant(UserProfilePOJO userProfilePOJO){
        participatedUserList.remove(userProfilePOJO);
        participatedAdapter.notifyDataSetChanged();
    }

    ParticipatedAdapter participatedAdapter;
    public void attachParticipantAdapter() {
        participatedAdapter = new ParticipatedAdapter(getActivity(), this, participatedUserList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_participants.setHasFixedSize(true);
        rv_participants.setAdapter(participatedAdapter);
        rv_participants.setLayoutManager(linearLayoutManager);
        rv_participants.setItemAnimator(new DefaultItemAnimator());
    }

    public void removepariticpant(UserProfilePOJO userProfilePOJO) {
        userProfilePOJOS.get(userProfilePOJO.getSelected_position()).setIs_checked(false);
        myFriendListAdapter.notifyDataSetChanged();
    }
}
