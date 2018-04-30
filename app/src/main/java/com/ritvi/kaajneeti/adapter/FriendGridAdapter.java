package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class FriendGridAdapter extends RecyclerView.Adapter<FriendGridAdapter.ViewHolder> {
    private List<UserProfilePOJO> items;
    Activity activity;
    Fragment fragment;

    public FriendGridAdapter(Activity activity, Fragment fragment, List<UserProfilePOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_profile_page, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(holder.iv_profile_pic);

        if(items.get(position).getFirstName().equals("")||
                items.get(position).getLastName().equals("")) {
            holder.tv_name.setText(items.get(position).getFirstName());
        }else{
            holder.tv_name.setText(items.get(position).getFirstName() + " " + items.get(position).getLastName());
        }

        holder.ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) activity;
                    homeActivity.showUserProfileFragment(items.get(position).getUserId(),items.get(position).getUserProfileId());
                }
            }
        });

        holder.itemView.setTag(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_profile_pic;
        public LinearLayout ll_profile;
        public TextView tv_name;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_profile_pic=itemView.findViewById(R.id.iv_profile_pic);
            ll_profile=itemView.findViewById(R.id.ll_profile);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}
