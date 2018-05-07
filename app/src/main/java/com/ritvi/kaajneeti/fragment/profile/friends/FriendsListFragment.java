package com.ritvi.kaajneeti.fragment.profile.friends;

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
import android.widget.ImageView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.adapter.MyFriendListAdapter;
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

public class FriendsListFragment extends Fragment {
    @BindView(R.id.rv_friends)
    RecyclerView rv_friends;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_friend_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        getAllUsers();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
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
        myFriendListAdapter = new MyFriendListAdapter(getActivity(), this, userProfilePOJOS, false,null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_friends.setHasFixedSize(true);
        rv_friends.setAdapter(myFriendListAdapter);
        rv_friends.setLayoutManager(linearLayoutManager);
        rv_friends.setItemAnimator(new DefaultItemAnimator());
    }

}
