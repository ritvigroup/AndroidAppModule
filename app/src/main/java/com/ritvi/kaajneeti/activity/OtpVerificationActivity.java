package com.ritvi.kaajneeti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtpVerificationActivity extends LocalizationActivity implements WebServicesCallBack {


    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.progress_timer)
    ProgressBar progress_timer;
    @BindView(R.id.et_otp)
    EditText et_otp;

    String mobile_number = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobile_number = bundle.getString("mobile_number");
        }

        et_otp.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btn_accept.callOnClick();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_otp.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateMobileOTP();
//                startActivity(new Intent(OtpVerificationActivity.this,MpinActivity.class));
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void validateMobileOTP() {
        if (et_otp.getText().length() == 6) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("device_token", ""));
            nameValuePairs.add(new BasicNameValuePair("mobile", mobile_number));
            nameValuePairs.add(new BasicNameValuePair("otp", et_otp.getText().toString()));
            new WebServiceBase(nameValuePairs, this, this, Constants.CALL_OTP_VERIFIED, true).execute(WebServicesUrls.REGISTER_VALIDATE_MOBILE_OTP);
        } else {
            ToastClass.showShortToast(getApplicationContext(),"Please Enter Correct OTP");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new CountDownTimer(120000, 1000) {

            @Override
            public void onTick(long l) {
//                Log.d(TagUtils.getTag(), "time elapsed:-" + l);
                progress_timer.setProgress((int) l);
            }

            @Override
            public void onFinish() {
                progress_timer.setProgress(0);
            }
        }.start();
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        if(apicall.equals(Constants.CALL_OTP_VERIFIED)){
            parseOTPVerifiedResponse(response);
        }
    }

    public void parseOTPVerifiedResponse(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString(Constants.API_STATUS).equals(Constants.API_SUCCESS)){
//                String userprofile=jsonObject.optJSONObject("user_info").toString();
//                Gson gson=new Gson();
//                UserProfilePOJO userProfilePOJO=gson.fromJson(userprofile,UserProfilePOJO.class);
//                Pref.SaveUserProfile(getApplicationContext(),userProfilePOJO);
                startActivity(new Intent(OtpVerificationActivity.this,MpinActivity.class).putExtra("mobile_number",mobile_number));
            }
            ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
