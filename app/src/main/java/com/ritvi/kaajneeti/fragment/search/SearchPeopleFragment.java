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
import com.ritvi.kaajneeti.adapter.HomeFeedAdapter;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchPeopleFragment extends Fragment {

    @BindView(R.id.rv_search)
    RecyclerView rv_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search_people, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setRVdata(List<FeedPOJO> feedPOJOS){
        this.feedPOJOS.clear();
        this.feedPOJOS.addAll(feedPOJOS);
        this.homeFeedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attachAdapter();
    }


    List<FeedPOJO> feedPOJOS= new ArrayList<>();
    HomeFeedAdapter homeFeedAdapter;

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_search.setLayoutManager(linearLayoutManager);
        homeFeedAdapter = new HomeFeedAdapter(rv_search,getActivity(), this, feedPOJOS,getChildFragmentManager());
        rv_search.setHasFixedSize(true);
        rv_search.setAdapter(homeFeedAdapter);
        rv_search.setItemAnimator(new DefaultItemAnimator());
    }



}
