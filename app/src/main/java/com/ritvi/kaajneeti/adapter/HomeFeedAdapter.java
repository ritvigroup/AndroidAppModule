package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.SetViews;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
import com.ritvi.kaajneeti.pojo.home.EventPOJO;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;
import com.ritvi.kaajneeti.pojo.home.PollAnsPOJO;
import com.ritvi.kaajneeti.pojo.home.PollPOJO;
import com.ritvi.kaajneeti.pojo.home.PostPOJO;
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
    FragmentManager fragmentManager;

    public HomeFeedAdapter(Activity activity, Fragment fragment, List<FeedPOJO> items, FragmentManager fragmentManager) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        try {
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
        }catch (Exception e){
            e.printStackTrace();
            return super.getItemViewType(position);
        }
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

    public void inflatePollData(final PollViewHolder pollViewHolder, final PollPOJO pollPOJO, int position) {

        if(pollPOJO.getPollQuestion().length()>0) {
            pollViewHolder.tv_questions.setText(pollPOJO.getPollQuestion());
        }else{
            pollViewHolder.tv_questions.setVisibility(View.GONE);
        }

        if(pollPOJO.getPollImage().length()>0){
            Glide.with(activity.getApplicationContext())
                    .load(pollPOJO.getPollImage())
                    .placeholder(R.drawable.ic_default_pic)
                    .error(R.drawable.ic_default_pic)
                    .dontAnimate()
                    .into(pollViewHolder.iv_poll_image);
        }else{
            pollViewHolder.iv_poll_image.setVisibility(View.GONE);
        }

//        Glide.with(activity.getApplicationContext())
//                .load(pollPOJO.getProfileDetailPOJO().getProfilePhotoPath())
//                .error(R.drawable.ic_default_profile_pic)
//                .placeholder(R.drawable.ic_default_profile_pic)
//                .dontAnimate()
//                .into(pollViewHolder.cv_profile_pic);

        UserProfilePOJO userProfilePOJO = pollPOJO.getProfileDetailPOJO();
        SetViews.setProfilePhoto(activity.getApplicationContext(),userProfilePOJO.getProfilePhotoPath(),pollViewHolder.cv_profile_pic);

        String name = "";

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

        pollViewHolder.cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) activity;
                    homeActivity.showUserProfileFragment(pollPOJO.getProfileDetailPOJO().getUserId(),pollPOJO.getProfileDetailPOJO().getUserProfileId());
                }
            }
        });
        pollViewHolder.tv_profile_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pollViewHolder.cv_profile_pic.callOnClick();
            }
        });

//        for (final PollAnsPOJO pollAnsPOJO : pollPOJO.getPollAnsPOJOS()) {
//            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.inflate_poll_ans, null);
//
//            Button btn_ans = view.findViewById(R.id.btn_ans);
//            btn_ans.setTag(pollAnsPOJO.getPollAnswer());
//            btn_ans.setText(pollAnsPOJO.getPollAnswer());
//            if (pollViewHolder.ll_ans.findViewWithTag(pollAnsPOJO.getPollAnswer()) == null) {
//                pollViewHolder.ll_ans.addView(view);
//            }
//
//            btn_ans.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
//                    nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
//                    nameValuePairs.add(new BasicNameValuePair("poll_id", pollPOJO.getPollId()));
//                    nameValuePairs.add(new BasicNameValuePair("answer_id", pollAnsPOJO.getPollAnswerId()));
//                    new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
//                        @Override
//                        public void onGetMsg(String apicall, String response) {
//                            try{
//                                JSONObject jsonObject=new JSONObject(response);
//                                if(jsonObject.optString("status").equals("success")){
//                                    pollViewHolder.ll_ans_view.setVisibility(View.GONE);
//                                    pollViewHolder.ll_already_participated.setVisibility(View.VISIBLE);
//                                    pollPOJO.setMeParticipated(1);
//                                }
//
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//                    }, "CALL_POLL_ANS_API", true).execute(WebServicesUrls.SAVE_POLL_ANS);
//
//                }
//            });
//        }


        boolean is_Ans_Image=false;
        for(PollAnsPOJO pollAnsPOJO:pollPOJO.getPollAnsPOJOS()){
            if(pollAnsPOJO.getPollAnswerImage().length()>0){
                is_Ans_Image=true;
            }
        }

        if(is_Ans_Image) {
            PollFeedAnsAdapter pollFeedAnsAdapter= new PollFeedAnsAdapter(activity, null, pollPOJO.getPollAnsPOJOS());
            GridLayoutManager layoutManager= new GridLayoutManager(activity, 2);
            pollViewHolder.rv_ans.setHasFixedSize(true);
            pollViewHolder.rv_ans.setAdapter(pollFeedAnsAdapter);
            pollViewHolder.rv_ans.setLayoutManager(layoutManager);
            pollViewHolder.rv_ans.setItemAnimator(new DefaultItemAnimator());
        }else{
            PollFeedAnsAdapter pollFeedAnsAdapter= new PollFeedAnsAdapter(activity, null, pollPOJO.getPollAnsPOJOS());
            LinearLayoutManager layoutManager= new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false);
            pollViewHolder.rv_ans.setHasFixedSize(true);
            pollViewHolder.rv_ans.setAdapter(pollFeedAnsAdapter);
            pollViewHolder.rv_ans.setLayoutManager(layoutManager);
            pollViewHolder.rv_ans.setItemAnimator(new DefaultItemAnimator());
        }

        if (pollPOJO.getMeParticipated() != null) {
            if (pollPOJO.getMeParticipated() == 1) {
                pollViewHolder.ll_ans_view.setVisibility(View.GONE);
                pollViewHolder.ll_already_participated.setVisibility(View.VISIBLE);
            } else {
                pollViewHolder.ll_ans_view.setVisibility(View.VISIBLE);
                pollViewHolder.ll_already_participated.setVisibility(View.GONE);
            }
        }



    }

    public void inflateEventData(final EventViewHolder eventViewHolder, final EventPOJO eventPOJO, int position) {

        eventViewHolder.tv_name.setText(eventPOJO.getEventName());

        Glide.with(activity.getApplicationContext())
                .load(eventPOJO.getEventProfile().getProfilePhotoPath())
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
        UserProfilePOJO userProfilePOJO = eventPOJO.getEventProfile();

        if (userProfilePOJO != null) {
            if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
                    || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
                    || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
                name = userProfilePOJO.getFirstName();
            } else {
                name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
            }
        }

        eventViewHolder.ll_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) activity;
                    homeActivity.showEventViewFragment(eventPOJO);
                }
            }
        });

        eventViewHolder.cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) activity;
                    homeActivity.showUserProfileFragment(eventPOJO.getEventProfile().getUserId(),eventPOJO.getEventProfile().getUserProfileId());
                }
            }
        });
        eventViewHolder.tv_profile_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventViewHolder.cv_profile_pic.callOnClick();
            }
        });

        eventViewHolder.tv_event_date.setText(eventPOJO.getStartDate() + "-" + eventPOJO.getEndDate());
        eventViewHolder.tv_place.setText(eventPOJO.getEventLocation());
//        eventViewHolder.tv_month.setText(eventPOJO.getEveryMonth());

        eventViewHolder.tv_profile_name.setText(name);
        eventViewHolder.tv_date.setText(eventPOJO.getAddedOn());
    }

    public void inflatePostData(final PostViewHolder postViewHolder, final PostPOJO postPOJO, int position) {

        try {
            UserProfilePOJO userProfilePOJO= postPOJO.getPostProfile();
            SetViews.setProfilePhoto(activity.getApplicationContext(),userProfilePOJO.getProfilePhotoPath(),postViewHolder.cv_profile_pic);
//            Glide.with(activity.getApplicationContext())
//                    .load(userProfilePOJO.getProfilePhotoPath())
//                    .error(R.drawable.ic_default_profile_pic)
//                    .placeholder(R.drawable.ic_default_profile_pic)
//                    .dontAnimate()
//                    .into(postViewHolder.cv_profile_pic);


            if (postPOJO.getPostAttachment().size() > 0) {

//                postViewHolder.iv_feed_image.setVisibility(View.GONE);

//                ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
//
//                for (PostAttachmentPOJO postAttachmentPOJO : postPOJO.getPostAttachment()) {
//                    PostFileGroupFragment postFileGroupFragment = new PostFileGroupFragment(postAttachmentPOJO, false);
//                    adapter.addFrag(postFileGroupFragment, "");
//                }
//
//                postViewHolder.viewPager.setAdapter(adapter);
//                postViewHolder.viewPager.setVisibility(View.VISIBLE);

//
//
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

            if (userProfilePOJO != null) {
                if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
                        || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
                    name = "<b>" + postPOJO.getPostProfile().getFirstName() + "</b>";
                } else {
                    name = "<b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b>";
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
                        profile_description += "<b>" + postPOJO.getPostTag().get(0).getFirstName() + " and " + (postPOJO.getPostTag().size() - 1) + " other" + "</b>";
                    } else if (postPOJO.getPostTag().size() == 2) {
                        profile_description += "<b>" + postPOJO.getPostTag().get(0).getFirstName() + " " + postPOJO.getPostTag().get(0).getLastName() +
                                " and " + postPOJO.getPostTag().get(1).getFirstName() + " " + postPOJO.getPostTag().get(1).getLastName() + "</b>";
                    } else {
                        profile_description += "<b>" + postPOJO.getPostTag().get(0).getFirstName() + " " + postPOJO.getPostTag().get(0).getLastName() + "</b>";
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

            postViewHolder.ll_news_feed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (activity instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) activity;
                        homeActivity.showPostViewFragment(postPOJO);
                    }
                }
            });

            postViewHolder.cv_profile_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(activity instanceof HomeActivity){
                        HomeActivity homeActivity= (HomeActivity) activity;
                        homeActivity.showUserProfileFragment(postPOJO.getPostProfile().getUserId(),postPOJO.getPostProfile().getUserProfileId());
                    }
                }
            });
            postViewHolder.tv_profile_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postViewHolder.cv_profile_pic.callOnClick();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void inflateComplaintData(final ComplaintViewHolder complaintViewHolder, final ComplaintPOJO complaintPOJO, final int position) {

        Glide.with(activity.getApplicationContext())
                .load(complaintPOJO.getComplaintProfile().getProfilePhotoPath())
                .error(R.drawable.ic_default_profile_pic)
                .placeholder(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(complaintViewHolder.cv_profile_pic);

        String name = "";
        UserProfilePOJO userProfilePOJO = complaintPOJO.getComplaintProfile();

        if (userProfilePOJO != null) {
            if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
                    || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
                    || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
                name = userProfilePOJO.getFirstName();
            } else {
                name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
            }
        }

        try {
            complaintViewHolder.tv_profile_name.setText(Html.fromHtml(name));

            complaintViewHolder.tv_date.setText(complaintPOJO.getAddedOn());

            complaintViewHolder.tv_analyze.setText(complaintPOJO.getComplaintSubject());
            complaintViewHolder.tv_id.setText("CID:-" + complaintPOJO.getComplaintUniqueId());

//            if (complaintPOJO.getComplaintTypeId().equals("2")) {
//                if (!complaintPOJO.getComplaintProfile().getUserProfileId().equals(Constants.userProfilePOJO.getUserProfileId())) {
//                    boolean is_accepted = false;
//                    boolean is_declined = false;
//                    for (UserProfilePOJO complaintMemberPOJO : complaintPOJO.getComplaintMemberPOJOS()) {
//                        if (complaintMemberPOJO.getUserProfileId().equals(Constants.userProfilePOJO.getUserProfileId())) {
//                            if (complaintMemberPOJO.getAcceptedYesNo().equals("1")) {
//                                is_accepted = true;
//                            } else if (complaintMemberPOJO.getAcceptedYesNo().equals("-1")) {
//                                is_declined = true;
//                            }
//                        }
//                    }
//                    if (is_accepted) {
//                        complaintViewHolder.ll_acceptdecline.setVisibility(View.GONE);
//                        complaintViewHolder.tv_accepted.setText("You accepted the complaint request");
//                    } else if (is_declined) {
//                        complaintViewHolder.ll_acceptdecline.setVisibility(View.GONE);
//                        complaintViewHolder.tv_accepted.setText("You declined the complaint request");
//                    } else {
//                        complaintViewHolder.ll_accepted.setVisibility(View.GONE);
//                    }
//                } else {
//                    complaintViewHolder.ll_acceptdecline.setVisibility(View.GONE);
//                }
//            } else {
                complaintViewHolder.ll_acceptdecline.setVisibility(View.GONE);
//            }

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
//                    Intent intent = new Intent(activity, ViewComplaintActivity.class);
//                    Intent intent = new Intent(activity, ComplaintDetailActivity.class);
////                    intent.putExtra("complaint", complaintPOJO);
//                    intent.putExtra("complaintPOJO", complaintPOJO);
//                    activity.startActivity(intent);
                    if (activity instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) activity;
                        homeActivity.showComplaintDescription(complaintPOJO);
                    }
                }
            });

            complaintViewHolder.iv_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(activity, ComplaintDetailActivity.class);
//                    intent.putExtra("complaintPOJO", complaintPOJO);
//                    activity.startActivity(intent);
                    complaintViewHolder.ll_analyze.callOnClick();
                }
            });

            complaintViewHolder.cv_profile_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(activity instanceof HomeActivity){
                        HomeActivity homeActivity= (HomeActivity) activity;
                        homeActivity.showUserProfileFragment(complaintPOJO.getComplaintProfile().getUserId(),complaintPOJO.getComplaintProfile().getUserProfileId());
                    }
                }
            });
            complaintViewHolder.tv_profile_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    complaintViewHolder.cv_profile_pic.callOnClick();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        public TextView tv_participated;
        public CircleImageView cv_profile_pic;
        public ImageView iv_poll_image;
        public LinearLayout ll_ans;
        public LinearLayout ll_ans_view;
        public LinearLayout ll_already_participated;
        public RecyclerView rv_ans;

        public PollViewHolder(View itemView) {
            super(itemView);
            tv_questions = itemView.findViewById(R.id.tv_questions);
            ll_ans = itemView.findViewById(R.id.ll_ans);
            ll_ans_view = itemView.findViewById(R.id.ll_ans_view);
            ll_already_participated = itemView.findViewById(R.id.ll_already_participated);
            tv_profile_name = itemView.findViewById(R.id.tv_profile_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            iv_poll_image = itemView.findViewById(R.id.iv_poll_image);
            tv_participated = itemView.findViewById(R.id.tv_participated);
            rv_ans = itemView.findViewById(R.id.rv_ans);
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
        public LinearLayout ll_event;

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
            ll_event = itemView.findViewById(R.id.ll_event);
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_profile_name;
        public TextView tv_date;
        public ImageView iv_feed_image;
        public TextView tv_description;
        public CircleImageView cv_profile_pic;
        public ViewPager viewPager;
        public LinearLayout ll_news_feed;

        public PostViewHolder(View itemView) {
            super(itemView);
            tv_profile_name = itemView.findViewById(R.id.tv_profile_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_feed_image = itemView.findViewById(R.id.iv_feed_image);
            tv_description = itemView.findViewById(R.id.tv_description);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            viewPager = itemView.findViewById(R.id.viewPager);
            ll_news_feed = itemView.findViewById(R.id.ll_news_feed);
        }
    }

    class ComplaintViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_profile_name;
        public TextView tv_date;
        public CircleImageView cv_profile_pic;
        public TextView tv_analyze, tv_id, tv_accepted;
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
            tv_analyze = itemView.findViewById(R.id.tv_analyze);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_accepted = itemView.findViewById(R.id.tv_accepted);
            ll_analyze = itemView.findViewById(R.id.ll_analyze);
            ll_accept = itemView.findViewById(R.id.ll_accept);
            ll_decline = itemView.findViewById(R.id.ll_decline);
            ll_accepted = itemView.findViewById(R.id.ll_accepted);
            ll_acceptdecline = itemView.findViewById(R.id.ll_acceptdecline);
            iv_info = itemView.findViewById(R.id.iv_info);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
