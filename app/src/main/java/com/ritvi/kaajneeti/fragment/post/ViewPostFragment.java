package com.ritvi.kaajneeti.fragment.post;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.NotificationAdapter;
import com.ritvi.kaajneeti.adapter.PostAttachmentViewAdapter;
import com.ritvi.kaajneeti.pojo.home.PostAttachmentPOJO;
import com.ritvi.kaajneeti.pojo.home.PostPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
public class ViewPostFragment extends Fragment{

    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.iv_post_menu)
    ImageView iv_post_menu;
    @BindView(R.id.tv_description)
    TextView tv_description;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.ll_news_feed)
    LinearLayout ll_news_feed;
    @BindView(R.id.cv_media)
    CardView cv_media;
    @BindView(R.id.rv_media)
    RecyclerView rv_media;

    PostPOJO postPOJO;
    public ViewPostFragment(PostPOJO postPOJO){
        this.postPOJO=postPOJO;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_view_post,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        try {
            UserProfilePOJO userProfilePOJO = postPOJO.getPostProfile();
            SetViews.setProfilePhoto(getActivity().getApplicationContext(), userProfilePOJO.getProfilePhotoPath(), cv_profile_pic);


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

            tv_profile_name.setText(Html.fromHtml(name + " " + profile_description));
            if (!postPOJO.getPostDescription().equalsIgnoreCase("")) {
                tv_description.setText(postPOJO.getPostDescription());
            } else {
                tv_description.setVisibility(View.GONE);
            }
            tv_date.setText(postPOJO.getAddedOn());

            ll_news_feed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        PostViewFragment postViewFragment = new PostViewFragment(postPOJO);
                        homeActivity.replaceFragmentinFrameHome(postViewFragment, "postViewFragment");
                    }
                }
            });

            cv_profile_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity() instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) getActivity();
                        homeActivity.showUserProfileFragment(postPOJO.getPostProfile().getUserId(), postPOJO.getPostProfile().getUserProfileId());
                    }
                }
            });
            tv_profile_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cv_profile_pic.callOnClick();
                }
            });


            iv_post_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final PopupMenu menu = new PopupMenu(getActivity(), view);

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

            if(postPOJO.getPostAttachment().size()>0){
                cv_media.setVisibility(View.VISIBLE);
                attachAdapter();
            }else{
                cv_media.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    PostAttachmentViewAdapter postAttachmentViewAdapter;

    public void attachAdapter() {
        postAttachmentViewAdapter = new PostAttachmentViewAdapter(getActivity(), this,postPOJO);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_media.setHasFixedSize(true);
        rv_media.setAdapter(postAttachmentViewAdapter);
        rv_media.setNestedScrollingEnabled(false);
        rv_media.setLayoutManager(linearLayoutManager);
        rv_media.setItemAnimator(new DefaultItemAnimator());
    }
}
