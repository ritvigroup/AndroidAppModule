package com.ritvi.kaajneeti.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.ritvi.kaajneeti.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateIssueActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_subject)
    EditText et_subject;
    @BindView(R.id.et_description)
    EditText et_description;
    @BindView(R.id.frame_attach)
    FrameLayout frame_attach;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_issue);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        showAttachFragment();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateIssueActivity.this,ApplicationSubmittedActivity.class).putExtra("comp_type","issue"));
            }
        });
    }

    AttachFragment attachFragment;
    public void showAttachFragment() {
        attachFragment = new AttachFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_attach, attachFragment, "attachFragment");
        transaction.addToBackStack(null);
        transaction.commit();
//        fragmentList.add(rewardsFragment);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
