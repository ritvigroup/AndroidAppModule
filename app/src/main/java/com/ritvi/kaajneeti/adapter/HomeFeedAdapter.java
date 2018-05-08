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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.SetViews;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.fragment.complaint.ComplaintDetailFragment;
import com.ritvi.kaajneeti.fragment.post.EventViewFragment;
import com.ritvi.kaajneeti.fragment.post.PostViewFragment;
import com.ritvi.kaajneeti.interfaces.OnLoadMoreListener;
import com.ritvi.kaajneeti.interfaces.PollAnsClickInterface;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
import com.ritvi.kaajneeti.pojo.home.EventPOJO;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;
import com.ritvi.kaajneeti.pojo.home.PollAnsPOJO;
import com.ritvi.kaajneeti.pojo.home.PollPOJO;
import com.ritvi.kaajneeti.pojo.home.PostPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
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
    RecyclerView recyclerView;

    private boolean isLoading;
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener onLoadMoreListener;

    public HomeFeedAdapter(RecyclerView recyclerView, Activity activity, Fragment fragment, List<FeedPOJO> items, FragmentManager fragmentManager) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        this.recyclerView = recyclerView;
        setHasStableIds(true);

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (items.get(position).getFeedtype() != null) {
                switch (items.get(position).getFeedtype()) {
                    case "poll":
                        return 0;
                    case "event":
                        return 1;
                    case "post":
                        return 2;
                    case "complaint":
                        return 3;
                    case "profile":
                        return 4;
                }
                return super.getItemViewType(position);
            } else {
                return 101;
            }
        } catch (Exception e) {
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
            case 4:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_search_user_profile, parent, false);
                return new UserProfileViewHolder(v);
            case 101:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_item_loading, parent, false);
                return new LoadingViewHolder(v);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (items.get(position).getFeedtype() == null) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        } else {
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
                case "profile":
                    UserProfileViewHolder userProfileViewHolder = (UserProfileViewHolder) holder;
                    inflateUserData(userProfileViewHolder, items.get(position).getProfiledata(), position);
                    break;
            }
        }

        holder.itemView.setTag(items.get(position));

    }

    public void inflateUserData(final UserProfileViewHolder userProfileViewHolder, final UserProfilePOJO userProfilePOJO, final int position) {
        userProfileViewHolder.tv_user_name.setText(userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName());
        Glide.with(activity.getApplicationContext())
                .load(userProfilePOJO.getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(userProfileViewHolder.cv_profile_pic);

        switch (userProfilePOJO.getMyFriend()) {
            case 0:
                userProfileViewHolder.tv_add_friend.setText("Add Friend");
                break;
            case 1:
                userProfileViewHolder.tv_add_friend.setText("Cancel Friend Request");
                break;
            case 2:
                userProfileViewHolder.tv_add_friend.setText("Accept Request");
                break;
            case 3:
                userProfileViewHolder.tv_add_friend.setText("Remove Friend");
                break;
            case 4:
                userProfileViewHolder.tv_add_friend.setText("Follow");
                break;
        }

        userProfileViewHolder.tv_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (userProfilePOJO.getMyFriend()) {
                    case 0:
//                        holder.tv_add_friend.setText("Add Friend");
                        sendFriendRequest(userProfilePOJO, userProfileViewHolder.tv_add_friend);
                        break;
                    case 1:
                        undoFriendRequest(userProfilePOJO, userProfileViewHolder.tv_add_friend);
//                        holder.tv_add_friend.setText("Friend Request Sent");
                        break;
                    case 2:
//                        holder.tv_add_friend.setText("Accept Request");
                        acceptRequest(userProfilePOJO, position);
                        break;
                    case 3:
//                        holder.tv_add_friend.setText("");
                        cancelFriendRequest(userProfilePOJO, userProfileViewHolder.tv_add_friend);
                        break;
                }

            }
        });

        userProfileViewHolder.ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) activity;
                    homeActivity.showUserProfileFragment(userProfilePOJO.getUserId(), userProfilePOJO.getUserProfileId());
                }
            }
        });
    }


    public void sendFriendRequest(final UserProfilePOJO userProfilePOJO, TextView textView) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                userProfilePOJO.setMyFriend(1);
                notifyDataSetChanged();
            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.SEND_FRIEND_REQUEST);
    }

    public void undoFriendRequest(final UserProfilePOJO userProfilePOJO, TextView textView) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                userProfilePOJO.setMyFriend(0);
                notifyDataSetChanged();
            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.UNDO_FRIEND_REQUEST);
    }

    public void cancelFriendRequest(final UserProfilePOJO userProfilePOJO, TextView textView) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                userProfilePOJO.setMyFriend(4);
                notifyDataSetChanged();
            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.CANCEL_FRIEND_REQUEST);
    }

    public void acceptRequest(final UserProfilePOJO userProfilePOJO, final int position) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                items.remove(position);
                notifyDataSetChanged();
            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.SEND_FRIEND_REQUEST);
    }


    public void inflatePollData(final PollViewHolder pollViewHolder, final PollPOJO pollPOJO, int position) {

        if (pollPOJO.getPollQuestion().length() > 0) {
            pollViewHolder.tv_questions.setText(pollPOJO.getPollQuestion());
        } else {
            pollViewHolder.tv_questions.setVisibility(View.GONE);
        }

        if (pollPOJO.getPollImage().length() > 0) {
            Glide.with(activity.getApplicationContext())
                    .load(pollPOJO.getPollImage())
                    .placeholder(R.drawable.ic_default_pic)
                    .error(R.drawable.ic_default_pic)
                    .dontAnimate()
                    .into(pollViewHolder.iv_poll_image);
        } else {
            pollViewHolder.iv_poll_image.setVisibility(View.GONE);
        }

        UserProfilePOJO userProfilePOJO = pollPOJO.getProfileDetailPOJO();
        SetViews.setProfilePhoto(activity.getApplicationContext(), userProfilePOJO.getProfilePhotoPath(), pollViewHolder.cv_profile_pic);

        String name = "";

        name = "<b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b>";

        pollViewHolder.tv_profile_name.setText(Html.fromHtml(name));
        pollViewHolder.tv_date.setText(pollPOJO.getAddedOn());

        pollViewHolder.cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) activity;
                    homeActivity.showUserProfileFragment(pollPOJO.getProfileDetailPOJO().getUserId(), pollPOJO.getProfileDetailPOJO().getUserProfileId());
                }
            }
        });
        pollViewHolder.tv_profile_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pollViewHolder.cv_profile_pic.callOnClick();
            }
        });

        boolean is_Ans_Image = false;
        for (PollAnsPOJO pollAnsPOJO : pollPOJO.getPollAnsPOJOS()) {
            if (pollAnsPOJO.getPollAnswerImage().length() > 0) {
                is_Ans_Image = true;
            }
        }
        PollFeedAnsAdapter pollFeedAnsAdapter = new PollFeedAnsAdapter(activity, null, pollPOJO.getPollAnsPOJOS());
        if (is_Ans_Image) {
            GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
            pollViewHolder.rv_ans.setLayoutManager(layoutManager);
            pollViewHolder.rv_ans.setHasFixedSize(true);
            pollViewHolder.rv_ans.setAdapter(pollFeedAnsAdapter);
            pollViewHolder.rv_ans.setItemAnimator(new DefaultItemAnimator());
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
            pollViewHolder.rv_ans.setLayoutManager(layoutManager);
            pollViewHolder.rv_ans.setHasFixedSize(true);
            pollViewHolder.rv_ans.setAdapter(pollFeedAnsAdapter);
            pollViewHolder.rv_ans.setItemAnimator(new DefaultItemAnimator());
        }

        pollFeedAnsAdapter.setOnAnsClicked(new PollAnsClickInterface() {
            @Override
            public void onAnsclicked(String ans_id) {
                Log.d(TagUtils.getTag(), "poll ans clicked:-" + ans_id);
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
                nameValuePairs.add(new BasicNameValuePair("poll_id", pollPOJO.getPollId()));
                nameValuePairs.add(new BasicNameValuePair("answer_id", ans_id));
                new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                                pollPOJO.setMeParticipated(1);
                                pollViewHolder.rv_ans.setVisibility(View.GONE);
                                pollViewHolder.ll_already_participated.setVisibility(View.VISIBLE);
                                ToastClass.showShortToast(activity.getApplicationContext(), "Thanks for you participation");
                            } else {
                                ToastClass.showShortToast(activity.getApplicationContext(), jsonObject.optString("message"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "POLL_ANSWERED", true).execute(WebServicesUrls.SAVE_POLL_ANS);
            }
        });

        if (pollPOJO.getMeParticipated() != null) {
            if (pollPOJO.getMeParticipated() == 1) {
                pollViewHolder.rv_ans.setVisibility(View.GONE);
                pollViewHolder.ll_already_participated.setVisibility(View.VISIBLE);
            } else {
                pollViewHolder.rv_ans.setVisibility(View.VISIBLE);
                pollViewHolder.ll_already_participated.setVisibility(View.GONE);
            }
        }

        pollViewHolder.iv_poll_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(activity, view);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        return false;
                    }
                });
                if(pollPOJO.getProfileDetailPOJO().getUserProfileId().equalsIgnoreCase(Constants.userProfilePOJO.getUserProfileId())) {
                    menu.inflate(R.menu.menu_my_feed);
                }else{
                    menu.inflate(R.menu.menu_friend_feed);
                }
                menu.show();
            }
        });


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

//        if (userProfilePOJO != null) {
//            if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
//                    || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
//                    || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
//                name = userProfilePOJO.getFirstName();
//            } else {
//                name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
//            }
//        }

        name = "<b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b>";

        eventViewHolder.ll_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) activity;
                    EventViewFragment eventViewFragment = new EventViewFragment(eventPOJO);
                    homeActivity.addFragmentinFrameHome(eventViewFragment, "eventViewFragment");
                }
            }
        });

        eventViewHolder.cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) activity;
                    homeActivity.showUserProfileFragment(eventPOJO.getEventProfile().getUserId(), eventPOJO.getEventProfile().getUserProfileId());
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

        eventViewHolder.tv_profile_name.setText(Html.fromHtml(name));
        eventViewHolder.tv_date.setText(eventPOJO.getAddedOn());


        eventViewHolder.iv_event_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(activity, view);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        return false;
                    }
                });
                if(eventPOJO.getEventProfile().getUserProfileId().equalsIgnoreCase(Constants.userProfilePOJO.getUserProfileId())) {
                    menu.inflate(R.menu.menu_my_feed);
                }else{
                    menu.inflate(R.menu.menu_friend_feed);
                }
                menu.show();
            }
        });
    }

    public void inflatePostData(final PostViewHolder postViewHolder, final PostPOJO postPOJO, int position) {

        try {
            UserProfilePOJO userProfilePOJO = postPOJO.getPostProfile();
            SetViews.setProfilePhoto(activity.getApplicationContext(), userProfilePOJO.getProfilePhotoPath(), postViewHolder.cv_profile_pic);


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
            name = "<b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b>";

            String profile_description = "";
            boolean containDescribe = false;
            if (postPOJO.getPostTag().size() > 0) {
                containDescribe = true;
            }

            if (postPOJO.getFeelingDataPOJOS().size() > 0) {
                containDescribe = true;
            }

            if (postPOJO.getPostLocation().length() > 0) {
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

                if (postPOJO.getPostLocation().length() > 0) {
                    profile_description += " - at <b>" + postPOJO.getPostLocation() + "</b>";
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
                        PostViewFragment postViewFragment = new PostViewFragment(postPOJO);
                        homeActivity.addFragmentinFrameHome(postViewFragment, "postViewFragment");
                    }
                }
            });

            postViewHolder.cv_profile_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (activity instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) activity;
                        homeActivity.showUserProfileFragment(postPOJO.getPostProfile().getUserId(), postPOJO.getPostProfile().getUserProfileId());
                    }
                }
            });
            postViewHolder.tv_profile_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postViewHolder.cv_profile_pic.callOnClick();
                }
            });


            postViewHolder.iv_post_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final PopupMenu menu = new PopupMenu(activity, view);

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuitem) {
                            return false;
                        }
                    });
                    if(postPOJO.getPostProfile().getUserProfileId().equalsIgnoreCase(Constants.userProfilePOJO.getUserProfileId())) {
                        menu.inflate(R.menu.menu_my_feed);
                    }else{
                        menu.inflate(R.menu.menu_friend_feed);
                    }
                    menu.show();
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

        name = "<b>" + userProfilePOJO.getFirstName() + " " + userProfilePOJO.getLastName() + "</b>";

        try {
            complaintViewHolder.tv_profile_name.setText(Html.fromHtml(name));

            complaintViewHolder.tv_date.setText(complaintPOJO.getAddedOn());

            complaintViewHolder.tv_analyze.setText(complaintPOJO.getComplaintSubject());
            complaintViewHolder.tv_id.setText("CID:-" + complaintPOJO.getComplaintUniqueId());

            complaintViewHolder.ll_analyze.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (activity instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) activity;
                        ComplaintDetailFragment complaintDetailFragment = new ComplaintDetailFragment(complaintPOJO);
                        homeActivity.addFragmentinFrameHome(complaintDetailFragment, "complaintDetailFragment");
                    }
                }
            });

            complaintViewHolder.iv_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    complaintViewHolder.ll_analyze.callOnClick();
                }
            });

            complaintViewHolder.cv_profile_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (activity instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) activity;
                        homeActivity.showUserProfileFragment(complaintPOJO.getComplaintProfile().getUserId(), complaintPOJO.getComplaintProfile().getUserProfileId());
                    }
                }
            });
            complaintViewHolder.tv_profile_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    complaintViewHolder.cv_profile_pic.callOnClick();
                }
            });


            if(complaintPOJO.getComplaintTypeId().equalsIgnoreCase("2")){
                complaintViewHolder.ll_group_complaint_status.setVisibility(View.VISIBLE);
            }else{
                complaintViewHolder.ll_group_complaint_status.setVisibility(View.GONE);
            }


            complaintViewHolder.iv_complaint_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final PopupMenu menu = new PopupMenu(activity, view);

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuitem) {
                            return false;
                        }
                    });
                    if(complaintPOJO.getComplaintProfile().getUserProfileId().equalsIgnoreCase(Constants.userProfilePOJO.getUserProfileId())) {
                        menu.inflate(R.menu.menu_my_feed);
                    }else{
                        menu.inflate(R.menu.menu_friend_feed);
                    }
                    menu.show();
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
        public ImageView iv_poll_menu;
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
            iv_poll_menu = itemView.findViewById(R.id.iv_poll_menu);
            tv_participated = itemView.findViewById(R.id.tv_participated);
            rv_ans = itemView.findViewById(R.id.rv_ans);
        }
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_profile_name;
        public TextView tv_date;
        public ImageView iv_event_image;
        public ImageView iv_event_menu;
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
            iv_event_menu = itemView.findViewById(R.id.iv_event_menu);
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
        public ImageView iv_post_menu;
        public TextView tv_description;
        public CircleImageView cv_profile_pic;
        public ViewPager viewPager;
        public LinearLayout ll_news_feed;

        public PostViewHolder(View itemView) {
            super(itemView);
            tv_profile_name = itemView.findViewById(R.id.tv_profile_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_feed_image = itemView.findViewById(R.id.iv_feed_image);
            iv_post_menu = itemView.findViewById(R.id.iv_post_menu);
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
        public LinearLayout ll_group_complaint_status;
        public ImageView iv_info;
        public ImageView iv_complaint_menu;

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
            ll_group_complaint_status = itemView.findViewById(R.id.ll_group_complaint_status);
            ll_acceptdecline = itemView.findViewById(R.id.ll_acceptdecline);
            iv_info = itemView.findViewById(R.id.iv_info);
            iv_complaint_menu = itemView.findViewById(R.id.iv_complaint_menu);
        }
    }


    public static class UserProfileViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView cv_profile_pic;
        public TextView tv_user_name, tv_add_friend;
        public LinearLayout ll_user;

        public UserProfileViewHolder(View itemView) {
            super(itemView);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_add_friend = itemView.findViewById(R.id.tv_add_friend);
            ll_user = itemView.findViewById(R.id.ll_user);
        }
    }


    public void setLoaded() {
        isLoading = false;
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
