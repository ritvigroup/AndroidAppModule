package com.ritvi.kaajneeti.fragment.complaint;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.ComplaintHistoryAdapter;
import com.ritvi.kaajneeti.fragment.CreateComplaintReplyFragment;
import com.ritvi.kaajneeti.fragment.search.SearchFragment;
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

@SuppressLint("ValidFragment")
public class ComplaintTrackFragment extends Fragment{

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.frame_search)
    FrameLayout frame_search;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_complaints)
    RecyclerView rv_complaints;
    @BindView(R.id.tv_reply)
    TextView tv_reply;
    @BindView(R.id.tv_not_acknowledge)
    TextView tv_not_acknowledge;
    @BindView(R.id.ll_track)
    LinearLayout ll_track;

    ComplaintPOJO complaintPOJO;
    public ComplaintTrackFragment(ComplaintPOJO complaintPOJO){
        this.complaintPOJO=complaintPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_tack_complaint,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        tv_title.setText(complaintPOJO.getComplaintSubject());

        frame_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    SearchFragment searchFragment = new SearchFragment();
                    homeActivity.replaceFragmentinFrameHome(searchFragment,"searchFragment");
                }
            }
        });
        attachAdapter();
        callAPI();

        tv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    CreateComplaintReplyFragment complaintReplyFragment = new CreateComplaintReplyFragment(complaintPOJO);
                    homeActivity.replaceFragmentinFrameHome(complaintReplyFragment,"complaintReplyFragment");
                }
            }
        });
        if (complaintPOJO.getComplaintStatus().equals("4") || complaintPOJO.getComplaintStatus().equals("5")) {
            tv_reply.setVisibility(View.GONE);
        }else{
            tv_reply.setVisibility(View.VISIBLE);
        }
    }

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("complaint_id", complaintPOJO.getComplaintId()));
        new WebServiceBaseResponseList<ComplaintHistoryPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<ComplaintHistoryPOJO>() {
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

        complaintHistoryAdapter = new ComplaintHistoryAdapter(getActivity(), null, complaintHistoryPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_complaints.setHasFixedSize(true);
        rv_complaints.setAdapter(complaintHistoryAdapter);
        rv_complaints.setLayoutManager(linearLayoutManager);
        rv_complaints.setItemAnimator(new DefaultItemAnimator());
    }

    public void showFeedbackDialog(final String title) {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
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
                new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
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

}
