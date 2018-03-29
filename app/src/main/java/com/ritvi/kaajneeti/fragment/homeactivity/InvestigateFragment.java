package com.ritvi.kaajneeti.fragment.homeactivity;

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
import com.ritvi.kaajneeti.adapter.ComplaitnAnalyzeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 12-03-2018.
 */

public class InvestigateFragment extends Fragment{

    @BindView(R.id.rv_analyze)
    RecyclerView rv_analyze;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_investigate,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
    }

    List<String> comStringList = new ArrayList<>();
    ComplaitnAnalyzeAdapter complaitnAnalyzeAdapter;

    public void attachAdapter() {

        comStringList.add("Electricity Issues");
        comStringList.add("Water Issues");
        comStringList.add("Sewage Problems");

        complaitnAnalyzeAdapter = new ComplaitnAnalyzeAdapter(getActivity(), this, comStringList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_analyze.setHasFixedSize(true);
        rv_analyze.setAdapter(complaitnAnalyzeAdapter);
        rv_analyze.setLayoutManager(linearLayoutManager);
        rv_analyze.setItemAnimator(new DefaultItemAnimator());
    }
}
