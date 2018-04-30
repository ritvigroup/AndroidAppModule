package com.ritvi.kaajneeti.fragment.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.userdetail.EducationPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 22-04-2018.
 */

@SuppressLint("ValidFragment")
public class UpdateEducationFragment extends Fragment implements DatePickerDialog.OnDateSetListener{


    @BindView(R.id.et_college_name)
    EditText et_college_name;
    @BindView(R.id.et_qualification)
    EditText et_qualification;
    @BindView(R.id.et_college_place)
    EditText et_college_place;
    @BindView(R.id.tv_college_from)
    TextView tv_college_from;
    @BindView(R.id.ll_college_to)
    LinearLayout ll_college_to;
    @BindView(R.id.tv_college_to)
    TextView tv_college_to;
    @BindView(R.id.view_college_to)
    View view_college_to;
    @BindView(R.id.check_college_currently)
    CheckBox check_college_currently;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.btn_save)
    Button btn_save;

    private static final String EDUCATION_FROM = "education_from";
    private static final String EDUCATION_TO = "education_to";

    private static String CALENDAR_TYPE = EDUCATION_FROM;

    String user_id;
    String profile_id;

    EducationPOJO educationPOJO;

    public UpdateEducationFragment(String user_id, String profile_id,EducationPOJO educationPOJO){
        this.user_id=user_id;
        this.profile_id=profile_id;
        this.educationPOJO=educationPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_update_education, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UtilityFunction.checkEdits(et_college_name,et_college_place,et_qualification)){
                    saveEducation();
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Enter All Fields Properly");
                }
            }
        });


        tv_college_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CALENDAR_TYPE = EDUCATION_FROM;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        UpdateEducationFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Select Date");
            }
        });

        tv_college_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CALENDAR_TYPE = EDUCATION_TO;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        UpdateEducationFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Select Date");
            }
        });
        if(educationPOJO!=null) {
            setValues();
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
////                    Log.i(TagUtils.getTag(), "onKey Back listener is working!!!");
//                    backPressed();
//                    return true;
//                }
//                return false;
//            }
//        });

    }

    public void backPressed(){
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void setValues(){
        et_college_name.setText(educationPOJO.getQualificationUniversity());
        et_qualification.setText(educationPOJO.getQualification());
        et_college_place.setText(educationPOJO.getQualificationLocation());
        tv_college_from.setText(educationPOJO.getQualificationFrom());
        tv_college_to.setText(educationPOJO.getQualificationTo());
        if(educationPOJO.getPersuing().equals("1")) {
            check_college_currently.setChecked(true);
        }else{
            check_college_currently.setChecked(false);
        }
    }

    public void saveEducation(){

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        if(educationPOJO!=null) {
            nameValuePairs.add(new BasicNameValuePair("education_id", educationPOJO.getUserProfileEducationId()));
        }else{
            nameValuePairs.add(new BasicNameValuePair("education_id", ""));
        }
        nameValuePairs.add(new BasicNameValuePair("qualification", et_qualification.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("location", et_college_place.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("university", et_college_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("from", tv_college_from.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("to", tv_college_to.getText().toString()));
        if(check_college_currently.isChecked()) {
            nameValuePairs.add(new BasicNameValuePair("persuing", "1"));
        }else{
            nameValuePairs.add(new BasicNameValuePair("persuing", "0"));
        }
        nameValuePairs.add(new BasicNameValuePair("private_public", ""));

        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equals("success")){
                        if(educationPOJO!=null) {
                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Education Updated");
                        }else{
                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Education Added");
                        }
                        if(getActivity() instanceof HomeActivity){
                            HomeActivity activity= (HomeActivity) getActivity();
                            activity.refreshUserProfileEditFragment();
                        }
                        getActivity().onBackPressed();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "CALL_COLLEGE_SAVE", true).execute(WebServicesUrls.UPDATE_PROFILE_EDUCATION);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        if (CALENDAR_TYPE.equals(EDUCATION_FROM)) {
            tv_college_from.setText(date);
        } else if (CALENDAR_TYPE.equals(EDUCATION_TO)) {
            tv_college_to.setText(date);
        }
    }
}


