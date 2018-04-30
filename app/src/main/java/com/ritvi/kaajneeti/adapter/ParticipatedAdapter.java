package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.fragment.group.SearchParticipantsFragment;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class ParticipatedAdapter extends RecyclerView.Adapter<ParticipatedAdapter.ViewHolder> {
    private List<UserProfilePOJO> items;
    Activity activity;
    Fragment fragment;

    public ParticipatedAdapter(Activity activity, Fragment fragment, List<UserProfilePOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_participated_user, parent, false);
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

        holder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragment instanceof SearchParticipantsFragment){
                    SearchParticipantsFragment searchParticipantsFragment= (SearchParticipantsFragment) fragment;
                    searchParticipantsFragment.removepariticpant(items.get(position));
                    items.remove(position);
                    notifyDataSetChanged();
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
        public CircleImageView cv_profile_pic;
        public TextView tv_user_name;
        public ImageView iv_remove;

        public ViewHolder(View itemView) {
            super(itemView);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            iv_remove = itemView.findViewById(R.id.iv_remove);
        }
    }
}
