package com.ritvi.kaajneeti.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.adapter.InfoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 27-02-2018.
 */

public class InfoDirectoryFragment extends Fragment{
    @BindView(R.id.rv_info)
    RecyclerView rv_info;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_info_directory,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
    }

    InfoAdapter infoAdapter;
    List<String> leaderPOJOS = new ArrayList<>();

    public void attachAdapter() {
        for (int i = 0; i < 7; i++) {
            leaderPOJOS.add("");
        }
        infoAdapter = new InfoAdapter(getActivity(), this, leaderPOJOS);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        rv_info.setHasFixedSize(true);
        rv_info.setAdapter(infoAdapter);
        rv_info.setLayoutManager(gridLayoutManager);
        rv_info.setItemAnimator(new DefaultItemAnimator());

    }
}
