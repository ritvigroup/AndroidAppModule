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

public class ForgotMpinConfirmActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_otp)
    EditText et_otp;
    @BindView(R.id.btn_next)
    Button btn_next;
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

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_otp.getText().toString().length() != 6) {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Valid OTP");
                } else {
                    startActivity(new Intent(ForgotMpinConfirmActivity.this, MpinActivity.class).putExtra("mobile_number",mobile_number));
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
