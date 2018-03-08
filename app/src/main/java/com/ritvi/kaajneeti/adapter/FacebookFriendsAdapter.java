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
import com.ritvi.kaajneeti.pojo.facebook.FacebookDataPOJO;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class FacebookFriendsAdapter extends RecyclerView.Adapter<FacebookFriendsAdapter.ViewHolder> implements WebServicesCallBack {
    private List<FacebookDataPOJO> items;
    Activity activity;
    Fragment fragment;
    int device_height = 0;

    public FacebookFriendsAdapter(Activity activity, Fragment fragment, List<FacebookDataPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_facebook_friends, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getFacebookPicturePOJO().getFacebookPictureDataPOJO().getUrl())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(holder.iv_image);

        holder.tv_friend_name.setText(items.get(position).getName());

        holder.itemView.setTag(items.get(position));
    }


    private final String TAG = getClass().getSimpleName();

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onGetMsg(String apicall, String response) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_image;
        public TextView tv_friend_name;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_friend_name = (TextView) itemView.findViewById(R.id.tv_friend_name);
        }
    }
}
