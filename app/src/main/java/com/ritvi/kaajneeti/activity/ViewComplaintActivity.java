package com.ritvi.kaajneeti.activity;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.adapter.ComplaintHistoryAdapter;
import com.ritvi.kaajneeti.fragment.CreateComplaintReplyFragment;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
import com.ritvi.kaajneeti.pojo.complaint.ComplaintHistoryPOJO;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.qap.ctimelineview.TimelineRow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewComplaintActivity extends AppCompatActivity {

    String complaint_name = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_complaints)
    RecyclerView rv_complaints;

    ComplaintPOJO complaintPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        complaintPOJO = (ComplaintPOJO) getIntent().getSerializableExtra("complaint");
        if (complaintPOJO != null) {
            getSupportActionBar().setTitle(complaintPOJO.getComplaintSubject());
        }
        attachAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        callAPI();

        if (complaintPOJO.getComplaintStatus().equals("4") || complaintPOJO.getComplaintStatus().equals("5")) {
            menu_reply.setVisible(false);
        }

    }

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("complaint_id", complaintPOJO.getComplaintId()));
        new WebServiceBaseResponseList<ComplaintHistoryPOJO>(nameValuePairs, this, new ResponseListCallback<ComplaintHistoryPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<ComplaintHistoryPOJO> responseListPOJO) {
                try {
                    complaintHistoryPOJOS.clear();
                    if (responseListPOJO.isSuccess()) {

                        complaintHistoryPOJOS.addAll(responseListPOJO.getResultList());

                    }

                    complaintHistoryAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ComplaintHistoryPOJO.class, "CALL_COMPLAINTDESCRIPTION_API", true).execute(WebServicesUrls.COMPLAINT_DETAIL);
    }


    ComplaintHistoryAdapter complaintHistoryAdapter;
    List<ComplaintHistoryPOJO> complaintHistoryPOJOS = new ArrayList<>();

    public void attachAdapter() {

        complaintHistoryAdapter = new ComplaintHistoryAdapter(this, null, complaintHistoryPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_complaints.setHasFixedSize(true);
        rv_complaints.setAdapter(complaintHistoryAdapter);
        rv_complaints.setLayoutManager(linearLayoutManager);
        rv_complaints.setItemAnimator(new DefaultItemAnimator());
    }

    public void showFeedbackDialog(final String title) {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_send_feedback);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final EditText et_title = dialog1.findViewById(R.id.et_title);
        Toolbar toolbar = dialog1.findViewById(R.id.toolbar);
        final EditText et_description = dialog1.findViewById(R.id.et_description);
        Button btn_submit = dialog1.findViewById(R.id.btn_submit);

        toolbar.setTitle(title);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
                nameValuePairs.add(new BasicNameValuePair("complaint_id", complaintPOJO.getComplaintId()));
                nameValuePairs.add(new BasicNameValuePair("history_id", "0"));
                nameValuePairs.add(new BasicNameValuePair("title", et_title.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("description", et_description.getText().toString()));
                if (title.equalsIgnoreCase("Send Feedback")) {
                    nameValuePairs.add(new BasicNameValuePair("current_status", "4"));
                } else {
                    nameValuePairs.add(new BasicNameValuePair("current_status", complaintPOJO.getComplaintStatus()));
                }
                new WebServiceBase(nameValuePairs, ViewComplaintActivity.this, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                                callAPI();
                                dialog1.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "CALL_COMPLAINT_HISTORY_ADD_API", true).execute(WebServicesUrls.SAVE_COMPLAINT_HISTORY);
            }
        });

    }

    public TimelineRow getTimeLineRow(ComplaintHistoryPOJO complaintHistoryPOJO) {
        try {

            TimelineRow myRow = new TimelineRow(0);

            String historyDate = complaintHistoryPOJO.getAddedOnTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            myRow.setDate(simpleDateFormat.parse(historyDate));
            myRow.setTitle(complaintHistoryPOJO.getHistoryTitle());
            myRow.setDescription(complaintHistoryPOJO.getHistoryDescription());
            myRow.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            myRow.setBellowLineColor(Color.argb(255, 0, 0, 0));
            myRow.setBellowLineSize(6);
            myRow.setImageSize(40);
            myRow.setBackgroundColor(Color.argb(255, 0, 0, 0));
            myRow.setBackgroundSize(60);
            myRow.setDateColor(Color.argb(255, 0, 0, 0));
            myRow.setTitleColor(Color.argb(255, 0, 0, 0));
            myRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

            return myRow;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    MenuItem menu_reply;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complaint_reply, menu);//Menu Resource, Menu
        menu_reply=menu.findItem(R.id.menu_reply);
        return true;
    }

    public void attachReplyFragment(){
        CreateComplaintReplyFragment createComplaintReplyFragment = new CreateComplaintReplyFragment(complaintPOJO);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, createComplaintReplyFragment, "createComplaintReplyFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reply:
//                Intent intent = new Intent(this, CreateEventHistoryActivity.class);
//                intent.putExtra("complaintPOJO", complaintPOJO);
//                startActivity(intent);
                attachReplyFragment();
                return true;
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
