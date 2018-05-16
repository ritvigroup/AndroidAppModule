package com.ritvi.kaajneeti.fragment.complaint;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.ApplicationSubmittedActivity;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.activity.SignActivity;
import com.ritvi.kaajneeti.adapter.AlreadyTaggedPeopleAdapter;
import com.ritvi.kaajneeti.adapter.ComplaintMembersAdapter;
import com.ritvi.kaajneeti.adapter.EventAttachAdapter;
import com.ritvi.kaajneeti.fragment.analyze.ComplaintListFragment;
import com.ritvi.kaajneeti.fragment.search.SearchFragment;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintAttachmentPOJO;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
import com.ritvi.kaajneeti.pojo.event.EventAttachment;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;
import com.ritvi.kaajneeti.webservice.WebUploadService;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
public class ComplaintDetailFragment extends Fragment {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.frame_search)
    FrameLayout frame_search;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_complaint_name)
    TextView tv_complaint_name;
    @BindView(R.id.tv_complaint_description)
    TextView tv_complaint_description;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_mobile_number)
    TextView tv_mobile_number;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;

    @BindView(R.id.rv_attachments)
    RecyclerView rv_attachments;
    @BindView(R.id.ll_attende)
    LinearLayout ll_attende;
    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.btn_reject)
    Button btn_reject;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    @BindView(R.id.tv_leader_name)
    TextView tv_leader_name;
    @BindView(R.id.tv_leader_email)
    TextView tv_leader_email;
    @BindView(R.id.tv_bio)
    TextView tv_bio;
    @BindView(R.id.email_tv)
    TextView email_tv;
    @BindView(R.id.ll_complaint_members)
    LinearLayout ll_complaint_members;
    @BindView(R.id.rv_complaint_members)
    RecyclerView rv_complaint_members;


    @BindView(R.id.btn_track)
    Button btn_track;

    ComplaintPOJO complaintPOJO;

    public ComplaintDetailFragment(ComplaintPOJO complaintPOJO) {
        this.complaintPOJO = complaintPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_complaint_details, container, false);
        ButterKnife.bind(this, view);
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

        getComplaintDetail();

    }

    public void getComplaintDetail() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("complaint_id", complaintPOJO.getComplaintId()));
        new WebServiceBaseResponse<ComplaintPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<ComplaintPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<ComplaintPOJO> responsePOJO) {
                try {
                    if (responsePOJO.isSuccess()) {
                        complaintPOJO=responsePOJO.getResult();
                        loadView();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    loadView();
                }
            }
        }, ComplaintPOJO.class, "COMPLAINT_DETAIL", true).execute(WebServicesUrls.GET_COMPLAINT_DETAIL);
    }

    public void loadView() {
        attachAlreadyTaggedAdapter();
        tv_title.setText(complaintPOJO.getComplaintSubject());
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

            tv_location.setText(complaintPOJO.getComplaintAddress() + " , " + complaintPOJO.getComplaintPlace());
            tv_date.setText(complaintPOJO.getAddedOnTime());
            UserProfilePOJO userProfilePOJO = complaintPOJO.getComplaintProfile();

            Log.d(TagUtils.getTag(), "complaint type id:-" + complaintPOJO.getComplaintTypeId());
            if (complaintPOJO.getComplaintTypeId().equalsIgnoreCase("3")) {
                email_tv.setText("Father's Name");

                tv_name.setText(complaintPOJO.getApplicantName());
                tv_mobile_number.setText(complaintPOJO.getApplicantMobile());
                tv_email.setText(complaintPOJO.getApplicantFatherName());
                ll_complaint_members.setVisibility(View.GONE);
            } else {
                if (userProfilePOJO != null) {
                    tv_name.setText(userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName());
                    tv_mobile_number.setText(userProfilePOJO.getMobile());
                    tv_email.setText(userProfilePOJO.getEmail());
                }
                email_tv.setText("Email");


                if (complaintPOJO.getComplaintTypeId().equalsIgnoreCase("2")) {
                    Log.d(TagUtils.getTag(), "complaint members:-" + complaintPOJO.getComplaintMemberPOJOS().size());
                    complaintMembersPojos.addAll(complaintPOJO.getComplaintMemberPOJOS());
                    ll_complaint_members.setVisibility(View.VISIBLE);

//                    if (!complaintPOJO.getComplaintProfile().getUserProfileId().equalsIgnoreCase(Constants.userProfilePOJO.getUserProfileId())) {
//                        ll_attende.setVisibility(View.VISIBLE);
//                    }

                    for (UserProfilePOJO userProfilePOJO1 : complaintPOJO.getComplaintMemberPOJOS()) {
                        if (userProfilePOJO1.getUserProfileId().equalsIgnoreCase(Constants.userProfilePOJO.getUserProfileId())) {
                            if (userProfilePOJO1.getAcceptedYesNo().equals("0")) {
                                btn_track.setVisibility(View.GONE);
                                btn_accept.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        acceptComplaint();
                                    }
                                });
                                btn_reject.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        rejectComplaint();
                                    }
                                });
                                ll_attende.setVisibility(View.VISIBLE);
                            } else if (userProfilePOJO1.getAcceptedYesNo().equals("-1")) {
                                btn_track.setVisibility(View.GONE);
                                ll_attende.setVisibility(View.GONE);
                            } else if (userProfilePOJO1.getAcceptedYesNo().equals("1")) {
                                btn_track.setVisibility(View.VISIBLE);
                                ll_attende.setVisibility(View.GONE);
                            }
                        }
                    }

                } else {
                    ll_complaint_members.setVisibility(View.GONE);
                }

            }

            if (complaintPOJO.getComplaintAssigned() != null && complaintPOJO.getComplaintAssigned().size() > 0) {
                tv_leader_name.setText(complaintPOJO.getComplaintAssigned().get(0).getFirstName() + " " + complaintPOJO.getComplaintAssigned().get(0).getLastName());
                tv_leader_email.setText(complaintPOJO.getComplaintAssigned().get(0).getEmail());
                tv_bio.setText(complaintPOJO.getComplaintAssigned().get(0).getUserBio());
            }
        }

        UserProfilePOJO userProfilePOJO = complaintPOJO.getComplaintProfile();
        tv_profile_name.setText(userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName());
        Glide.with(getActivity().getApplicationContext())
                .load(userProfilePOJO.getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);

        if (complaintPOJO.getComplaintPrivacy().equals("0")) {
            tv_privacy.setText("Private");
        } else {
            tv_privacy.setText("Public");
        }

        frame_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    SearchFragment searchFragment = new SearchFragment();
                    homeActivity.replaceFragmentinFrameHome(searchFragment, "searchFragment");
                }
            }
        });


        // complaint status 1= not viewed, >1 leader taken action, -1=leader declined, 0=inactive
        try{
            int status=Integer.parseInt(complaintPOJO.getComplaintStatus());
            if(status>1){
                btn_track.setVisibility(View.VISIBLE);
            }else{
                btn_track.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    homeActivity.showComplaintTrackFragment(complaintPOJO);
                }
            }
        });
    }

    public void acceptComplaint() {
        startActivityForResult(new Intent(getActivity(), SignActivity.class), 1001);
    }

    public void rejectComplaint() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("complaint_id", complaintPOJO.getComplaintId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("success")) {
                        ComplaintPOJO complaintPOJO = new Gson().fromJson(jsonObject.optString("result").toString(), ComplaintPOJO.class);
                        ComplaintDetailFragment.this.complaintPOJO = complaintPOJO;
                        loadView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_COMPLAINT_INVITATION", true).execute(WebServicesUrls.DELETE_COMPLAINT_INVITATION);
    }

    ComplaintMembersAdapter complaintMembersAdapter;
    List<UserProfilePOJO> complaintMembersPojos = new ArrayList<>();

    public void attachAlreadyTaggedAdapter() {
        complaintMembersAdapter = new ComplaintMembersAdapter(getActivity(), this, complaintMembersPojos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_complaint_members.setHasFixedSize(true);
        rv_complaint_members.setAdapter(complaintMembersAdapter);
        rv_complaint_members.setLayoutManager(linearLayoutManager);
        rv_complaint_members.setNestedScrollingEnabled(true);
        rv_complaint_members.setItemAnimator(new DefaultItemAnimator());
    }

    List<EventAttachment> eventAttachments = new ArrayList<>();
    EventAttachAdapter eventAttachAdapter;

    public void attachAdapter() {

        EventAttachment eventAttachment = new EventAttachment("", "", "", "", true);
        eventAttachments.add(eventAttachment);

        eventAttachAdapter = new EventAttachAdapter(getActivity(), null, eventAttachments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_attachments.setHasFixedSize(true);
        rv_attachments.setAdapter(eventAttachAdapter);
        rv_attachments.setLayoutManager(linearLayoutManager);
        rv_attachments.setNestedScrollingEnabled(true);
        rv_attachments.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TagUtils.getTag(), "on activity result");
                String file_path = data.getStringExtra("result");
                try {
                    MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                    nameValuePairs.add(new BasicNameValuePair("", ""));
                    reqEntity.addPart("user_profile_id", new StringBody(Constants.userProfilePOJO.getUserProfileId()));
                    reqEntity.addPart("complaint_id", new StringBody(complaintPOJO.getComplaintId()));
                    FileBody fileBody = new FileBody(new File(file_path));
                    reqEntity.addPart("file", fileBody);

                    new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                        @Override
                        public void onGetMsg(String apicall, String response) {
                            Log.d(TagUtils.getTag(), apicall + " :- " + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                                if (jsonObject.optString("status").equals("success")) {
                                    ComplaintPOJO complaintPOJO = new Gson().fromJson(jsonObject.optString("result").toString(), ComplaintPOJO.class);
                                    ComplaintDetailFragment.this.complaintPOJO = complaintPOJO;
                                    loadView();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, "CREATE_EVENT", true).execute(WebServicesUrls.UPDATE_COMPLAINT_INVITATION);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
