package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class SentRequestAdapter extends RecyclerView.Adapter<SentRequestAdapter.ViewHolder> {
    private List<UserProfilePOJO> items;
    Activity activity;
    Fragment fragment;

    public SentRequestAdapter(Activity activity, Fragment fragment, List<UserProfilePOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_search_user_profile, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_user_name.setText(items.get(position).getFirstName()+" "+items.get(position).getLastName());
        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(holder.cv_profile_pic);

        switch (items.get(position).getMyFriend()) {
            case 0:
                holder.tv_add_friend.setText("Add Friend");
                break;
            case 1:
                holder.tv_add_friend.setText("Cancel Friend Request");
                break;
            case 2:
                holder.tv_add_friend.setText("Accept Request");
                break;
            case 3:
                holder.tv_add_friend.setText("Remove Friend");
                break;
            case 4:
                holder.tv_add_friend.setText("Follow");
                break;
        }

        holder.tv_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (items.get(position).getMyFriend()) {
                    case 0:
//                        holder.tv_add_friend.setText("Add Friend");
                        sendFriendRequest(items.get(position),holder.tv_add_friend);
                        break;
                    case 1:
                        undoFriendRequest(items.get(position),holder.tv_add_friend);
//                        holder.tv_add_friend.setText("Friend Request Sent");
                        break;
                    case 2:
//                        holder.tv_add_friend.setText("Accept Request");
                        acceptRequest(items.get(position),position);
                        break;
                    case 3:
//                        holder.tv_add_friend.setText("");
                        cancelFriendRequest(items.get(position),holder.tv_add_friend);
                        break;
                }

            }
        });

        holder.ll_user.setOnClickListener(new View.OnClickListener() {
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


    public void sendFriendRequest(final UserProfilePOJO userProfilePOJO, TextView textView){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id",userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                userProfilePOJO.setMyFriend(1);
                notifyDataSetChanged();
            }
        },"CALL_ADD_FRIEND_API",true).execute(WebServicesUrls.SEND_FRIEND_REQUEST);
    }

    public void undoFriendRequest(final UserProfilePOJO userProfilePOJO, TextView textView){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id",userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                userProfilePOJO.setMyFriend(0);
                notifyDataSetChanged();
            }
        },"CALL_ADD_FRIEND_API",true).execute(WebServicesUrls.UNDO_FRIEND_REQUEST);
    }

    public void cancelFriendRequest(final UserProfilePOJO userProfilePOJO, TextView textView){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id",userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                userProfilePOJO.setMyFriend(4);
                notifyDataSetChanged();
            }
        },"CALL_ADD_FRIEND_API",true).execute(WebServicesUrls.CANCEL_FRIEND_REQUEST);
    }

    public void acceptRequest(final UserProfilePOJO userProfilePOJO, final int position){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id",userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                items.remove(position);
                notifyDataSetChanged();
            }
        },"CALL_ADD_FRIEND_API",true).execute(WebServicesUrls.SEND_FRIEND_REQUEST);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView cv_profile_pic;
        public TextView tv_user_name, tv_add_friend;
        public LinearLayout ll_user;
        public ViewHolder(View itemView) {
            super(itemView);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_add_friend = itemView.findViewById(R.id.tv_add_friend);
            ll_user = itemView.findViewById(R.id.ll_user);
        }
    }
}
