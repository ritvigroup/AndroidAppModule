package com.ritvi.kaajneeti.fragment.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.github.tamir7.contacts.Contact;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.SetViews;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.activity.ProfilePageActivity;
import com.ritvi.kaajneeti.adapter.HomeFeedAdapter;
import com.ritvi.kaajneeti.interfaces.OnLoadMoreListener;
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


    int start_position = 0;
    int range = 10;

    boolean check_scroll = true;
    boolean is_apicalled = true;

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


        if (getActivity() instanceof HomeActivity) {

            tv_profile_name.setText(Constants.userProfilePOJO.getFirstName() + " " + Constants.userProfilePOJO.getLastName());

            Log.d(TagUtils.getTag(), "profile photo path:-" + Constants.userProfilePOJO.getProfilePhotoPath());

            SetViews.setProfilePhoto(getActivity().getApplicationContext(), Constants.userProfilePOJO.getProfilePhotoPath(), cv_profile_pic);

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
                    if (getActivity() instanceof HomeActivity) {
                        UserProfilePOJO userProfilePOJO = Constants.userProfilePOJO;
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        homeActivity.showUserProfileFragment(userProfilePOJO.getUserId(), userProfilePOJO.getUserProfileId());
                    }
                }
            });

            tv_profile_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() instanceof HomeActivity) {
                        UserProfilePOJO userProfilePOJO = Constants.userProfilePOJO;
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        homeActivity.showUserProfileFragment(userProfilePOJO.getUserId(), userProfilePOJO.getUserProfileId());
                    }
                }
            });

            attachAdapter();
            getAllData(false, 0);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    start_position = 0;
                    range = 10;
                    check_scroll = true;
                    is_apicalled = true;
//                    getAllData(true);
                    getAllData(true,0);
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

    public void getAllData(final boolean is_refreshing, int start_position) {


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
                    removeLastPosition();

                    if (responseListPOJO.isSuccess()) {
                        Log.d(TagUtils.getTag(), "response length:-" + responseListPOJO.getResultList().size());
                        if (is_refreshing) {
                            feedPOJOS.clear();
                        }
                        feedPOJOS.addAll(responseListPOJO.getResultList());
                        homeFeedAdapter.notifyDataSetChanged();
//                        if(!is_refreshing) {
//                            if (responseListPOJO.getResultList().size() == range) {
//                                check_scroll = true;
//                                is_apicalled = false;
//                                start_position = start_position + range;
//                            } else {
//                                check_scroll = false;
//                                is_apicalled = true;
//                            }
//                        }else{
//                            start_position=0;
//                            range=10;
//                            check_scroll=true;
//                            is_apicalled=true;
//                        }
//                        inflateViews();
                    } else {
                        ToastClass.showShortToast(getActivity(), responseListPOJO.getMessage());
                    }
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, FeedPOJO.class, "CALL_FEED_DATA", false).execute(WebServicesUrls.HOME_PAGE_DATA);

    }

    public void removeLastPosition() {

        if (feedPOJOS.size() > 0) {
            if(feedPOJOS.size()>1&&feedPOJOS.get(feedPOJOS.size()-1).getFeedtype()==null) {
                feedPOJOS.remove(feedPOJOS.size() - 1);
                homeFeedAdapter.notifyItemRemoved(feedPOJOS.size());
            }

            //Generating more data
//            int index = feedPOJOS.size();
//            int end = index + 10;

//                homeFeedAdapter.notifyDataSetChanged();
//                homeFeedAdapter.setLoaded();
//            getAllData(false, index);
        }
    }

    public void goTo(int position) {
        if (position == 0) {

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
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_feeds.setHasFixedSize(true);
        rv_feeds.setLayoutManager(linearLayoutManager);
        homeFeedAdapter = new HomeFeedAdapter(rv_feeds, getActivity(), this, feedPOJOS, getChildFragmentManager());
        rv_feeds.setAdapter(homeFeedAdapter);
        rv_feeds.setNestedScrollingEnabled(true);
        rv_feeds.setItemAnimator(new DefaultItemAnimator());

        homeFeedAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d(TagUtils.getTag(), "item loading");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FeedPOJO feedPOJO = new FeedPOJO();
                        feedPOJO.setFeedtype(null);
                        feedPOJOS.add(feedPOJO);
                        homeFeedAdapter.notifyItemInserted(feedPOJOS.size() - 1);
                        getAllData(false,feedPOJOS.size());
                    }
                }, 0);
            }
        });

    }

}
