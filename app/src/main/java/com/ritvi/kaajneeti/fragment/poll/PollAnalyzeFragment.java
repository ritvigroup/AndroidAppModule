package com.ritvi.kaajneeti.fragment.poll;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.SetViews;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.PollFeedAnalyzeAdapter;
import com.ritvi.kaajneeti.adapter.PollFeedAnsAdapter;
import com.ritvi.kaajneeti.interfaces.PollAnsClickInterface;
import com.ritvi.kaajneeti.pojo.home.PollAnsPOJO;
import com.ritvi.kaajneeti.pojo.home.PollPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
public class PollAnalyzeFragment extends Fragment {

    @BindView(R.id.tv_questions)
    TextView tv_questions;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.iv_poll_image)
    ImageView iv_poll_image;
    @BindView(R.id.iv_poll_menu)
    ImageView iv_poll_menu;
    @BindView(R.id.rv_ans)
    RecyclerView rv_ans;
    @BindView(R.id.tv_total_participation)
    TextView tv_total_participation;

    PollPOJO pollPOJO;

    public PollAnalyzeFragment(PollPOJO pollPOJO) {
        this.pollPOJO = pollPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_poll_analyze, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (pollPOJO.getPollQuestion().length() > 0) {
            tv_questions.setText(pollPOJO.getPollQuestion());
        } else {
            tv_questions.setVisibility(View.GONE);
        }

        if (pollPOJO.getPollImage().length() > 0) {
            Glide.with(getActivity().getApplicationContext())
                    .load(pollPOJO.getPollImage())
                    .placeholder(R.drawable.ic_default_pic)
                    .error(R.drawable.ic_default_pic)
                    .dontAnimate()
                    .into(iv_poll_image);
        } else {
            iv_poll_image.setVisibility(View.GONE);
        }

        UserProfilePOJO userProfilePOJO = pollPOJO.getProfileDetailPOJO();
        SetViews.setProfilePhoto(getActivity().getApplicationContext(), userProfilePOJO.getProfilePhotoPath(), cv_profile_pic);

        String name = "";

        name = "<b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b>";

        tv_profile_name.setText(Html.fromHtml(name));
        tv_date.setText(pollPOJO.getAddedOn());

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homegetActivity = (HomeActivity) getActivity();
                    homegetActivity.showUserProfileFragment(pollPOJO.getProfileDetailPOJO().getUserId(), pollPOJO.getProfileDetailPOJO().getUserProfileId());
                }
            }
        });
        tv_profile_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cv_profile_pic.callOnClick();
            }
        });

        boolean is_Ans_Image = false;
        for (PollAnsPOJO pollAnsPOJO : pollPOJO.getPollAnsPOJOS()) {
            if (pollAnsPOJO.getPollAnswerImage().length() > 0) {
                is_Ans_Image = true;
            }
        }
        PollFeedAnalyzeAdapter pollFeedAnsAdapter = new PollFeedAnalyzeAdapter(getActivity(), null, pollPOJO);
        if (is_Ans_Image) {
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            rv_ans.setLayoutManager(layoutManager);
            rv_ans.setHasFixedSize(true);
            rv_ans.setAdapter(pollFeedAnsAdapter);
            rv_ans.setItemAnimator(new DefaultItemAnimator());
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv_ans.setLayoutManager(layoutManager);
            rv_ans.setHasFixedSize(true);
            rv_ans.setAdapter(pollFeedAnsAdapter);
            rv_ans.setItemAnimator(new DefaultItemAnimator());
        }

        iv_poll_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(getActivity(), view);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        return false;
                    }
                });
                if (pollPOJO.getProfileDetailPOJO().getUserProfileId().equalsIgnoreCase(Constants.userProfilePOJO.getUserProfileId())) {
                    menu.inflate(R.menu.menu_my_feed);
                } else {
                    menu.inflate(R.menu.menu_friend_feed);
                }
                menu.show();
            }
        });

        tv_total_participation.setText(String.valueOf(pollPOJO.getPollTotalParticipation())+" people participated in this poll ");

    }
}
