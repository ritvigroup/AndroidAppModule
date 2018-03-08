package com.ritvi.kaajneeti.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.AddCommunication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 27-02-2018.
 */

public class MyCommunicationFragment extends Fragment{
    @BindView(R.id.btn_add_communication)
    Button btn_add_communication;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_my_communication,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_add_communication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddCommunication.class));
            }
        });
    }
}
