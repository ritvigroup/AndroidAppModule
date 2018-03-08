package com.ritvi.kaajneeti.fragment;

import android.annotation.SuppressLint;
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
import com.ritvi.kaajneeti.adapter.SearchVoterAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 04-03-2018.
 */

@SuppressLint("ValidFragment")
public class SearchVoterFragment extends Fragment{

    @BindView(R.id.rv_users)
    RecyclerView rv_users;

    List<String> search_voter_list=new ArrayList<>();
    public SearchVoterFragment(List<String> search_voter_list){
        this.search_voter_list=search_voter_list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_search_voter,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
    }
    SearchVoterAdapter searchVoterAdapter;
    public void attachAdapter(){
        searchVoterAdapter= new SearchVoterAdapter(getActivity(), this, search_voter_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rv_users.setHasFixedSize(true);
        rv_users.setAdapter(searchVoterAdapter);
        rv_users.setLayoutManager(linearLayoutManager);
        rv_users.setItemAnimator(new DefaultItemAnimator());
    }
}
