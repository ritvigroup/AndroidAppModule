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
                    startActivity(new Intent(ForgotMpinActivity.this,ForgotMpinConfirmActivity.class).putExtra("mobile_number","+91"+et_login_otp.getText().toString()));
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
