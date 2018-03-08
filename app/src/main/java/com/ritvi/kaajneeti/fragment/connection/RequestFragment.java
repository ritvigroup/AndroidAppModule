package com.ritvi.kaajneeti.fragment.connection;

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
import com.ritvi.kaajneeti.adapter.ConnectionRequestAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 05-03-2018.
 */

public class RequestFragment extends Fragment {
    @BindView(R.id.rv_complaints)
    RecyclerView rv_complaints;
    List<String> complaints = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_conn_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
    }

    ConnectionRequestAdapter connectionInvitationAdapter;

    public void attachAdapter() {

        for (int i = 0; i < 10; i++) {
            complaints.add("");
        }

        connectionInvitationAdapter = new ConnectionRequestAdapter(getActivity(), this, complaints);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_complaints.setHasFixedSize(true);
        rv_complaints.setAdapter(connectionInvitationAdapter);
        rv_complaints.setLayoutManager(linearLayoutManager);
        rv_complaints.setItemAnimator(new DefaultItemAnimator());
    }
}
