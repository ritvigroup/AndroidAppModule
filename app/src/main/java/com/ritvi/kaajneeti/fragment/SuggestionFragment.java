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
import com.ritvi.kaajneeti.activity.SuggestionSubmitFormActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 01-03-2018.
 */

public class SuggestionFragment extends Fragment{
    @BindView(R.id.btn_next)
    Button btn_next;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_suggestion,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SuggestionSubmitFormActivity.class));
            }
        });
    }
}
