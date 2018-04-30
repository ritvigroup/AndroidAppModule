package com.ritvi.kaajneeti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.Pref;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginOtpConfirmActivity extends AppCompatActivity {
    private static final String CALL_LOGIN_OTP_VERIFY = "call_login_otp_verify";
    private static final String CALL_RESEND_OTP = "call_resend_otp";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_otp)
    EditText et_otp;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.pb_otp)
    ProgressBar pb_otp;
    @BindView(R.id.tv_resend_otp)
    TextView tv_resend_otp;

    String mobile_number = "";
    boolean is_timer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp_confirm);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobile_number = bundle.getString("mobile_number");
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_otp.getText().toString().length() != 6) {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Valid OTP");
                } else {
                    callOTPAPI();
//                    startActivity(new Intent(LoginOtpConfirmActivity.this, CitizenHomeActivity.class));
                }
            }
        });

        et_otp.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btn_next.callOnClick();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_otp.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        startTimer();

        tv_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_timer) {
//                    ToastClass.showShortToast(getApplicationContext(),"");
                } else {
                    callResendOtpAPI();
                }
            }
        });

    }

    public void startTimer() {
        new CountDownTimer(60000, 100) {

            @Override
            public void onTick(long l) {
//                Log.d(TagUtils.getTag(), "time:-" + l);
                pb_otp.setProgress((int) l);
                is_timer = true;
            }

            @Override
            public void onFinish() {
                is_timer = false;
            }
        }.start();
    }

    public void callResendOtpAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "REGENERATE_MOBILE_OTP"));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile_number));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                parseResendOTP(response);
            }
        }, CALL_RESEND_OTP, true).execute(WebServicesUrls.LOGIN_URL);
    }

    public void callOTPAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile_number));
        nameValuePairs.add(new BasicNameValuePair("otp", et_otp.getText().toString()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                parseLoginOTPResponse(response);
            }
        }, CALL_LOGIN_OTP_VERIFY, true).execute(WebServicesUrls.VERIFY_LOGIN_OTP);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void parseResendOTP(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("status").equals("success")) {
                startTimer();
            }
            ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseLoginOTPResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString(Constants.API_STATUS).equals(Constants.API_SUCCESS)) {
                String user_profile = jsonObject.optJSONObject("result").toString();
                Gson gson = new Gson();
                UserProfilePOJO userProfilePOJO = gson.fromJson(user_profile, UserProfilePOJO.class);
                Pref.SetStringPref(getApplicationContext(), StringUtils.USER_PROFILE, user_profile);
                Pref.SetBooleanPref(this, StringUtils.IS_LOGIN, true);
                Constants.userProfilePOJO = userProfilePOJO;
                startActivity(new Intent(this, HomeActivity.class));
                finishAffinity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
