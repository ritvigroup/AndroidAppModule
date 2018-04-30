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
import android.widget.EditText;
import android.widget.ImageView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.userdetail.AddressPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 22-04-2018.
 */

@SuppressLint("ValidFragment")
public class UpdateAddressFragment extends Fragment {

    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.et_country)
    EditText et_country;
    @BindView(R.id.et_pincode)
    EditText et_pincode;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_landmark)
    EditText et_landmark;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.btn_save)
    Button btn_save;

    String user_id;
    String profile_id;
    AddressPOJO addressPOJO;

    public UpdateAddressFragment(String user_id, String profile_id, AddressPOJO addressPOJO) {
        this.user_id = user_id;
        this.profile_id = profile_id;
        this.addressPOJO = addressPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_update_address, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityFunction.checkEdits(et_city, et_country, et_pincode, et_state)) {
                    saveAddress();
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), "Please Enter All Fields Properly");
                }
            }
        });

        if (addressPOJO != null) {
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

    public void setValues() {
        et_address.setText(addressPOJO.getAddress());
        et_city.setText(addressPOJO.getCity());
        et_country.setText(addressPOJO.getCountry());
        et_landmark.setText(addressPOJO.getLandmark());
        et_pincode.setText(addressPOJO.getPincode());
        et_state.setText(addressPOJO.getState());
    }

    public void saveAddress() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", profile_id));
        if (addressPOJO != null) {
            nameValuePairs.add(new BasicNameValuePair("address_id", addressPOJO.getUserProfileAddressId()));
        }
        nameValuePairs.add(new BasicNameValuePair("address", et_address.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("city", et_city.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("state", et_state.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("landmark", et_landmark.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("country", et_country.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("pincode", et_pincode.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("default", ""));
        nameValuePairs.add(new BasicNameValuePair("home_work", ""));
        nameValuePairs.add(new BasicNameValuePair("private_public", ""));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equals("success")){
                        if(addressPOJO!=null) {
                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Address Updated");
                        }else{
                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Address Added");
                        }
                        if(getActivity() instanceof HomeActivity){
                            HomeActivity activity= (HomeActivity) getActivity();
                            activity.refreshUserProfileEditFragment();
                        }
                        getActivity().onBackPressed();
//                        backPressed();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "UPDATE_ADDRESS", true).execute(WebServicesUrls.UPDATE_PROFILE_ADDRESS);
    }
}


