package com.ritvi.kaajneeti.fragment;

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

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.GroupListAdapter;
import com.ritvi.kaajneeti.pojo.GroupPOJO;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupListFragment extends Fragment {

    @BindView(R.id.btn_add_group)
    Button btn_add_group;
    @BindView(R.id.rv_group)
    RecyclerView rv_group;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_group_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        btn_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    homeActivity.showSearchParticipantFragment();
                }
            }
        });

        getGrouplist();
    }

    public void getGrouplist() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBaseResponseList<GroupPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<GroupPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<GroupPOJO> responseListPOJO) {
                try {
                    groupPOJOS.clear();
                    if (responseListPOJO.isSuccess()) {
                        groupPOJOS.addAll(responseListPOJO.getResultList());
                    }
                    groupListAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, GroupPOJO.class, "group_details", false).execute(WebServicesUrls.GROUP_LIST);
    }

    List<GroupPOJO> groupPOJOS = new ArrayList<>();
    GroupListAdapter groupListAdapter;

    public void attachAdapter() {
        groupListAdapter = new GroupListAdapter(getActivity(), this, groupPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_group.setHasFixedSize(true);
        rv_group.setAdapter(groupListAdapter);
        rv_group.setLayoutManager(linearLayoutManager);
        rv_group.setItemAnimator(new DefaultItemAnimator());
    }
}
