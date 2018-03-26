package com.ritvi.kaajneeti.fragment.adcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.CreateSuggestionActivity;
import com.ritvi.kaajneeti.activity.SuggestionSubmitFormActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 01-03-2018.
 */

public class SuggestionFragment extends Fragment{
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_father_name)
    EditText et_father_name;
    @BindView(R.id.et_mobile2)
    EditText et_mobile2;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_aadhar_number)
    EditText et_aadhar_number;

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
                Intent intent=new Intent(getActivity(), SuggestionSubmitFormActivity.class);
                intent.putExtra("name",et_name.getText().toString());
                intent.putExtra("father_name",et_father_name.getText().toString());
                intent.putExtra("mobile",et_mobile2.getText().toString());
                intent.putExtra("email",et_email.getText().toString());
                intent.putExtra("aadhar",et_aadhar_number.getText().toString());
                if(getActivity() instanceof CreateSuggestionActivity){
                    CreateSuggestionActivity createSuggestionActivity= (CreateSuggestionActivity) getActivity();
                    intent.putExtra("assigned_profile_id",createSuggestionActivity.leader_id);
                }
                startActivity(intent);
            }
        });
    }
}
