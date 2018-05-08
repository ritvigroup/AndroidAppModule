package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ritvi.kaajneeti.R;
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

public class ForgotMpinActivity extends AppCompatActivity {
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.et_login_otp)
    EditText et_login_otp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_mpin);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_login_otp.getText().toString().length()!=10){
                    ToastClass.showShortToast(getApplicationContext(),"Please Enter Valid Mobile Number");
                }else{
                    callForgotMPIN();
                }
            }
        });

    }


    private void callForgotMPIN() {
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("mobile","+91"+et_login_otp.getText().toString()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equalsIgnoreCase("success")){
                        startActivity(new Intent(ForgotMpinActivity.this,ForgotMpinConfirmActivity.class).putExtra("mobile_number","+91"+et_login_otp.getText().toString()));
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"FORGOT_MPIN",true).execute(WebServicesUrls.FORGOT_MPIN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
