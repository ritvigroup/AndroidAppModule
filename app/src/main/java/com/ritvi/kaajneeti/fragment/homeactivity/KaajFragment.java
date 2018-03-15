package com.ritvi.kaajneeti.fragment.homeactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.activity.AddPostActivity;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.newsfeeds.EventFeed;
import com.ritvi.kaajneeti.pojo.newsfeeds.PollFeed;
import com.ritvi.kaajneeti.pojo.newsfeeds.PostFeed;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.testing.FacebookMainActivity;
import com.ritvi.kaajneeti.webservice.GetWebServices;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 12-03-2018.
 */

public class KaajFragment extends Fragment implements WebServicesCallBack {

    private static final String CALL_NEWS_FEED = "call_news_feed";
    @BindView(R.id.ll_scroll)
    LinearLayout ll_scroll;
    @BindView(R.id.tv_whats_mind)
    TextView tv_whats_mind;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;

    UserProfilePOJO userProfilePOJO;

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
                startActivity(new Intent(getActivity(), FacebookMainActivity.class));
            }
        });


        tv_whats_mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddPostActivity.class));
            }
        });

        if (getActivity() instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            userProfilePOJO = homeActivity.userProfilePOJO;

//            inflateNewsFeeds();
            tv_profile_name.setText(userProfilePOJO.getFullname());

            Glide.with(getActivity().getApplicationContext())
                    .load(userProfilePOJO.getProfileImage())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);

            StringRequest req = new StringRequest("https://demo8911870.mockable.io/getnewsfeeds",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TagUtils.getTag(), response.toString());
//                        parseHomeNewsResponse(response);
                            onGetMsg("", response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TagUtils.getTag(), "api error:-" + error.toString());
                }
            });
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(req);
        }
    }

    public void getNewsFeed() {
        new GetWebServices(getActivity(), this, CALL_NEWS_FEED, true).execute("https://demo8911870.mockable.io/getnewsfeeds");
    }

    public void inflateNewsFeeds() {
        for (int i = 0; i < 10; i++) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = null;
            if (i % 4 == 0) {
                view = inflater.inflate(R.layout.inflate_poll_feed, null);
            } else if (i % 4 == 1) {
                view = inflater.inflate(R.layout.inflate_event_feed, null);

                final TextView tv_title = view.findViewById(R.id.tv_title);
                final LinearLayout ll_preview = view.findViewById(R.id.ll_preview);

                final View preview_card = inflater.inflate(R.layout.preview_content, null);

                final ImageView image_post_set = preview_card.findViewById(R.id.image_post_set);
                final TextView title = preview_card.findViewById(R.id.title);
                final TextView url = preview_card.findViewById(R.id.url);
                final TextView description = preview_card.findViewById(R.id.description);


                TextCrawler textCrawler = new TextCrawler();

                LinkPreviewCallback linkPreviewCallback = new LinkPreviewCallback() {
                    @Override
                    public void onPre() {
                        // Any work that needs to be done before generating the preview. Usually inflate
                        // your custom preview layout here.
                    }

                    @Override
                    public void onPos(SourceContent sourceContent, boolean b) {
                        // Populate your preview layout with the results of sourceContent.

                        if (sourceContent.getImages().size() > 0) {
                            Log.d(TagUtils.getTag(), "source:-" + sourceContent.getImages().get(0));
                            Glide.with(getActivity().getApplicationContext())
                                    .load(sourceContent.getImages().get(0))
                                    .into(image_post_set);
                        }
                        Log.d(TagUtils.getTag(), "source final:-" + sourceContent.getFinalUrl());
                        Log.d(TagUtils.getTag(), "source title:-" + sourceContent.getTitle());
                        Log.d(TagUtils.getTag(), "source description:-" + sourceContent.getDescription());

                        title.setText(sourceContent.getTitle());
                        url.setText(sourceContent.getFinalUrl());
                        description.setText(sourceContent.getDescription());
                        ll_preview.addView(preview_card);
                        tv_title.setText(sourceContent.getTitle());

                    }
                };
                textCrawler.makePreview(linkPreviewCallback, "https://www.androidhive.info/2018/01/android-app-ui-designing-using-sketch-app-and-zeplin/");

            } else if (i % 4 == 2) {
                view = inflater.inflate(R.layout.inflate_sponsored_add, null);
            } else if (i % 4 == 3) {
                view = inflater.inflate(R.layout.inflate_news_feeds, null);
            }

            CircleImageView cv_profile_pic = view.findViewById(R.id.cv_profile_pic);
            TextView tv_profile_name = view.findViewById(R.id.tv_profile_name);

            tv_profile_name.setText(userProfilePOJO.getFullname());
            Glide.with(getActivity().getApplicationContext())
                    .load(userProfilePOJO.getProfileImage())
                    .placeholder(R.drawable.ic_default_profile_pic)
                    .error(R.drawable.ic_default_profile_pic)
                    .dontAnimate()
                    .into(cv_profile_pic);


            ll_scroll.addView(view);

        }

    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(), apicall + ":-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("status").equals("success")) {
                JSONArray jsonArray = jsonObject.optJSONArray("feeds");
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        inflateNewsFeed(jsonArray.optJSONObject(i).toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inflateNewsFeed(String news) {

        try {

            JSONObject jsonObject = new JSONObject(news);

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = null;
            if (jsonObject.optString("feedtype").equals("post")) {
                view = inflater.inflate(R.layout.inflate_news_feeds, null);

                Gson gson = new Gson();
                PostFeed postFeed = gson.fromJson(jsonObject.optJSONObject("data").toString(), PostFeed.class);
                inflatePostFeed(view, postFeed);

            } else if (jsonObject.optString("feedtype").equals("poll")) {
                view = inflater.inflate(R.layout.inflate_poll_feed, null);

                Gson gson = new Gson();
                PollFeed pollFeed = gson.fromJson(jsonObject.optJSONObject("data").toString(), PollFeed.class);
                inflatePollFeed(view, pollFeed);
            } else if (jsonObject.optString("feedtype").equals("event")) {
                view = inflater.inflate(R.layout.inflate_event_feed, null);

                Gson gson = new Gson();
                EventFeed eventFeed = gson.fromJson(jsonObject.optJSONObject("data").toString(), EventFeed.class);
                inflateEventFeed(view, eventFeed);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void inflatePostFeed(View view, PostFeed postFeed) {
        CircleImageView cv_profile_pic = view.findViewById(R.id.cv_profile_pic);
        TextView tv_profile_name = view.findViewById(R.id.tv_profile_name);
        ImageView iv_feed_image = view.findViewById(R.id.iv_feed_image);
        TextView tv_description = view.findViewById(R.id.tv_description);
//        ViewPager viewPager = view.findViewById(R.id.viewPager);

        tv_profile_name.setText(postFeed.getFirst_name() + " " + postFeed.getLast_name());
        Glide.with(getActivity().getApplicationContext())
                .load(postFeed.getProfile_pic())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);


        String profile_name = postFeed.getFirst_name() + " " + postFeed.getLast_name();
        String profile_desc = "<b>" + profile_name + "</b>";

        if (!postFeed.getFeeling().equals("")) {
            profile_desc += " is feeling <b>" + postFeed.getFeeling() + "</b> ";
        } else {
            if (postFeed.getPostAttachments().size() > 0) {
                if (postFeed.getPostAttachments().size() > 1) {
                    profile_desc += " - added " + postFeed.getPostAttachments().size() + " Photos";
                } else {
                    profile_desc += " - added Photo";
                }
                Glide.with(getActivity())
                        .load(postFeed.getPostAttachments().get(0).getAttachmentFile())
                        .into(iv_feed_image);
            } else {
                iv_feed_image.setVisibility(View.GONE);
//            viewPager.setVisibility(View.GONE);
            }
        }

        if (postFeed.getPostAttachments().size() > 0) {
            Glide.with(getActivity())
                    .load(postFeed.getPostAttachments().get(0).getAttachmentFile())
                    .into(iv_feed_image);
        } else {
            iv_feed_image.setVisibility(View.GONE);
//            viewPager.setVisibility(View.GONE);
        }


        if (postFeed.getPostDescription().equals("")) {
            tv_description.setVisibility(View.GONE);
        } else {
            tv_description.setText(postFeed.getPostDescription());
        }
        if (postFeed.getTaggedusersList().size() > 0) {
            if (postFeed.getTaggedusersList().size() == 1) {
                profile_desc += " with <b>" + postFeed.getTaggedusersList().get(0).getFirst_name() + " " + postFeed.getTaggedusersList().get(0).getLast_name() + "</b>";
            } else if (postFeed.getTaggedusersList().size() == 2) {
                profile_desc += " with <b>" + postFeed.getTaggedusersList().get(0).getFirst_name() + " " + postFeed.getTaggedusersList().get(0).getLast_name() + "</b> and <b>" + postFeed.getTaggedusersList().get(1).getFirst_name() + " " + postFeed.getTaggedusersList().get(1).getLast_name() + "</b>";
            } else if (postFeed.getTaggedusersList().size() > 2) {
                profile_desc += " with <b>" + postFeed.getTaggedusersList().get(0).getFirst_name() + " " + postFeed.getTaggedusersList().get(0).getLast_name() + "</b> , <b>" + postFeed.getTaggedusersList().get(1).getFirst_name() + " " + postFeed.getTaggedusersList().get(1).getLast_name() + "</b> and <b>" + (postFeed.getTaggedusersList().size() - 2) + " others.</b>";
            }
        }


        tv_profile_name.setText(Html.fromHtml(profile_desc));
        ll_scroll.addView(view);
    }

    public void inflatePollFeed(View view, PollFeed pollFeed) {
        CircleImageView cv_profile_pic = view.findViewById(R.id.cv_profile_pic);
        TextView tv_profile_name = view.findViewById(R.id.tv_profile_name);
        final LinearLayout ll_votes = view.findViewById(R.id.ll_votes);
        final LinearLayout ll_review = view.findViewById(R.id.ll_review);

        tv_profile_name.setText(pollFeed.getFirstName() + " " + pollFeed.getLastName());
        Glide.with(getActivity().getApplicationContext())
                .load(pollFeed.getProfilePic())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);

        final Button btn_ans1 = view.findViewById(R.id.btn_ans1);
        Button btn_ans2 = view.findViewById(R.id.btn_ans2);
        Button btn_ans3 = view.findViewById(R.id.btn_ans3);
        final Button btn_ans4 = view.findViewById(R.id.btn_ans4);

        btn_ans1.setText(pollFeed.getPollAnsList().get(0).getPollAnswer());
        btn_ans2.setText(pollFeed.getPollAnsList().get(1).getPollAnswer());
        btn_ans3.setText(pollFeed.getPollAnsList().get(2).getPollAnswer());
        btn_ans4.setText(pollFeed.getPollAnsList().get(3).getPollAnswer());

        btn_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_votes.setVisibility(View.GONE);
                ll_review.setVisibility(View.VISIBLE);
            }
        });
        btn_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_ans1.callOnClick();
            }
        });

        btn_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_ans1.callOnClick();
            }
        });
        btn_ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_ans1.callOnClick();
            }
        });
//        for (PollAns pollAns : pollFeed.getPollAnsList()) {
//            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View pollAnsView = inflater.inflate(R.layout.inflate_poll_ans, ll_votes,true);
//
//            Button btn_ans = pollAnsView.findViewById(R.id.btn_ans);
//            btn_ans.setText(pollAns.getPollAnswer());
//            ll_votes.addView(btn_ans);
//        }

        ll_scroll.addView(view);
    }

    public void inflateEventFeed(View view, EventFeed eventFeed) {
        CircleImageView cv_profile_pic = view.findViewById(R.id.cv_profile_pic);
        TextView tv_profile_name = view.findViewById(R.id.tv_profile_name);

        tv_profile_name.setText(eventFeed.getFirstName() + " " + eventFeed.getLastName());
        Glide.with(getActivity().getApplicationContext())
                .load(eventFeed.getProfilePic())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(cv_profile_pic);

        ImageView iv_event_image=view.findViewById(R.id.iv_event_image);
        TextView tv_name=view.findViewById(R.id.tv_name);
        TextView tv_date=view.findViewById(R.id.tv_date);
        TextView tv_place=view.findViewById(R.id.tv_place);

        Glide.with(getActivity().getApplicationContext())
                .load(eventFeed.getAttachments().get(0).getAttachmentFile())
                .into(iv_event_image);

        tv_name.setText(eventFeed.getEventName());
        tv_place.setText(eventFeed.getEventLocation());
        tv_date.setText(eventFeed.getStartDate()+" - "+eventFeed.getEndDate());


        ll_scroll.addView(view);
    }
}
