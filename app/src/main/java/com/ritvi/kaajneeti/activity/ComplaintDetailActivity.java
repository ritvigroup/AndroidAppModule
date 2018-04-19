package com.ritvi.kaajneeti.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.adapter.EventAttachAdapter;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintAttachmentPOJO;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
import com.ritvi.kaajneeti.pojo.event.EventAttachment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComplaintDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_complaint_name)
    TextView tv_complaint_name;
    @BindView(R.id.tv_complaint_description)
    TextView tv_complaint_description;
    @BindView(R.id.rv_attachments)
    RecyclerView rv_attachments;
    @BindView(R.id.ll_attende)
    LinearLayout ll_attende;
    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.btn_reject)
    Button btn_reject;


    ComplaintPOJO complaintPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        complaintPOJO = (ComplaintPOJO) getIntent().getSerializableExtra("complaintPOJO");

        attachAdapter();
        if (complaintPOJO != null) {
            tv_complaint_name.setText(complaintPOJO.getComplaintSubject());
            tv_complaint_description.setText(complaintPOJO.getComplaintDescription());
            eventAttachments.clear();
            for (ComplaintAttachmentPOJO complaintAttachmentPOJO : complaintPOJO.getComplaintAttachments()) {
                String type = "";
                if (complaintAttachmentPOJO.getAttachmentType().equals("photo")) {
                    type = Constants.EVENT_IMAGE_ATTACH;
                } else {
                    type = Constants.EVENT_VIDEO_ATTACH;
                }
                EventAttachment eventAttachment = new EventAttachment("", type, complaintAttachmentPOJO.getAttachmentFile(), "",
                false);
                eventAttachments.add(eventAttachment);
            }
            eventAttachAdapter.notifyDataSetChanged();

            if(complaintPOJO.getComplaintProfile().getUserProfileDetailPOJO().getUserInfoPOJO().getUserId().equals(Constants.userInfoPOJO.getUserId())){
                ll_attende.setVisibility(View.GONE);
            }else{
                ll_attende.setVisibility(View.VISIBLE);
            }
        }
    }


    List<EventAttachment> eventAttachments = new ArrayList<>();
    EventAttachAdapter eventAttachAdapter;

    public void attachAdapter() {

        EventAttachment eventAttachment = new EventAttachment("", "", "", "", true);
        eventAttachments.add(eventAttachment);

        eventAttachAdapter = new EventAttachAdapter(this, null, eventAttachments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_attachments.setHasFixedSize(true);
        rv_attachments.setAdapter(eventAttachAdapter);
        rv_attachments.setLayoutManager(linearLayoutManager);
        rv_attachments.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
