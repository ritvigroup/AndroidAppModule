package com.ritvi.kaajneeti.fragment.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.HomeFeedAdapter;
import com.ritvi.kaajneeti.adapter.SentRequestAdapter;
import com.ritvi.kaajneeti.adapter.TagSearchAdapter;
import com.ritvi.kaajneeti.pojo.AllSearchPOJO;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllSearchFragment extends Fragment {

    @BindView(R.id.rv_tags)
    RecyclerView rv_tags;
    @BindView(R.id.ic_back)
    ImageView ic_back;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.iv_search_close)
    ImageView iv_search_close;
    @BindView(R.id.ll_people)
    LinearLayout ll_people;
    @BindView(R.id.rv_people)
    RecyclerView rv_people;
    @BindView(R.id.rv_posts)
    RecyclerView rv_posts;
    @BindView(R.id.rv_events)
    RecyclerView rv_events;
    @BindView(R.id.tv_all_people)
    TextView tv_all_people;
    @BindView(R.id.tv_all_posts)
    TextView tv_all_posts;
    @BindView(R.id.tv_all_events)
    TextView tv_all_events;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_all_search_new, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        attachAdapter();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                callSearchAPI(et_search.getText().toString());
            }
        });

        tv_all_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    SearchActivityShowFragment searchActivityShowFragment = new SearchActivityShowFragment(Constants.SEARCH_IN_PEOPLE, et_search.getText().toString(), null);
                    homeActivity.replaceFragmentinFrameHome(searchActivityShowFragment, "searchActivityShowFragment");
                }
            }
        });

        tv_all_posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    SearchPostShowFragment searchPostShowFragment = new SearchPostShowFragment(Constants.SEARCH_IN_POST, et_search.getText().toString(), null);
                    homeActivity.replaceFragmentinFrameHome(searchPostShowFragment, "searchPostShowFragment");
                }
            }
        });

        tv_all_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    SearchEventShowFragment searchEventShowFragment = new SearchEventShowFragment(Constants.SEARCH_IN_EVENT, et_search.getText().toString(), null);
                    homeActivity.replaceFragmentinFrameHome(searchEventShowFragment, "searchEventShowFragment");
                }
            }
        });
    }

    AllSearchPOJO allSearchPOJO;

    public void callSearchAPI(String key) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("q", key));
        nameValuePairs.add(new BasicNameValuePair("start", "0"));
        nameValuePairs.add(new BasicNameValuePair("end", "10"));
        nameValuePairs.add(new BasicNameValuePair("search_in", "all"));
        new WebServiceBaseResponse<AllSearchPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<AllSearchPOJO>() {

            @Override
            public void onGetMsg(ResponsePOJO<AllSearchPOJO> responsePOJO) {
                if (responsePOJO.isSuccess()) {
                    AllSearchFragment.this.allSearchPOJO = responsePOJO.getResult();
                    showData(responsePOJO.getResult());
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responsePOJO.getMessage());
                }
            }
        }, AllSearchPOJO.class, "ALL_SEARCH_API", false).execute(WebServicesUrls.ALL_SEARCH_API);
    }


    public void showData(AllSearchPOJO allSearchPOJO) {
        if (allSearchPOJO != null && allSearchPOJO.getProfileFeeds() != null && allSearchPOJO.getProfileFeeds().size() > 0) {
            List<FeedPOJO> profileFeedPOJOs = new ArrayList<>();
            if (allSearchPOJO.getProfileFeeds().size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    FeedPOJO feedPOJO = allSearchPOJO.getProfileFeeds().get(i);
                    if (feedPOJO.getProfiledata() != null) {
                        profileFeedPOJOs.add(feedPOJO);
                    }
                }
            } else {
                profileFeedPOJOs.addAll(allSearchPOJO.getProfileFeeds());
            }

            List<FeedPOJO> postFeedPOJOs = new ArrayList<>();
            if (allSearchPOJO.getPostFeeds().size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    postFeedPOJOs.add(allSearchPOJO.getPostFeeds().get(i));
                }
            } else {
                postFeedPOJOs.addAll(allSearchPOJO.getPostFeeds());
            }

            List<FeedPOJO> eventsFeedPOJOs = new ArrayList<>();
            if (allSearchPOJO.getEventFeeds().size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    eventsFeedPOJOs.add(allSearchPOJO.getEventFeeds().get(i));
                }
            } else {
                eventsFeedPOJOs.addAll(allSearchPOJO.getEventFeeds());
            }

            attachProfileRecycler(profileFeedPOJOs);
            attachRecyclerView(rv_posts, postFeedPOJOs);
            attachRecyclerView(rv_events, eventsFeedPOJOs);
        }
    }

    public void attachProfileRecycler(List<FeedPOJO> feedPOJOS) {
        List<UserProfilePOJO> userProfilePOJOS = new ArrayList<>();
        for (FeedPOJO feedPOJO : feedPOJOS) {
            userProfilePOJOS.add(feedPOJO.getProfiledata());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_people.setLayoutManager(linearLayoutManager);
        SentRequestAdapter sentRequestAdapter = new SentRequestAdapter(getActivity(), this, userProfilePOJOS);
        rv_people.setHasFixedSize(true);
        rv_people.setAdapter(sentRequestAdapter);
        rv_people.setNestedScrollingEnabled(false);
        rv_people.setItemAnimator(new DefaultItemAnimator());
    }

    public void attachRecyclerView(RecyclerView recyclerView, List<FeedPOJO> feedPOJOS) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        HomeFeedAdapter homeFeedAdapter = new HomeFeedAdapter(recyclerView, getActivity(), this, feedPOJOS, getChildFragmentManager());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(homeFeedAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    List<String> tagsStrings = new ArrayList<>();
    TagSearchAdapter tagSearchAdapter;

    public void attachAdapter() {

        tagsStrings.add(Constants.SEARCH_IN_PEOPLE);
        tagsStrings.add(Constants.SEARCH_IN_POST);
        tagsStrings.add(Constants.SEARCH_IN_EVENT);
        tagsStrings.add(Constants.SEARCH_IN_POLL);
        tagsStrings.add(Constants.SEARCH_IN_COMPLAINT);
        tagsStrings.add(Constants.SEARCH_IN_SUGGESTION);

        tagSearchAdapter = new TagSearchAdapter(getActivity(), this, tagsStrings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_tags.setHasFixedSize(true);
        rv_tags.setAdapter(tagSearchAdapter);
        rv_tags.setLayoutManager(linearLayoutManager);
        rv_tags.setItemAnimator(new DefaultItemAnimator());
    }


    public void showPosts(String search_in) {
        if (getActivity() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            switch (search_in.toLowerCase()) {
                case Constants.SEARCH_IN_POST:
                    SearchPostShowFragment searchPostShowFragment;
                    if (allSearchPOJO != null && allSearchPOJO.getPostFeeds() != null) {
                        searchPostShowFragment = new SearchPostShowFragment(search_in, et_search.getText().toString(), allSearchPOJO.getPostFeeds());
                    } else {
                        searchPostShowFragment = new SearchPostShowFragment(search_in, et_search.getText().toString(), null);
                    }
                    homeActivity.replaceFragmentinFrameHome(searchPostShowFragment, "searchPostShowFragment");
                    break;
                case Constants.SEARCH_IN_PEOPLE:
                    SearchActivityShowFragment searchActivityShowFragment;
                    if (allSearchPOJO != null && allSearchPOJO.getProfileFeeds() != null) {
                        searchActivityShowFragment = new SearchActivityShowFragment(search_in, et_search.getText().toString(), allSearchPOJO.getProfileFeeds());
                    } else {
                        searchActivityShowFragment = new SearchActivityShowFragment(search_in, et_search.getText().toString(), null);
                    }
                    homeActivity.replaceFragmentinFrameHome(searchActivityShowFragment, "searchActivityShowFragment");
                    break;
                case Constants.SEARCH_IN_EVENT:
                    SearchEventShowFragment searchEventShowFragment;
                    if (allSearchPOJO != null && allSearchPOJO.getEventFeeds() != null) {
                        searchEventShowFragment = new SearchEventShowFragment(search_in, et_search.getText().toString(), allSearchPOJO.getEventFeeds());
                    } else {
                        searchEventShowFragment = new SearchEventShowFragment(search_in, et_search.getText().toString(), null);
                    }
                    homeActivity.replaceFragmentinFrameHome(searchEventShowFragment, "searchEventShowFragment");
                    break;
                case Constants.SEARCH_IN_POLL:
                    SearchPollShowFragment searchPollShowFragment;
                    if (allSearchPOJO != null && allSearchPOJO.getPollFeeds() != null) {
                        searchPollShowFragment = new SearchPollShowFragment(search_in, et_search.getText().toString(), allSearchPOJO.getPollFeeds());
                    } else {
                        searchPollShowFragment = new SearchPollShowFragment(search_in, et_search.getText().toString(), null);
                    }
                    homeActivity.replaceFragmentinFrameHome(searchPollShowFragment, "searchPollShowFragment");
                    break;
                case Constants.SEARCH_IN_COMPLAINT:
                    SearchComplaintShowFragment searchComplaintShowFragment;
                    if (allSearchPOJO != null && allSearchPOJO.getComplaintFeeds() != null) {
                        searchComplaintShowFragment = new SearchComplaintShowFragment(search_in, et_search.getText().toString(), allSearchPOJO.getComplaintFeeds());
                    } else {
                        searchComplaintShowFragment = new SearchComplaintShowFragment(search_in, et_search.getText().toString(), null);
                    }
                    homeActivity.replaceFragmentinFrameHome(searchComplaintShowFragment, "searchComplaintShowFragment");
                    break;
            }
        }
    }
}
