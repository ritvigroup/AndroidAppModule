package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.ComplaintDetailActivity;
import com.ritvi.kaajneeti.activity.ViewComplaintActivity;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
import com.ritvi.kaajneeti.pojo.home.EventPOJO;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;
import com.ritvi.kaajneeti.pojo.home.PollAnsPOJO;
import com.ritvi.kaajneeti.pojo.home.PollPOJO;
import com.ritvi.kaajneeti.pojo.home.PostPOJO;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class HomeFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FeedPOJO> items;
    Activity activity;
    Fragment fragment;

    public HomeFeedAdapter(Activity activity, Fragment fragment, List<FeedPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public int getItemViewType(int position) {
        switch (items.get(position).getFeedtype()) {
            case "poll":
                return 0;
            case "event":
                return 1;
            case "post":
                return 2;
            case "complaint":
                return 3;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_poll_feed, parent, false);
                return new PollViewHolder(v);
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_event_feed, parent, false);
                return new EventViewHolder(v);
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_news_feeds, parent, false);
                return new PostViewHolder(v);
            case 3:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_complaint_feed, parent, false);
                return new ComplaintViewHolder(v);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (items.get(position).getFeedtype()) {
            case "poll":
                PollViewHolder pollViewHolder = (PollViewHolder) holder;
                inflatePollData(pollViewHolder, items.get(position).getPollPOJO(), position);
                break;
            case "event":
                EventViewHolder eventViewHolder = (EventViewHolder) holder;
                inflateEventData(eventViewHolder, items.get(position).getEventPOJO(), position);
                break;
            case "post":
                PostViewHolder postViewHolder = (PostViewHolder) holder;
                inflatePostData(postViewHolder, items.get(position).getPostPOJO(), position);
                break;
            case "complaint":
                ComplaintViewHolder complaintViewHolder = (ComplaintViewHolder) holder;
                inflateComplaintData(complaintViewHolder, items.get(position).getComplaintPOJO(), position);
                break;
        }

        holder.itemView.setTag(items.get(position));

    }

    public void inflatePollData(PollViewHolder pollViewHolder, PollPOJO pollPOJO, int position) {
        pollViewHolder.tv_questions.setText(pollPOJO.getPollQuestion());

        Glide.with(activity.getApplicationContext())
                .load(pollPOJO.getProfileDetailPOJO().getUserProfileDetailPOJO().getUserInfoPOJO().getProfilePhotoPath())
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(pollViewHolder.cv_profile_pic);

        String name = "";
        UserProfilePOJO userProfilePOJO = pollPOJO.getProfileDetailPOJO().getUserProfileDetailPOJO().getUserProfilePOJO();

        if (userProfilePOJO != null) {
            if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
                    || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
                    || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
                name = userProfilePOJO.getFirstName();
            } else {
                name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
            }
        }

        pollViewHolder.tv_profile_name.setText(name);
        pollViewHolder.tv_date.setText(pollPOJO.getAddedOn());

        for (PollAnsPOJO pollAnsPOJO : pollPOJO.getPollAnsPOJOS()) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_poll_ans, null);

            Button btn_ans = view.findViewById(R.id.btn_ans);
            btn_ans.setTag(pollAnsPOJO.getPollAnswer());
            btn_ans.setText(pollAnsPOJO.getPollAnswer());
            if (pollViewHolder.ll_ans.findViewWithTag(pollAnsPOJO.getPollAnswer()) == null) {
                pollViewHolder.ll_ans.addView(view);
            }
        }

    }

    public void inflateEventData(EventViewHolder eventViewHolder, EventPOJO eventPOJO, int position) {

        eventViewHolder.tv_name.setText(eventPOJO.getEventName());

        Glide.with(activity.getApplicationContext())
                .load(eventPOJO.getEventProfile().getUserProfileDetailPOJO().getUserInfoPOJO().getProfilePhotoPath())
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(eventViewHolder.cv_profile_pic);

        if (eventPOJO.getEventAttachment().size() > 0) {
            Glide.with(activity.getApplicationContext())
                    .load(eventPOJO.getEventAttachment().get(0).getAttachmentFile())
                    .error(R.drawable.ic_default_profile_pic)
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(eventViewHolder.iv_event_image);
        }

        String name = "";
        UserProfilePOJO userProfilePOJO = eventPOJO.getEventProfile().getUserProfileDetailPOJO().getUserProfilePOJO();

        if (userProfilePOJO != null) {
            if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
                    || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
                    || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
                name = userProfilePOJO.getFirstName();
            } else {
                name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
            }
        }

        eventViewHolder.tv_event_date.setText(eventPOJO.getStartDate() + "-" + eventPOJO.getEndDate());
        eventViewHolder.tv_place.setText(eventPOJO.getEventLocation());
//        eventViewHolder.tv_month.setText(eventPOJO.getEveryMonth());

        eventViewHolder.tv_profile_name.setText(name);
        eventViewHolder.tv_date.setText(eventPOJO.getAddedOn());
    }

    public void inflatePostData(PostViewHolder postViewHolder, PostPOJO postPOJO, int position) {


        Glide.with(activity.getApplicationContext())
                .load(postPOJO.getPostProfile().getUserProfileDetailPOJO().getUserInfoPOJO().getProfilePhotoPath())
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(postViewHolder.cv_profile_pic);

        if (postPOJO.getPostAttachment().size() > 0) {
            Glide.with(activity.getApplicationContext())
                    .load(postPOJO.getPostAttachment().get(0).getAttachmentFile())
                    .error(R.drawable.ic_default_profile_pic)
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(postViewHolder.iv_feed_image);
        } else {
            postViewHolder.iv_feed_image.setVisibility(View.GONE);
        }

        String name = "";
        UserProfilePOJO userProfilePOJO = postPOJO.getPostProfile().getUserProfileDetailPOJO().getUserProfilePOJO();

        if (userProfilePOJO != null) {
            if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
                    || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
                    || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
                name = userProfilePOJO.getFirstName();
            } else {
                name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
            }
        }

        String profile_description = "";
        boolean containDescribe = false;
        if (postPOJO.getPostTag().size() > 0) {
            containDescribe = true;
        }

        if (postPOJO.getFeelingDataPOJOS().size() > 0) {
            containDescribe = true;
        }

        if (containDescribe) {
            profile_description += " is ";

            if (postPOJO.getFeelingDataPOJOS().size() > 0) {
                profile_description += "<b>feeling " + postPOJO.getFeelingDataPOJOS().get(0).getFeelingName() + "</b>";
            }

            if (postPOJO.getPostTag().size() > 0) {
                profile_description += " with ";
                if (postPOJO.getPostTag().size() > 2) {
                    profile_description += "<b>" + postPOJO.getPostTag().get(0).getUserProfileDetailPOJO().getUserProfilePOJO().getFirstName() + " and " + (postPOJO.getPostTag().size() - 1) + " other" + "</b>";
                } else if (postPOJO.getPostTag().size() == 2) {
                    profile_description += "<b>" + postPOJO.getPostTag().get(0).getUserProfileDetailPOJO().getUserProfilePOJO().getFirstName() + " " + postPOJO.getPostTag().get(0).getUserProfileDetailPOJO().getUserProfilePOJO().getLastName() +
                            " and " + postPOJO.getPostTag().get(1).getUserProfileDetailPOJO().getUserProfilePOJO().getFirstName() + " " + postPOJO.getPostTag().get(1).getUserProfileDetailPOJO().getUserProfilePOJO().getLastName() + "</b>";
                } else {
                    profile_description += "<b>" + postPOJO.getPostTag().get(0).getUserProfileDetailPOJO().getUserProfilePOJO().getFirstName() + " " + postPOJO.getPostTag().get(0).getUserProfileDetailPOJO().getUserProfilePOJO().getLastName() + "</b>";
                }
            }
        }

        postViewHolder.tv_profile_name.setText(Html.fromHtml(name + " " + profile_description));
        if (!postPOJO.getPostDescription().equalsIgnoreCase("")) {
            postViewHolder.tv_description.setText(postPOJO.getPostDescription());
        } else {
            postViewHolder.tv_description.setVisibility(View.GONE);
        }
        postViewHolder.tv_date.setText(postPOJO.getAddedOn());
    }


    public void inflateComplaintData(ComplaintViewHolder complaintViewHolder, final ComplaintPOJO complaintPOJO, final int position) {

        Glide.with(activity.getApplicationContext())
                .load(complaintPOJO.getComplaintProfile().getUserProfileDetailPOJO().getUserInfoPOJO().getProfilePhotoPath())
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(complaintViewHolder.cv_profile_pic);

        String name = "";
        UserProfilePOJO userProfilePOJO = complaintPOJO.getComplaintProfile().getUserProfileDetailPOJO().getUserProfilePOJO();

        if (userProfilePOJO != null) {
            if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
                    || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
                    || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
                name = userProfilePOJO.getFirstName();
            } else {
                name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
            }
        }


        complaintViewHolder.tv_profile_name.setText(Html.fromHtml(name));

        complaintViewHolder.tv_date.setText(complaintPOJO.getAddedOn());

        complaintViewHolder.tv_analyze.setText(complaintPOJO.getComplaintSubject());
        complaintViewHolder.tv_id.setText("CID:-"+complaintPOJO.getComplaintUniqueId());

        if(complaintPOJO.getComplaintTypeId().equals("2")){
            if(!UtilityFunction.getUserProfilePOJO(complaintPOJO.getComplaintProfile().getUserProfileDetailPOJO().getUserInfoPOJO()).getUserProfileId().equals(UtilityFunction.getUserProfilePOJO(Constants.userInfoPOJO).getUserProfileId())){
                boolean is_accepted=false;
                boolean is_declined=false;
                for(OutGoingRequestPOJO complaintMemberPOJO:complaintPOJO.getComplaintMemberPOJOS()){
                    if(UtilityFunction.getUserProfilePOJO(complaintMemberPOJO.getUserProfileDetailPOJO().getUserInfoPOJO()).getUserProfileId().equals(UtilityFunction.getProfileID(Constants.userInfoPOJO))){
                        if(complaintMemberPOJO.getAcceptedYesNo().equals("1")){
                            is_accepted=true;
                        }else if(complaintMemberPOJO.getAcceptedYesNo().equals("-1")){
                            is_declined=true;
                        }
                    }
                }
                if(is_accepted){
                    complaintViewHolder.ll_acceptdecline.setVisibility(View.GONE);
                    complaintViewHolder.tv_accepted.setText("You accepted the complaint request");
                }else if(is_declined){
                    complaintViewHolder.ll_acceptdecline.setVisibility(View.GONE);
                    complaintViewHolder.tv_accepted.setText("You declined the complaint request");
                }else{
                    complaintViewHolder.ll_accepted.setVisibility(View.GONE);
                }
            }else{
                complaintViewHolder.ll_acceptdecline.setVisibility(View.GONE);
            }
        }else{
            complaintViewHolder.ll_acceptdecline.setVisibility(View.GONE);
        }

//        complaintViewHolder.ll_decline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showAcceptDeclintDialog("Do you want to decline the Complaint Request",0,items.get(position).getComplaintId());
//            }
//        });
//
//        complaintViewHolder.ll_accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showAcceptDeclintDialog("Do you want to accept the Complaint Request",1,items.get(position).getComplaintId());
//            }
//        });

        complaintViewHolder.ll_analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ViewComplaintActivity.class);
                intent.putExtra("complaint",complaintPOJO);
                activity.startActivity(intent);
            }
        });

        complaintViewHolder.iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ComplaintDetailActivity.class);
                intent.putExtra("complaintPOJO",complaintPOJO);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_description;
        public LinearLayout ll_analyze;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
        }
    }

    class PollViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_questions;
        public TextView tv_profile_name;
        public TextView tv_date;
        public CircleImageView cv_profile_pic;
        public LinearLayout ll_ans;

        public PollViewHolder(View itemView) {
            super(itemView);
            tv_questions = itemView.findViewById(R.id.tv_questions);
            ll_ans = itemView.findViewById(R.id.ll_ans);
            tv_profile_name = itemView.findViewById(R.id.tv_profile_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
        }
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_profile_name;
        public TextView tv_date;
        public ImageView iv_event_image;
        public ImageView cv_profile_pic;
        public TextView tv_event_date;
        public TextView tv_place;
        public TextView tv_month;
        public TextView tv_day;

        public EventViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_profile_name = itemView.findViewById(R.id.tv_profile_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_event_image = itemView.findViewById(R.id.iv_event_image);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            tv_event_date = itemView.findViewById(R.id.tv_event_date);
            tv_place = itemView.findViewById(R.id.tv_place);
            tv_month = itemView.findViewById(R.id.tv_month);
            tv_day = itemView.findViewById(R.id.tv_day);
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_profile_name;
        public TextView tv_date;
        public ImageView iv_feed_image;
        public TextView tv_description;
        public CircleImageView cv_profile_pic;

        public PostViewHolder(View itemView) {
            super(itemView);
            tv_profile_name = itemView.findViewById(R.id.tv_profile_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_feed_image = itemView.findViewById(R.id.iv_feed_image);
            tv_description = itemView.findViewById(R.id.tv_description);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
        }
    }

    class ComplaintViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_profile_name;
        public TextView tv_date;
        public CircleImageView cv_profile_pic;
        public TextView tv_analyze,tv_id,tv_accepted;
        public LinearLayout ll_analyze;
        public LinearLayout ll_decline;
        public LinearLayout ll_accept;
        public LinearLayout ll_accepted;
        public LinearLayout ll_acceptdecline;
        public ImageView iv_info;

        public ComplaintViewHolder(View itemView) {
            super(itemView);
            tv_profile_name = itemView.findViewById(R.id.tv_profile_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            tv_analyze=itemView.findViewById(R.id.tv_analyze);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_accepted=itemView.findViewById(R.id.tv_accepted);
            ll_analyze=itemView.findViewById(R.id.ll_analyze);
            ll_accept=itemView.findViewById(R.id.ll_accept);
            ll_decline=itemView.findViewById(R.id.ll_decline);
            ll_accepted=itemView.findViewById(R.id.ll_accepted);
            ll_acceptdecline=itemView.findViewById(R.id.ll_acceptdecline);
            iv_info=itemView.findViewById(R.id.iv_info);
        }
    }

}
