package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.pojo.user.RequestProfilePOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfileDetailPOJO;
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
    private List<RequestProfilePOJO> items;
    Activity activity;
    Fragment fragment;

    public SentRequestAdapter(Activity activity, Fragment fragment, List<RequestProfilePOJO> items) {
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

//        holder.ll_report_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

//        holder.ll_report_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        holder.tv_user_name.setText(items.get(position).getUserProfileDetailPOJO().getUserProfilePOJO().getUserName());
        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getUserProfileDetailPOJO().getUserProfilePOJO().getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(holder.cv_profile_pic);

        switch (items.get(position).getUserProfileDetailPOJO().getUserProfilePOJO().getCitizenProfilePOJO().getMyFriend()) {
            case "0":holder.tv_add_friend.setText("Add Friend");
                break;
            case "1":holder.tv_add_friend.setText("Cancel Friend Request");
                break;
            case "2":holder.tv_add_friend.setText("Accept Request");
                break;
            case "3":holder.tv_add_friend.setText("");
                break;
        }

        holder.tv_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (items.get(position).getUserProfileDetailPOJO().getUserProfilePOJO().getCitizenProfilePOJO().getMyFriend()) {
                    case "0":
//                        holder.tv_add_friend.setText("Add Friend");
//                            sendFriendRequest(items.get(position),holder.tv_add_friend);
                        break;
                    case "1":
                        undoFriendRequest(items.get(position).getUserProfileDetailPOJO(),position);
//                        holder.tv_add_friend.setText("Friend Request Sent");
                        break;
                    case "2":
//                        holder.tv_add_friend.setText("Accept Request");
//                        cancelFriendRequest(items.get(position),holder.tv_add_friend);
                        break;
                    case "3":
//                        holder.tv_add_friend.setText("");
                        break;
                }

            }
        });

        holder.itemView.setTag(items.get(position));
    }


    public void undoFriendRequest(final UserProfileDetailPOJO userProfileDetailPOJO, final int position){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePojo.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",Constants.userProfilePojo.getCitizenProfilePOJO().getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id",userProfileDetailPOJO.getUserProfilePOJO().getCitizenProfilePOJO().getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
//                userProfileDetailPOJO.getUserProfilePOJO().getCitizenProfilePOJO().setMyFriend("0");
                items.remove(position);
                notifyDataSetChanged();
            }
        },"CALL_ADD_FRIEND_API",true).execute(WebServicesUrls.UNDO_FRIEND_REQUEST);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView cv_profile_pic;
        public TextView tv_user_name, tv_add_friend;

        public ViewHolder(View itemView) {
            super(itemView);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_add_friend = itemView.findViewById(R.id.tv_add_friend);
        }
    }
}
