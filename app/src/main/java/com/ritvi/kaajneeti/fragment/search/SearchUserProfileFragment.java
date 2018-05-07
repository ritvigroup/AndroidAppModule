package com.ritvi.kaajneeti.fragment.search;

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
import com.ritvi.kaajneeti.adapter.SentRequestAdapter;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchUserProfileFragment extends Fragment {

    @BindView(R.id.rv_search)
    RecyclerView rv_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_all_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setRVdata(List<UserProfilePOJO> userProfilePOJOS){
        this.userProfilePOJOS.clear();
        this.userProfilePOJOS.addAll(userProfilePOJOS);
        this.searchUserProfileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attachAdapter();
    }



    SentRequestAdapter searchUserProfileAdapter;
    List<UserProfilePOJO> userProfilePOJOS = new ArrayList<>();

    public void attachAdapter() {
        searchUserProfileAdapter = new SentRequestAdapter(getActivity(), this, userProfilePOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_search.setHasFixedSize(true);
        rv_search.setAdapter(searchUserProfileAdapter);
        rv_search.setLayoutManager(linearLayoutManager);
        rv_search.setItemAnimator(new DefaultItemAnimator());
    }

}
