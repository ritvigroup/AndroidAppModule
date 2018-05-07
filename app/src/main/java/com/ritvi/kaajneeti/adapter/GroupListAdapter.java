package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.fragment.group.GroupFragment;
import com.ritvi.kaajneeti.pojo.GroupPOJO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    private List<GroupPOJO> items;
    Activity activity;
    Fragment fragment;

    public GroupListAdapter(Activity activity, Fragment fragment, List<GroupPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_group_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getFriendGroupPhoto())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(holder.cv_group_pic);

        holder.tv_group_name.setText(items.get(position).getFriendGroupName());

        holder.ll_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) activity;

                    GroupFragment fragment =  new GroupFragment();

                    Bundle bundle =  new Bundle();
                    bundle.putSerializable("group",items.get(position));
                    fragment.setArguments(bundle);

                    homeActivity.addFragmentinFrameHome(fragment,"Group Fragment");
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

        public TextView tv_group_name;
        public CircleImageView cv_group_pic;
        public LinearLayout ll_group;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_group_name = itemView.findViewById(R.id.tv_group_name);
            cv_group_pic = itemView.findViewById(R.id.cv_group_pic);
            ll_group = itemView.findViewById(R.id.ll_group);

        }
    }
}
