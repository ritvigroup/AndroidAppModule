package com.ritvi.kaajneeti.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.adapter.StateAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StateSelectActivity extends LocalizationActivity {
    List<String> stateList = new ArrayList<>();

    @BindView(R.id.rv_places)
    RecyclerView rv_places;
    @BindView(R.id.et_place_search)
    EditText et_place_search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_select);
        ButterKnife.bind(this);
        stateList= Constants.setStateList();
        attachAdapter();

        et_place_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(stateAdapter!=null){
                    if(et_place_search.getText().toString().length()>0) {
                        stateAdapter.getFilter().filter(et_place_search.getText().toString());
                    }else{
                        stateAdapter.getFilter().filter(" ");
                    }
                }
            }
        });

    }

    public void showSelectedState(String state_name) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("state", state_name);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    StateAdapter stateAdapter;
    public void attachAdapter() {

        stateAdapter = new StateAdapter(this, null, stateList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_places.setHasFixedSize(true);
        rv_places.setAdapter(stateAdapter);
        rv_places.setLayoutManager(layoutManager);
        rv_places.setNestedScrollingEnabled(false);
        rv_places.setItemAnimator(new DefaultItemAnimator());
    }


}
