package com.ritvi.kaajneeti.fragment.complaint;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.activity.AddCommunication;
import com.ritvi.kaajneeti.activity.AddComplaintDescriptionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 23-03-2018.
 */

public class OtherComplaintFragment extends Fragment{

    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_father_name)
    EditText et_father_name;
    @BindView(R.id.et_mobile_number)
    EditText et_mobile_number;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_self_complaint,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof AddCommunication) {
                    AddCommunication addCommunication = (AddCommunication) getActivity();
                    Log.d(TagUtils.getTag(), "leader id:-" + addCommunication.leader_id);
                    Intent intent = new Intent(getActivity(), AddComplaintDescriptionActivity.class);
                    intent.putExtra("applicant_name", et_name.getText().toString());
                    intent.putExtra("applicant_father_name", et_father_name.getText().toString());
                    intent.putExtra("applicant_mobile", et_mobile_number.getText().toString());
                    intent.putExtra("complaint_type_id", "2");
                    intent.putExtra("leader_id", addCommunication.leader_id);
                    startActivity(intent);
                }
            }
        });
    }
}
