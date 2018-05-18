package com.ritvi.kaajneeti.fragment.suggestion;

import android.annotation.SuppressLint;
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
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.activity.SignActivity;
import com.ritvi.kaajneeti.adapter.ComplaintMembersAdapter;
import com.ritvi.kaajneeti.adapter.EventAttachAdapter;
import com.ritvi.kaajneeti.fragment.complaint.ComplaintDetailFragment;
import com.ritvi.kaajneeti.fragment.search.SearchFragment;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintAttachmentPOJO;
import com.ritvi.kaajneeti.pojo.analyze.SuggestionAttachmentPOJO;
import com.ritvi.kaajneeti.pojo.analyze.SuggestionPOJO;
import com.ritvi.kaajneeti.pojo.event.EventAttachment;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
public class SuggestionViewFragment extends Fragment {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.frame_search)
    FrameLayout frame_search;

    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    @BindView(R.id.tv_suggestion_subject)
    TextView tv_suggestion_subject;
    @BindView(R.id.tv_suggestion_description)
    TextView tv_suggestion_description;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_leader_name)
    TextView tv_leader_name;
    @BindView(R.id.tv_leader_email)
    TextView tv_leader_email;
    @BindView(R.id.tv_bio)
    TextView tv_bio;
    @BindView(R.id.ll_suggestion_members)
    LinearLayout ll_suggestion_members;
    @BindView(R.id.rv_attachments)
    RecyclerView rv_attachments;
    @BindView(R.id.ll_suggestion_attachments)
    LinearLayout ll_suggestion_attachments;

    SuggestionPOJO suggestionPOJO;

    public SuggestionViewFragment(SuggestionPOJO suggestionPOJO) {
        this.suggestionPOJO = suggestionPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_suggestion_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadView();
    }

    public void loadView() {
        tv_profile_name.setText(suggestionPOJO.getSuggestionProfile().getFirstName()+" "+suggestionPOJO.getSuggestionProfile().getLastName());
        attachAdapter();
        if (suggestionPOJO != null) {
            tv_suggestion_subject.setText(suggestionPOJO.getSuggestionSubject());
            tv_suggestion_description.setText(suggestionPOJO.getSuggestionDescription());
            eventAttachments.clear();
            for (SuggestionAttachmentPOJO suggestionAttachmentPOJO : suggestionPOJO.getSuggestionAttachment()) {
                String type = "";
                if (suggestionAttachmentPOJO.getAttachmentType().equals("photo")) {
                    type = Constants.EVENT_IMAGE_ATTACH;
                } else {
                    type = Constants.EVENT_VIDEO_ATTACH;
                }
                EventAttachment eventAttachment = new EventAttachment("", type, suggestionAttachmentPOJO.getAttachmentFile(), "",
                        false);
                eventAttachments.add(eventAttachment);
            }
            eventAttachAdapter.notifyDataSetChanged();

            tv_location.setText(suggestionPOJO.getApplicantAddress()+ " , " + suggestionPOJO.getApplicantPlace());
            tv_date.setText(suggestionPOJO.getAddedOnTime());
            UserProfilePOJO userProfilePOJO = suggestionPOJO.getSuggestionProfile();


            if ( suggestionPOJO.getSuggestionAssignedList()!=null&&suggestionPOJO.getSuggestionAssignedList().size() > 0) {
                tv_leader_name.setText(suggestionPOJO.getSuggestionAssignedList().get(0).getFirstName() + " " + suggestionPOJO.getSuggestionAssignedList().get(0).getLastName());
                tv_leader_email.setText(suggestionPOJO.getSuggestionAssignedList().get(0).getEmail());
                tv_bio.setText(suggestionPOJO.getSuggestionAssignedList().get(0).getUserBio());
            }
            tv_profile_name.setText(userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName());
            Glide.with(getActivity().getApplicationContext())
                    .load(userProfilePOJO.getProfilePhotoPath())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);

            tv_privacy.setVisibility(View.GONE);
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

}
