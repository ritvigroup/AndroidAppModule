package com.ritvi.kaajneeti.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ritvi.kaajneeti.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 01-02-2018.
 */

public class UrbanAddressFragment extends Fragment {

    @BindView(R.id.et_district)
    EditText et_district;
    @BindView(R.id.et_tehsil)
    EditText et_tehsil;
    @BindView(R.id.et_thana)
    EditText et_thana;
    @BindView(R.id.et_town_area)
    EditText et_town_area;
    @BindView(R.id.et_ward)
    EditText et_ward;
    @BindView(R.id.et_full_address)
    EditText et_full_address;
    @BindView(R.id.et_department)
    EditText et_department;
    @BindView(R.id.et_subject)
    EditText et_subject;
    @BindView(R.id.et_description)
    EditText et_description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_urban_address, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public String[] checkValidations(){
        String[] valueString=new String[9];
        valueString[0]=et_district.getText().toString();
        valueString[1]=et_tehsil.getText().toString();
        valueString[2]=et_thana.getText().toString();
        valueString[3]=et_town_area.getText().toString();
        valueString[4]=et_ward.getText().toString();
        valueString[5]=et_full_address.getText().toString();
        valueString[6]=et_department.getText().toString();
        valueString[7]=et_subject.getText().toString();
        valueString[8]=et_description.getText().toString();
        return valueString;
    }
}
