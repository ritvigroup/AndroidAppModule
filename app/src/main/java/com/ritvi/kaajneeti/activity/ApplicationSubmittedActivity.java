package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.ritvi.kaajneeti.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApplicationSubmittedActivity extends LocalizationActivity {

    @BindView(R.id.tv_complaint_id)
    TextView tv_complaint_id;
    @BindView(R.id.tv_comp_submitted)
    TextView tv_comp_submitted;

    String comp_type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_submitted);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            comp_type = bundle.getString("comp_type");
            switch (comp_type) {
                case "complaint":
                    tv_complaint_id.setText("You complaint has been submitted successfully");
                    tv_comp_submitted.setText("Complaint Submitted");
                    break;
                case "suggestion":
                    tv_complaint_id.setText("You Suggestion has been submitted successfully");
                    tv_comp_submitted.setText("Suggestion Submitted");
                    break;
                case "information":
                    tv_complaint_id.setText("You Information has been submitted successfully");
                    tv_comp_submitted.setText("Information Submitted");
                    break;
                case "issue":
                    tv_complaint_id.setText("You Issue has been submitted successfully");
                    tv_comp_submitted.setText("Issue Submitted");
                    break;

            }
//            tv_complaint_id.setText("Complaint ID : "+bundle.getString("complaint_id"));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ApplicationSubmittedActivity.this, HomeActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
