package com.ritvi.kaajneeti.fragment.information;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.EventAttachAdapter;
import com.ritvi.kaajneeti.fragment.search.SearchFragment;
import com.ritvi.kaajneeti.pojo.analyze.InformationAttachmentPOJO;
import com.ritvi.kaajneeti.pojo.analyze.InformationPOJO;
import com.ritvi.kaajneeti.pojo.analyze.SuggestionAttachmentPOJO;
import com.ritvi.kaajneeti.pojo.analyze.SuggestionPOJO;
import com.ritvi.kaajneeti.pojo.event.EventAttachment;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
public class InformationViewFragment extends Fragment {

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
    @BindView(R.id.tv_information_subject)
    TextView tv_information_subject;
    @BindView(R.id.tv_information_description)
    TextView tv_information_description;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.rv_attachments)
    RecyclerView rv_attachments;
    @BindView(R.id.ll_suggestion_attachments)
    LinearLayout ll_suggestion_attachments;

    InformationPOJO informationPOJO;

    public InformationViewFragment(InformationPOJO informationPOJO) {
        this.informationPOJO = informationPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_information_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadView();
    }

    public void loadView() {
        tv_profile_name.setText(informationPOJO.getInformationProfile().getFirstName() + " " + informationPOJO.getInformationProfile().getLastName());
        attachAdapter();
        if (informationPOJO != null) {
            tv_information_subject.setText(informationPOJO.getInformationSubject());
            tv_information_description.setText(informationPOJO.getInformationDescription());
            eventAttachments.clear();
            for (InformationAttachmentPOJO informationAttachmentPOJO: informationPOJO.getInformationAttachment()) {
                String type = "";
                if (informationAttachmentPOJO.getAttachmentType().equals("photo")) {
                    type = Constants.EVENT_IMAGE_ATTACH;
                } else {
                    type = Constants.EVENT_VIDEO_ATTACH;
                }
                EventAttachment eventAttachment = new EventAttachment("", type, informationAttachmentPOJO.getAttachmentFile(), "",
                        false);
                eventAttachments.add(eventAttachment);
            }
            eventAttachAdapter.notifyDataSetChanged();

            tv_date.setText(informationPOJO.getAddedOnTime());
            UserProfilePOJO userProfilePOJO = informationPOJO.getInformationProfile();


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
