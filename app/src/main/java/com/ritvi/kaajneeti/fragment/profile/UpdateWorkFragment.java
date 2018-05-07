package com.ritvi.kaajneeti.fragment.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.userdetail.WorkPOJO;
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
public class UpdateWorkFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;

    private static final String WORK_FROM = "work_from";
    private static final String WORK_TO = "work_to";
    private static String CALENDAR_TYPE = WORK_FROM;

    @BindView(R.id.et_work)
    EditText et_work;
    @BindView(R.id.et_work_position)
    EditText et_work_position;
    @BindView(R.id.et_work_location)
    EditText et_work_location;
    @BindView(R.id.tv_work_from)
    TextView tv_work_from;
    @BindView(R.id.ll_work_to)
    LinearLayout ll_work_to;
    @BindView(R.id.tv_work_to)
    TextView tv_work_to;
    @BindView(R.id.view_work_to)
    View view_work_to;
    @BindView(R.id.check_currently_work)
    CheckBox check_currently_work;
    @BindView(R.id.iv_location)
    ImageView iv_location;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.btn_save)
    Button btn_save;

    String user_id;
    String profile_id;
    WorkPOJO workPOJO;

    public UpdateWorkFragment(String user_id, String profile_id,WorkPOJO workPOJO){
        this.user_id=user_id;
        this.profile_id=profile_id;
        this.workPOJO=workPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_update_work, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UtilityFunction.checkEdits(et_work,et_work_position,et_work_location)){
                    saveWork();
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Enter All Fields Properly");
                }
            }
        });


        check_currently_work.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ll_work_to.setVisibility(View.GONE);
                    view_work_to.setVisibility(View.GONE);
                }else{
                    ll_work_to.setVisibility(View.VISIBLE);
                    view_work_to.setVisibility(View.VISIBLE);
                }
            }
        });


        tv_work_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CALENDAR_TYPE = WORK_FROM;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        UpdateWorkFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Select Date");
            }
        });

        if(workPOJO!=null){
            setValues();
        }

        iv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPlace();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
//
//        et_work_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

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


    public void findPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    public void backPressed(){
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void setValues(){
        et_work.setText(workPOJO.getWorkPosition());
        et_work_position.setText(workPOJO.getWorkPosition());
        et_work_location.setText(workPOJO.getWorkLocation());
        tv_work_from.setText(workPOJO.getWorkFrom());
        tv_work_to.setText(workPOJO.getWorkTo());
        if(workPOJO.getCurrentlyWorking().equals("1")){
            check_currently_work.setChecked(true);
        }else {
            check_currently_work.setChecked(false);
        }

    }

    public void saveWork(){

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", profile_id));
        if(workPOJO!=null) {
            nameValuePairs.add(new BasicNameValuePair("work_id", workPOJO.getUserProfileWorkId()));
        }else{
            nameValuePairs.add(new BasicNameValuePair("work_id", ""));
        }
        nameValuePairs.add(new BasicNameValuePair("work", et_work.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("place", et_work_location.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("location", et_work_location.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("from", tv_work_from.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("to", tv_work_to.getText().toString()));
        if (check_currently_work.isChecked()) {
            nameValuePairs.add(new BasicNameValuePair("currently", "1"));
        } else {
            nameValuePairs.add(new BasicNameValuePair("currently", "0"));
        }
        nameValuePairs.add(new BasicNameValuePair("private_public", "1"));

        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equals("success")){
                        if(workPOJO!=null) {
                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Work Updated");
                        }else{
                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Work Added");
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
        }, "CALL_WORK_API", true).execute(WebServicesUrls.UPDATE_PROFILE_WORK);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        if (CALENDAR_TYPE.equals(WORK_FROM)) {
            tv_work_from.setText(date);
        } else if (CALENDAR_TYPE.equals(WORK_TO)) {
            tv_work_to.setText(date);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i(TagUtils.getTag(), "Place: " + place.getName());
                et_work_location.setText((String) place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TagUtils.getTag(), status.getStatusMessage());

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}


