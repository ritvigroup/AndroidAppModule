package com.ritvi.kaajneeti.fragment.complaint;

import android.app.Activity;
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
import android.widget.ImageView;

import com.riontech.staggeredtextgridview.StaggeredTextGridView;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.activity.AddComplaintDescriptionActivity;
import com.ritvi.kaajneeti.activity.TagPeopleActivity;
import com.ritvi.kaajneeti.adapter.AttachPeopleAdapter;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 23-03-2018.
 */

public class GroupComplaintFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.btn_add_applicants)
    Button btn_add_applicants;
    @BindView(R.id.et_message)
    EditText et_message;
    @BindView(R.id.et_date)
    EditText et_date;
    @BindView(R.id.iv_date)
    ImageView iv_date;
    @BindView(R.id.staggerdGV)
    StaggeredTextGridView staggerdGV;

    List<UserInfoPOJO> taggedUserProfilePOJOS = new ArrayList<>();
    private static final int TAG_PEOPLE = 105;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_group_complaint, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        GroupComplaintFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "");
            }
        });

        btn_add_applicants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), TagPeopleActivity.class).putExtra("taggedpeople", (Serializable) taggedUserProfilePOJOS), TAG_PEOPLE);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddComplaintDescriptionActivity.class);
                intent.putExtra("applicant_name", Constants.userInfoPOJO.getUserProfileCitizen().getFirstName()+" "+Constants.userInfoPOJO.getUserProfileCitizen().getLastName());
                intent.putExtra("applicant_father_name", "");
                intent.putExtra("applicant_mobile", "");
                intent.putExtra("complaint_type_id", "3");
                intent.putExtra("taggedpeople", (Serializable) taggedUserProfilePOJOS);
                startActivity(intent);
            }
        });
        attachStaggeredAdapter();
    }

    AttachPeopleAdapter adapter;

    public void attachStaggeredAdapter() {
        adapter = new AttachPeopleAdapter(getActivity(), taggedUserProfilePOJOS);
        staggerdGV.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAG_PEOPLE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TagUtils.getTag(), "people tag fragment");
                taggedUserProfilePOJOS.clear();
                List<UserInfoPOJO> userProfilePOJOS = (List<UserInfoPOJO>) data.getSerializableExtra("taggedpeople");
                Log.d(TagUtils.getTag(), "tagged users:-" + userProfilePOJOS.toString());
                taggedUserProfilePOJOS.addAll(userProfilePOJOS);
                attachStaggeredAdapter();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }

        String date = day + "-" + month + "-" + year;
        et_date.setText(date);
    }
}
