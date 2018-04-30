package com.ritvi.kaajneeti.fragment.complaint;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.EventAttachAdapter;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintAttachmentPOJO;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
import com.ritvi.kaajneeti.pojo.event.EventAttachment;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

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
    @BindView(R.id.tv_father_name)
    TextView tv_father_name;
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

            if (complaintPOJO.getComplaintProfile().getUserId().equals(Constants.userProfilePOJO.getUserId())) {
                ll_attende.setVisibility(View.GONE);
            } else {
                ll_attende.setVisibility(View.VISIBLE);
            }

            tv_location.setText(complaintPOJO.getComplaintAddress() + " , " + complaintPOJO.getComplaintPlace());
            tv_date.setText(complaintPOJO.getAddedOnTime());
            UserProfilePOJO userProfilePOJO = complaintPOJO.getComplaintProfile();
            if (userProfilePOJO != null) {
                tv_name.setText(userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName());
                tv_mobile_number.setText(userProfilePOJO.getMobile());
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
                    homeActivity.showSearchFragment();
                }
            }
        });


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
        rv_attachments.setItemAnimator(new DefaultItemAnimator());
    }


}
