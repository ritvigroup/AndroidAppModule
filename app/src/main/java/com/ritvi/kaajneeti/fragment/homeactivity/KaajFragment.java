package com.ritvi.kaajneeti.fragment.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.SetViews;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.activity.ProfilePageActivity;
import com.ritvi.kaajneeti.adapter.HomeFeedAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;
import com.ritvi.kaajneeti.pojo.home.PollAnsPOJO;
import com.ritvi.kaajneeti.pojo.home.PollPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.testing.CreateExpressActivity;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
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

/**
 * Created by sunil on 12-03-2018.
 */

public class KaajFragment extends Fragment {


    int start_position=0;
    int range=10;

    boolean  check_scroll=true;
    boolean is_apicalled=true;

    private static final String CALL_NEWS_FEED = "call_news_feed";
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.tv_whats_mind)
    TextView tv_whats_mind;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.rv_feeds)
    RecyclerView rv_feeds;
    @BindView(R.id.tv_event)
    TextView tv_event;
    @BindView(R.id.tv_poll)
    TextView tv_poll;
    @BindView(R.id.tv_complaint)
    TextView tv_complaint;
    @BindView(R.id.tv_suggestion)
    TextView tv_suggestion;
    @BindView(R.id.tv_information)
    TextView tv_information;
    @BindView(R.id.tv_issue)
    TextView tv_issue;
    @BindView(R.id.scroll_home)
    ScrollView scroll_home;
    @BindView(R.id.ll_post)
    LinearLayout ll_post;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.ll_whats_mind)
    LinearLayout ll_whats_mind;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_kaaj, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfilePageActivity.class).putExtra("userProfilePOJO", Constants.userProfilePOJO));
            }
        });

        if (getActivity() instanceof HomeActivity) {

            tv_profile_name.setText(Constants.userProfilePOJO.getFirstName()+" "+Constants.userProfilePOJO.getLastName());

            Log.d(TagUtils.getTag(),"profile photo path:-"+Constants.userProfilePOJO.getProfilePhotoPath());

//            Glide.with(getActivity().getApplicationContext())
//                    .load(Constants.userProfilePOJO.getProfilePhotoPath())
//                    .placeholder(R.drawable.ic_default_profile_pic)
//                    .error(R.drawable.ic_default_profile_pic)
//                    .dontAnimate()
//                    .into(cv_profile_pic);

            SetViews.setProfilePhoto(getActivity().getApplicationContext(),Constants.userProfilePOJO.getProfilePhotoPath(),cv_profile_pic);

            ll_whats_mind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goTo(0);
                }
            });

            tv_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goTo(5);
                }
            });

            tv_complaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goTo(2);
                }
            });

            tv_information.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goTo(4);
                }
            });

            tv_poll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goTo(6);
                }
            });

            tv_suggestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goTo(3);
                }
            });
            tv_issue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goTo(1);
                }
            });

            cv_profile_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(new Intent(getActivity(), ProfileDescriptionActivity.class).putExtra("userInfo", Constants.userInfoPOJO));
                    if(getActivity() instanceof HomeActivity){
                        UserProfilePOJO userProfilePOJO=Constants.userProfilePOJO;
                        HomeActivity homeActivity= (HomeActivity) getActivity();
                        homeActivity.showUserProfileFragment(userProfilePOJO.getUserId(),userProfilePOJO.getUserProfileId());
                    }
                }
            });

            tv_profile_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getActivity() instanceof HomeActivity){
                        UserProfilePOJO userProfilePOJO=Constants.userProfilePOJO;
                        HomeActivity homeActivity= (HomeActivity) getActivity();
                        homeActivity.showUserProfileFragment(userProfilePOJO.getUserId(),userProfilePOJO.getUserProfileId());
                    }
                }
            });

            attachAdapter();
            getAllData(false);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    start_position=0;
                    range=10;
                    check_scroll=true;
                    is_apicalled=true;
                    getAllData(true);
                }
            });
        }

//        scroll_home.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                int scrollY = scroll_home.getScrollY(); // For ScrollView
//                int scrollX = scroll_home.getScrollX();
//                Log.d(TagUtils.getTag(),"scroll X:-"+scrollX+" scroll y:-"+scrollY);
//            }
//        });



    }

    public void getAllData(final boolean is_refreshing) {

        is_apicalled=true;

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("start", String.valueOf(start_position)));
        nameValuePairs.add(new BasicNameValuePair("end", String.valueOf(range)));
        new WebServiceBaseResponseList<FeedPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<FeedPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<FeedPOJO> responseListPOJO) {
                try {
//                    feedPOJOS.clear();
                    if (responseListPOJO.isSuccess()) {
                        Log.d(TagUtils.getTag(),"response length:-"+responseListPOJO.getResultList().size());
                        if(is_refreshing){
                            feedPOJOS.clear();
                        }
                        feedPOJOS.addAll(responseListPOJO.getResultList());
                        homeFeedAdapter.notifyDataSetChanged();
                        if(!is_refreshing) {
                            if (responseListPOJO.getResultList().size() == range) {
                                check_scroll = true;
                                is_apicalled = false;
                                start_position = start_position + range;
                            } else {
                                check_scroll = false;
                                is_apicalled = true;
                            }
                        }else{
                            start_position=0;
                            range=10;
                            check_scroll=true;
                            is_apicalled=true;
                        }
//                        inflateViews();
                    } else {
                        ToastClass.showShortToast(getActivity(), responseListPOJO.getMessage());
                    }
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, FeedPOJO.class, "CALL_FEED_DATA", true).execute(WebServicesUrls.HOME_PAGE_DATA);
//
//        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
//            @Override
//            public void onGetMsg(String apicall, String response) {
//                try{
//                    JSONObject jsonObject=new JSONObject(response);
//                    if(jsonObject.optString("status").equals("success")){
//                        JSONArray jsonArray=jsonObject.optJSONArray("result");
//                        for(int i=0;i<jsonArray.length();i++){
//                            Gson gson=new Gson();
//                            Log.d(TagUtils.getTag(),"data:-"+jsonArray.optJSONObject(i).toString());
//                            FeedPOJO feedPOJO=gson.fromJson(jsonArray.optJSONObject(i).toString(),FeedPOJO.class);
//                        }
//                    }
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        },"CALL_FEED_DATA",true).execute(WebServicesUrls.HOME_PAGE_DATA);
    }

    public void goTo(int position) {
        if (position == 0) {
//            startActivity(new Intent(getActivity(),CreatePostActivity.class));
            startActivity(new Intent(getActivity(), CreateExpressActivity.class));
        } else if (position == 1) {
//            startActivity(new Intent(getActivity(),CreateIssueActivity.class));
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "issue"));
        } else if (position == 2) {
//            startActivity(new Intent(getActivity(), AddCommunication.class));
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "complaint"));
        } else if (position == 3) {
//            startActivity(new Intent(getActivity(),CreateSuggestionActivity.class));
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "suggestion"));
        } else if (position == 4) {
//            startActivity(new Intent(getActivity(),CreateInformationActivity.class));
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "information"));
        } else if (position == 5) {
//            startActivity(new Intent(getActivity(),CreateEventActivity.class));
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "event"));
        } else if (position == 6) {
//            startActivity(new Intent(getActivity(),CreatePollActivity.class));
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "poll"));
        }
    }

    HomeFeedAdapter homeFeedAdapter;
    List<FeedPOJO> feedPOJOS = new ArrayList<>();

    public void attachAdapter() {
        homeFeedAdapter = new HomeFeedAdapter(getActivity(), this, feedPOJOS,getChildFragmentManager());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_feeds.setHasFixedSize(true);
        rv_feeds.setAdapter(homeFeedAdapter);
        rv_feeds.setLayoutManager(linearLayoutManager);
        rv_feeds.setNestedScrollingEnabled(true);
        rv_feeds.setItemAnimator(new DefaultItemAnimator());

        rv_feeds.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if (linearLayoutManager.findFirstVisibleItemPosition() != -1) {
//                    int position = linearLayoutManager.findFirstVisibleItemPosition();
//                    Log.d(TagUtils.getTag(),"position scroll:-"+position);
//                }
                if (linearLayoutManager.findFirstVisibleItemPosition() != -1) {
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    Log.d(TagUtils.getTag(),"position scroll:-"+position);
                    Log.d(TagUtils.getTag(),"checking position:-"+(start_position-3));
                    if(check_scroll&&!is_apicalled){
                        if(position>=((start_position)-3)){
                            getAllData(false);
                        }
                    }
                }
            }
        });

    }

//    public void inflateViews() {
//        if (feedPOJOS.size() > 0) {
//            for (FeedPOJO feedPOJO : feedPOJOS) {
//                switch (feedPOJO.getFeedtype()) {
//                    case "post":
//                        inflatePostPOJO(getActivity(), feedPOJO.getPostPOJO());
//                        break;
//                    case "poll":
//                        inflatePollPOJO(getActivity(), feedPOJO.getPollPOJO());
//                        break;
//                    case "event":
//                        inflateEventPOJO(getActivity(), feedPOJO.getEventPOJO());
//                        break;
//                    case "complaint":
//                        inflateComplaintPOJO(getActivity(), feedPOJO.getComplaintPOJO());
//                        break;
//                }
//            }
//        }
//    }

//    public void inflateComplaintPOJO(final Activity activity, final ComplaintPOJO complaintPOJO) {
//
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View itemView = inflater.inflate(R.layout.inflate_complaint_feed, null);
//        TextView tv_profile_name = itemView.findViewById(R.id.tv_profile_name);
//        TextView tv_date = itemView.findViewById(R.id.tv_date);
//        CircleImageView cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
//        TextView tv_analyze = itemView.findViewById(R.id.tv_analyze);
//        TextView tv_id = itemView.findViewById(R.id.tv_id);
//        TextView tv_accepted = itemView.findViewById(R.id.tv_accepted);
//        LinearLayout ll_analyze = itemView.findViewById(R.id.ll_analyze);
//        LinearLayout ll_accept = itemView.findViewById(R.id.ll_accept);
//        LinearLayout ll_decline = itemView.findViewById(R.id.ll_decline);
//        LinearLayout ll_accepted = itemView.findViewById(R.id.ll_accepted);
//        LinearLayout ll_acceptdecline = itemView.findViewById(R.id.ll_acceptdecline);
//        ImageView iv_info = itemView.findViewById(R.id.iv_info);
//        ImageView iv_complaint_menu = itemView.findViewById(R.id.iv_complaint_menu);
//
//
//        Glide.with(activity.getApplicationContext())
//                .load(complaintPOJO.getComplaintProfile().getUserProfileDetailPOJO().getUserInfoPOJO().getProfilePhotoPath())
//                .error(R.drawable.ic_default_profile_pic)
//                .placeholder(R.drawable.ic_default_profile_pic)
//                .dontAnimate()
//                .into(cv_profile_pic);
//
//        String name = "";
//        UserProfilePOJO userProfilePOJO = complaintPOJO.getComplaintProfile().getUserProfileDetailPOJO().getUserProfilePOJO();
//
//        if (userProfilePOJO != null) {
//            if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
//                    || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
//                    || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
//                name = userProfilePOJO.getFirstName();
//            } else {
//                name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
//            }
//        }
//
//        iv_complaint_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final PopupMenu menu = new PopupMenu(activity, view);
//
//                if (UtilityFunction.getProfileID(complaintPOJO.getComplaintProfile().getUserProfileDetailPOJO().getUserInfoPOJO()).equals(Constants.userProfilePOJO.getUserProfileId())) {
//                    menu.inflate(R.menu.menu_my_complaint);
//                    menu.show();
//                } else {
//                    menu.inflate(R.menu.menu_other_complaint);
//                    menu.show();
//                }
//            }
//        });
//
//        tv_profile_name.setText(Html.fromHtml(name));
//
//        tv_date.setText(complaintPOJO.getAddedOn());
//
//        tv_analyze.setText(complaintPOJO.getComplaintSubject());
//        tv_id.setText("CID:-" + complaintPOJO.getComplaintUniqueId());
//
//        if (complaintPOJO.getComplaintTypeId().equals("2")) {
//            if (!UtilityFunction.getUserProfilePOJO(complaintPOJO.getComplaintProfile().getUserProfileDetailPOJO().getUserInfoPOJO()).getUserProfileId().equals(Constants.userProfilePOJO.getUserProfileId())) {
//                boolean is_accepted = false;
//                boolean is_declined = false;
//                for (OutGoingRequestPOJO complaintMemberPOJO : complaintPOJO.getComplaintMemberPOJOS()) {
//                    if (UtilityFunction.getUserProfilePOJO(complaintMemberPOJO.getUserProfileDetailPOJO().getUserInfoPOJO()).getUserProfileId().equals(Constants.userProfilePOJO.getUserProfileId())) {
//                        if (complaintMemberPOJO.getAcceptedYesNo().equals("1")) {
//                            is_accepted = true;
//                        } else if (complaintMemberPOJO.getAcceptedYesNo().equals("-1")) {
//                            is_declined = true;
//                        }
//                    }
//                }
//                if (is_accepted) {
//                    ll_acceptdecline.setVisibility(View.GONE);
//                    tv_accepted.setText("You accepted the complaint request");
//                } else if (is_declined) {
//                    ll_acceptdecline.setVisibility(View.GONE);
//                    tv_accepted.setText("You declined the complaint request");
//                } else {
//                    ll_accepted.setVisibility(View.GONE);
//                }
//            } else {
//                ll_acceptdecline.setVisibility(View.GONE);
//            }
//        } else {
//            ll_acceptdecline.setVisibility(View.GONE);
//        }
//
////        ll_decline.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                showAcceptDeclintDialog("Do you want to decline the Complaint Request",0,items.get(position).getComplaintId());
////            }
////        });
////
////        ll_accept.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                showAcceptDeclintDialog("Do you want to accept the Complaint Request",1,items.get(position).getComplaintId());
////            }
////        });
//
//        ll_analyze.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(activity, ViewComplaintActivity.class);
//                intent.putExtra("complaint", complaintPOJO);
//                activity.startActivity(intent);
//            }
//        });
//
//        iv_info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(activity, ComplaintDetailActivity.class);
//                intent.putExtra("complaintPOJO", complaintPOJO);
//                activity.startActivity(intent);
//            }
//        });
//
//        ll_post.addView(itemView);
//    }

//    public void inflateEventPOJO(final Activity activity, final EventPOJO eventPOJO) {
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.inflate_event_feed, null);
//
//        TextView tv_name = view.findViewById(R.id.tv_name);
//        TextView tv_profile_name = view.findViewById(R.id.tv_profile_name);
//        TextView tv_date = view.findViewById(R.id.tv_date);
//        ImageView iv_event_image = view.findViewById(R.id.iv_event_image);
//        ImageView iv_event_menu = view.findViewById(R.id.iv_event_menu);
//        CircleImageView cv_profile_pic = view.findViewById(R.id.cv_profile_pic);
//        TextView tv_event_date = view.findViewById(R.id.tv_event_date);
//        TextView tv_place = view.findViewById(R.id.tv_place);
//        TextView tv_month = view.findViewById(R.id.tv_month);
//        TextView tv_day = view.findViewById(R.id.tv_day);
//
//
//        iv_event_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final PopupMenu menu = new PopupMenu(activity, view);
//
//                if (UtilityFunction.getProfileID(eventPOJO.getEventProfile().getUserProfileDetailPOJO().getUserInfoPOJO()).equals(Constants.userProfilePOJO.getUserProfileId())) {
//                    menu.inflate(R.menu.menu_my_complaint);
//                    menu.show();
//                } else {
//                    menu.inflate(R.menu.menu_other_complaint);
//                    menu.show();
//                }
//            }
//        });
//
//        tv_name.setText(eventPOJO.getEventName());
//
//        Glide.with(activity.getApplicationContext())
//                .load(eventPOJO.getEventProfile().getUserProfileDetailPOJO().getUserInfoPOJO().getProfilePhotoPath())
//                .error(R.drawable.ic_default_profile_pic)
//                .placeholder(R.drawable.ic_default_profile_pic)
//                .dontAnimate()
//                .into(cv_profile_pic);
//
//        if (eventPOJO.getEventAttachment().size() > 0) {
//            Glide.with(activity.getApplicationContext())
//                    .load(eventPOJO.getEventAttachment().get(0).getAttachmentFile())
//                    .error(R.drawable.ic_default_profile_pic)
//                    .placeholder(R.drawable.ic_default_profile_pic)
//                    .dontAnimate()
//                    .into(iv_event_image);
//        }
//
//        String name = "";
//        UserProfilePOJO userProfilePOJO = eventPOJO.getEventProfile().getUserProfileDetailPOJO().getUserProfilePOJO();
//
//        if (userProfilePOJO != null) {
//            if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
//                    || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
//                    || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
//                name = userProfilePOJO.getFirstName();
//            } else {
//                name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
//            }
//        }
//
//        tv_event_date.setText(eventPOJO.getStartDate() + "-" + eventPOJO.getEndDate());
//        tv_place.setText(eventPOJO.getEventLocation());
////        tv_month.setText(eventPOJO.getEveryMonth());
//
//        tv_profile_name.setText(name);
//        tv_date.setText(eventPOJO.getAddedOn());
//
//        ll_post.addView(view);
//    }

//    public void inflatePollPOJO(final Activity activity, final PollPOJO pollPOJO) {
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.inflate_poll_feed, null);
//
//        TextView tv_questions = view.findViewById(R.id.tv_questions);
//        LinearLayout ll_ans = view.findViewById(R.id.ll_ans);
//        TextView tv_profile_name = view.findViewById(R.id.tv_profile_name);
//        TextView tv_date = view.findViewById(R.id.tv_date);
//        CircleImageView cv_profile_pic = view.findViewById(R.id.cv_profile_pic);
//        ImageView iv_poll_menu = view.findViewById(R.id.iv_poll_menu);
//
//        tv_questions.setText(pollPOJO.getPollQuestion());
//
//        iv_poll_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final PopupMenu menu = new PopupMenu(activity, view);
//
//                if (UtilityFunction.getProfileID(pollPOJO.getProfileDetailPOJO().getUserProfileDetailPOJO().getUserInfoPOJO()).equals(Constants.userProfilePOJO.getUserProfileId())) {
//                    menu.inflate(R.menu.menu_my_complaint);
//                    menu.show();
//                } else {
//                    menu.inflate(R.menu.menu_other_complaint);
//                    menu.show();
//                }
//            }
//        });
//
//        Glide.with(activity.getApplicationContext())
//                .load(pollPOJO.getProfileDetailPOJO().getUserProfileDetailPOJO().getUserInfoPOJO().getProfilePhotoPath())
//                .error(R.drawable.ic_default_profile_pic)
//                .placeholder(R.drawable.ic_default_profile_pic)
//                .dontAnimate()
//                .into(cv_profile_pic);
//
//        String name = "";
//        UserProfilePOJO userProfilePOJO = pollPOJO.getProfileDetailPOJO().getUserProfileDetailPOJO().getUserProfilePOJO();
//
//        if (userProfilePOJO != null) {
//            if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
//                    || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
//                    || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
//                name = userProfilePOJO.getFirstName();
//            } else {
//                name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
//            }
//        }
//
//        tv_profile_name.setText(name);
//        tv_date.setText(pollPOJO.getAddedOn());
//
//        for (final PollAnsPOJO pollAnsPOJO : pollPOJO.getPollAnsPOJOS()) {
//            LayoutInflater inflater1 = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view1 = inflater1.inflate(R.layout.inflate_poll_ans, null);
//
//            Button btn_ans = view1.findViewById(R.id.btn_ans);
//            btn_ans.setTag(pollAnsPOJO.getPollAnswer());
//            btn_ans.setText(pollAnsPOJO.getPollAnswer());
//
//            btn_ans.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    callSavePollAns(pollPOJO, pollAnsPOJO);
//                }
//            });
//            if (ll_ans.findViewWithTag(pollAnsPOJO.getPollAnswer()) == null) {
//                ll_ans.addView(view1);
//            }
//        }
//
//        ll_post.addView(view);
//    }

    public void callSavePollAns(PollPOJO pollPOJO, PollAnsPOJO pollAnsPOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("poll_id", pollPOJO.getPollId()));
        nameValuePairs.add(new BasicNameValuePair("answer_id", pollAnsPOJO.getPollAnswerId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("success")) {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Poll Ans Saved");
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "POLL_ANS", true).execute(WebServicesUrls.SAVE_POLL_ANS);

    }

//    public void inflatePostPOJO(final Activity activity, final PostPOJO postPOJO) {
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.inflate_news_feeds, null);
//        TextView tv_profile_name = view.findViewById(R.id.tv_profile_name);
//        TextView tv_date = view.findViewById(R.id.tv_date);
//        ImageView iv_feed_image = view.findViewById(R.id.iv_feed_image);
//        ImageView iv_post_menu = view.findViewById(R.id.iv_post_menu);
//        TextView tv_description = view.findViewById(R.id.tv_description);
//        CircleImageView cv_profile_pic = view.findViewById(R.id.cv_profile_pic);
//
//        if (postPOJO != null) {
//
//
//            iv_post_menu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    final PopupMenu menu = new PopupMenu(activity, view);
//
//                    if (UtilityFunction.getProfileID(postPOJO.getPostProfile().getUserProfileDetailPOJO().getUserInfoPOJO()).equals(Constants.userProfilePOJO.getUserProfileId())) {
//                        menu.inflate(R.menu.menu_my_complaint);
//                        menu.show();
//                    } else {
//                        menu.inflate(R.menu.menu_other_complaint);
//                        menu.show();
//                    }
//                }
//            });
//
//            Glide.with(activity.getApplicationContext())
//                    .load(postPOJO.getPostProfile().getUserProfileDetailPOJO().getUserInfoPOJO().getProfilePhotoPath())
//                    .error(R.drawable.ic_default_profile_pic)
//                    .placeholder(R.drawable.ic_default_profile_pic)
//                    .dontAnimate()
//                    .into(cv_profile_pic);
//
//            if (postPOJO.getPostAttachment().size() > 0) {
//                Glide.with(activity.getApplicationContext())
//                        .load(postPOJO.getPostAttachment().get(0).getAttachmentFile())
//                        .error(R.drawable.ic_default_profile_pic)
//                        .placeholder(R.drawable.ic_default_profile_pic)
//                        .dontAnimate()
//                        .into(iv_feed_image);
//            } else {
//                iv_feed_image.setVisibility(View.GONE);
//            }
//
//            String name = "";
//            UserProfilePOJO userProfilePOJO = postPOJO.getPostProfile().getUserProfileDetailPOJO().getUserProfilePOJO();
//
//            if (userProfilePOJO != null) {
//                if (userProfilePOJO.getFirstName().equalsIgnoreCase("")
//                        || userProfilePOJO.getMiddleName().equalsIgnoreCase("")
//                        || userProfilePOJO.getLastName().equalsIgnoreCase("")) {
//                    name = userProfilePOJO.getFirstName();
//                } else {
//                    name = userProfilePOJO.getFirstName() + " " + userProfilePOJO.getMiddleName() + " " + userProfilePOJO.getLastName();
//                }
//            }
//
//            String profile_description = "";
//            boolean containDescribe = false;
//            if (postPOJO.getPostTag().size() > 0) {
//                containDescribe = true;
//            }
//
//            if (postPOJO.getFeelingDataPOJOS().size() > 0) {
//                containDescribe = true;
//            }
//
//            if (containDescribe) {
//                profile_description += " is ";
//
//                if (postPOJO.getFeelingDataPOJOS().size() > 0) {
//                    profile_description += "<b>feeling " + postPOJO.getFeelingDataPOJOS().get(0).getFeelingName() + "</b>";
//                }
//
//                if (postPOJO.getPostTag().size() > 0) {
//                    profile_description += " with ";
//                    if (postPOJO.getPostTag().size() > 2) {
//                        profile_description += "<b>" + postPOJO.getPostTag().get(0).getUserProfileDetailPOJO().getUserProfilePOJO().getFirstName() + " and " + (postPOJO.getPostTag().size() - 1) + " other" + "</b>";
//                    } else if (postPOJO.getPostTag().size() == 2) {
//                        profile_description += "<b>" + postPOJO.getPostTag().get(0).getUserProfileDetailPOJO().getUserProfilePOJO().getFirstName() + " " + postPOJO.getPostTag().get(0).getUserProfileDetailPOJO().getUserProfilePOJO().getLastName() +
//                                " and " + postPOJO.getPostTag().get(1).getUserProfileDetailPOJO().getUserProfilePOJO().getFirstName() + " " + postPOJO.getPostTag().get(1).getUserProfileDetailPOJO().getUserProfilePOJO().getLastName() + "</b>";
//                    } else {
//                        profile_description += "<b>" + postPOJO.getPostTag().get(0).getUserProfileDetailPOJO().getUserProfilePOJO().getFirstName() + " " + postPOJO.getPostTag().get(0).getUserProfileDetailPOJO().getUserProfilePOJO().getLastName() + "</b>";
//                    }
//                }
//            }
//
//            tv_profile_name.setText(Html.fromHtml(name + " " + profile_description));
//            if (!postPOJO.getPostDescription().equalsIgnoreCase("")) {
//                tv_description.setText(postPOJO.getPostDescription());
//            } else {
//                tv_description.setVisibility(View.GONE);
//            }
//            tv_date.setText(postPOJO.getAddedOn());
//        }
//        ll_post.addView(view);
//    }
}
