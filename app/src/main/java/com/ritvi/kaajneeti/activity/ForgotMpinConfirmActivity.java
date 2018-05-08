package com.ritvi.kaajneeti.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
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

public class ForgotMpinConfirmActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_otp)
    EditText et_otp;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.progress_timer)
    ProgressBar progress_timer;
    @BindView(R.id.tv_resend_otp)
    TextView tv_resend_otp;

    String mobile_number="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_mpin_confirm);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mobile_number=getIntent().getStringExtra("mobile_number");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

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

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_otp.getText().toString().length() != 6) {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Valid OTP");
                } else {
                    callVARIFYOTP();
                }
            }
        });

        tv_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callForgotMPIN();
            }
        });
    }


    private void callForgotMPIN() {
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("mobile",mobile_number));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equalsIgnoreCase("success")){
//                        startActivity(new Intent(ForgotMpinConfirmActivity.this,ForgotMpinConfirmActivity.class).putExtra("mobile_number",mobile_number));
                        startTimer();
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"FORGOT_MPIN",true).execute(WebServicesUrls.FORGOT_MPIN);
    }

    private void callVARIFYOTP() {
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("mobile",mobile_number));
        nameValuePairs.add(new BasicNameValuePair("reset_code",et_otp.getText().toString()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                Log.d(TagUtils.getTag(),"response:-"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equalsIgnoreCase("success")){
                        startActivity(new Intent(ForgotMpinConfirmActivity.this, MpinActivity.class).putExtra("mobile_number",mobile_number));
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_VALIDATE_MPIN",true).execute(WebServicesUrls.VALIDATE_FORGOT_MPIN);
    }

    public void startTimer(){
        tv_resend_otp.setEnabled(false);
        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long l) {
//                Log.d(TagUtils.getTag(), "time elapsed:-" + l);
                progress_timer.setProgress((int) l);
            }

            @Override
            public void onFinish() {
                progress_timer.setProgress(0);
                tv_resend_otp.setEnabled(true);
            }
        }.start();
    }

    private void callResendOTPAPI() {
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("",""));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                Log.d(TagUtils.getTag(),"response:-"+response);
            }
        },"CALL_RESEND_MPIN_OTP",true).execute(WebServicesUrls.VALIDATE_FORGOT_MPIN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
        getApplicationContext().registerReceiver(mMessageReceiver, new IntentFilter(StringUtils.FORGOT_SMS_CLASS));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra("message");
            Log.d(TagUtils.getTag(), "result:-" + result);
            if (result != null && result.length() > 0) {
                try {
                    Log.d(TagUtils.getTag(), "message received:-" + result);
//                    String[] msgsplit = result.split("c-");
//                    Log.d(TagUtils.getTag(), "otp:-" + msgsplit[1].trim());
                    result=result.replace(" is the OTP verifying your mobile with kaajneeti","");
                    Log.d(TagUtils.getTag(),"otp:-"+result);
                    et_otp.setText(result);
                    btn_next.callOnClick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
