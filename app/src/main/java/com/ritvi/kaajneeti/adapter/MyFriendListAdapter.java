package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.fragment.group.SearchParticipantsFragment;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class MyFriendListAdapter extends RecyclerView.Adapter<MyFriendListAdapter.ViewHolder> {
    private List<UserProfilePOJO> items;
    Activity activity;
    Fragment fragment;
    boolean is_searching;
    List<UserProfilePOJO> participatedUserProfilePOJOS;

    public MyFriendListAdapter(Activity activity, Fragment fragment, List<UserProfilePOJO> items, boolean is_searching, List<UserProfilePOJO> participatedUserProfilePOJOS) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
        this.is_searching = is_searching;
        this.participatedUserProfilePOJOS = participatedUserProfilePOJOS;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_my_friends, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_user_name.setText(items.get(position).getFirstName() + " " + items.get(position).getLastName());
        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(holder.cv_profile_pic);
        holder.tv_email.setText(items.get(position).getEmail());

        if (is_searching) {

            if (items.get(position).isIs_checked()) {
                holder.check_user.setChecked(true);
            } else {
                holder.check_user.setChecked(false);
            }

            holder.check_user.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    items.get(position).setIs_checked(b);
                    items.get(position).setSelected_position(position);
                    if (fragment instanceof SearchParticipantsFragment) {
                        if (b) {
                            SearchParticipantsFragment searchParticipantsFragment = (SearchParticipantsFragment) fragment;
                            searchParticipantsFragment.setParticipant(items.get(position));
                        } else {
                            SearchParticipantsFragment searchParticipantsFragment = (SearchParticipantsFragment) fragment;
                            searchParticipantsFragment.deleteParticipant(items.get(position));
                        }
                    }
                }
            });
            if (participatedUserProfilePOJOS != null) {
                for (UserProfilePOJO userProfilePOJO : participatedUserProfilePOJOS) {
                    if (userProfilePOJO.getUserProfileId().equalsIgnoreCase(items.get(position).getUserProfileId())) {
                        holder.check_user.setChecked(true);
                    }
                }
            }
        } else {
            holder.check_user.setVisibility(View.GONE);
            holder.ll_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (activity instanceof HomeActivity) {
                        HomeActivity homeActivity = (HomeActivity) activity;
                        homeActivity.showUserProfileFragment(items.get(position).getUserId(), items.get(position).getUserProfileId());
                    }
                }
            });
        }


        holder.itemView.setTag(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView cv_profile_pic;
        public TextView tv_user_name, tv_email;
        public CheckBox check_user;
        public LinearLayout ll_friend;

        public ViewHolder(View itemView) {
            super(itemView);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_email = itemView.findViewById(R.id.tv_email);
            check_user = itemView.findViewById(R.id.check_user);
            ll_friend = itemView.findViewById(R.id.ll_friend);
        }
    }
}
