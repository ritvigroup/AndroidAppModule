package com.ritvi.kaajneeti.fragment.homeactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ritvi.kaajneeti.R;

import butterknife.ButterKnife;

/**
 * Created by sunil on 12-03-2018.
 */

public class InvestigateFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_investigate,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
