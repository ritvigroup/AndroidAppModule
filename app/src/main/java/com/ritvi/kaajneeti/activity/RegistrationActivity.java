package com.ritvi.kaajneeti.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
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

public class RegistrationActivity extends LocalizationActivity implements View.OnClickListener, TextWatcher, WebServicesCallBack {


    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_phone_number_cancel)
    ImageView iv_phone_number_cancel;
    @BindView(R.id.et_phone_number)
    EditText et_phone_number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        btn_accept.setOnClickListener(this);

        et_phone_number.addTextChangedListener(this);
        iv_phone_number_cancel.setOnClickListener(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        et_phone_number.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btn_accept.callOnClick();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_phone_number.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (et_phone_number.getText().toString().length() > 0) {
            iv_phone_number_cancel.setVisibility(View.VISIBLE);
        } else {
            iv_phone_number_cancel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_phone_number_cancel:
                et_phone_number.setText("");
                break;
            case R.id.btn_accept:
                onAcceptPhoneNumber();
                break;
        }
    }

    public void onAcceptPhoneNumber() {
        if (et_phone_number.getText().toString().length() >= 10) {
//            callAPI
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("request_action", "REGISTER_MOBILE"));
            nameValuePairs.add(new BasicNameValuePair("device_token", ""));
            nameValuePairs.add(new BasicNameValuePair("mobile", "+91" + et_phone_number.getText().toString()));
            new WebServiceBase(nameValuePairs, this, this, Constants.CALL_REGISTER_API, true).execute(WebServicesUrls.REGISTER_URL);

//            startActivity(new Intent(RegistrationActivity.this, OtpVerificationActivity.class));
        }
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(), apicall + " :- " + response);

        if (apicall.equals(Constants.CALL_REGISTER_API)) {
            parseRegisterResponse(response);
        }
    }

    public void parseRegisterResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString(Constants.API_STATUS).equals(Constants.API_SUCCESS)) {
                startActivity(new Intent(RegistrationActivity.this, OtpVerificationActivity.class).putExtra("mobile_number", "+91" + et_phone_number.getText().toString()));
            } else {
                ToastClass.showShortToast(getApplicationContext(), ToastClass.SOMETHING_WENT_WRONG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), ToastClass.NO_INTERNET_CONNECTION);
        }
    }
}
