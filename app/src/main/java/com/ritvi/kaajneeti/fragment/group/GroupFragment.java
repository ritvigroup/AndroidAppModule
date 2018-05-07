package com.ritvi.kaajneeti.fragment.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.SetViews;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.GroupPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.testing.CreateExpressActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupFragment extends Fragment {

    @BindView(R.id.tv_group_name)
    TextView tv_group_name;
    @BindView(R.id.iv_group_menu)
    ImageView iv_group_menu;

    GroupPOJO groupPOJO;

    @BindView(R.id.ll_whats_mind)
    LinearLayout ll_whats_mind;
    @BindView(R.id.tv_whats_mind)
    TextView tv_whats_mind;
    @BindView(R.id.cv_profile_pic)
    CircleImageView cv_profile_pic;
    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_group, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            groupPOJO = (GroupPOJO) bundle.getSerializable("group");
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (groupPOJO != null) {
            tv_group_name.setText(groupPOJO.getFriendGroupName());

            iv_group_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final PopupMenu menu = new PopupMenu(getActivity(), view);

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuitem) {
                            switch (menuitem.getItemId()) {
                                case R.id.popup_group_info:
                                    showGroupInfoFragment();
                                    break;
                            }
                            return false;
                        }
                    });
                    menu.inflate(R.menu.menu_group);
                    menu.show();
                }
            });

        }

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
        }
    }


    public void goTo(int position) {
        if (position == 0) {
            startActivity(new Intent(getActivity(), CreateExpressActivity.class));
        } else if (position == 1) {
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "issue"));
        } else if (position == 2) {
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "complaint"));
        } else if (position == 3) {
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "suggestion"));
        } else if (position == 4) {
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "information"));
        } else if (position == 5) {
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "event"));
        } else if (position == 6) {
            startActivity(new Intent(getActivity(), CreateExpressActivity.class).putExtra("type", "poll"));
        }
    }


    public void showGroupInfoFragment() {
//        GroupInfoFragment groupInfoFragment=new GroupInfoFragment(groupPOJO);
//        if(getActivity() instanceof HomeActivity){
//            HomeActivity homeActivity= (HomeActivity) getActivity();
//            homeActivity.addFragmentinFrameHome(groupInfoFragment,"groupInfoFragment");
//        }
        startActivity(new Intent(getActivity(), GroupInfoActivity.class).putExtra("groupPOJO", groupPOJO));
    }
}
