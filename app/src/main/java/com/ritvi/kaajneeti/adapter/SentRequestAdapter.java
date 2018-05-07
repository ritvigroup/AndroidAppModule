package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

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
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.infalte_user_profile_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_user_name.setText(items.get(position).getFirstName() + " " + items.get(position).getLastName());
        holder.tv_email.setText(items.get(position).getEmail());
        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(holder.cv_profile_pic);
        Log.d(TagUtils.getTag(), items.get(position).getFirstName() + " :- " + items.get(position).getMyFriend());

        holder.iv_friend_add.setVisibility(View.GONE);
        holder.ll_cancel.setVisibility(View.GONE);
        holder.ll_friend_request.setVisibility(View.GONE);
        holder.ll_unfriend.setVisibility(View.GONE);
        holder.ll_follow.setVisibility(View.GONE);

        switch (items.get(position).getMyFriend()) {
            case 0:
                holder.iv_friend_add.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.ll_cancel.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.ll_friend_request.setVisibility(View.VISIBLE);
                break;
            case 3:
//                holder.ll_unfriend.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.ll_follow.setVisibility(View.VISIBLE);
                break;
            case -1:
                break;
        }

        holder.iv_friend_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFriendRequest(items.get(position));
            }
        });
        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptRequest(items.get(position));
            }
        });
        holder.btn_reject_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelFriendRequest(items.get(position));
            }
        });
        holder.btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.btn_cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoFriendRequest(items.get(position));
            }
        });
        holder.btn_unfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) activity;
                    homeActivity.showUserProfileFragment(items.get(position).getUserId(), items.get(position).getUserProfileId());
                }
                Log.d(TagUtils.getTag(), "friend status:-" + items.get(position).getMyFriend());
            }
        });

//        holder.itemView.setTag(items.get(position));
    }


    public void sendFriendRequest(final UserProfilePOJO userProfilePOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        userProfilePOJO.setMyFriend(1);
                        notifyDataSetChanged();
                    } else {
                        ToastClass.showShortToast(activity.getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.SEND_FRIEND_REQUEST);
    }

    public void undoFriendRequest(final UserProfilePOJO userProfilePOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        userProfilePOJO.setMyFriend(0);
                        notifyDataSetChanged();
                    } else {
                        ToastClass.showShortToast(activity.getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.UNDO_FRIEND_REQUEST);
    }

    public void cancelFriendRequest(final UserProfilePOJO userProfilePOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        userProfilePOJO.setMyFriend(4);
                        notifyDataSetChanged();
                    } else {
                        ToastClass.showShortToast(activity.getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.CANCEL_FRIEND_REQUEST);
    }

    public void acceptRequest(final UserProfilePOJO userProfilePOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("friend_user_profile_id", userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        userProfilePOJO.setMyFriend(3);
                        notifyDataSetChanged();
                    } else {
                        ToastClass.showShortToast(activity.getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_ADD_FRIEND_API", true).execute(WebServicesUrls.SEND_FRIEND_REQUEST);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView cv_profile_pic;
        public TextView tv_user_name, tv_email;
        public ImageView iv_friend_add;
        public LinearLayout ll_user;
        public LinearLayout ll_friend_request;
        public LinearLayout ll_follow;
        public LinearLayout ll_cancel;
        public LinearLayout ll_unfriend;
        public Button btn_accept, btn_reject_friend, btn_follow, btn_cancel_request, btn_unfriend;

        public ViewHolder(View itemView) {
            super(itemView);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            iv_friend_add = itemView.findViewById(R.id.iv_friend_add);
            tv_email = itemView.findViewById(R.id.tv_email);
            ll_user = itemView.findViewById(R.id.ll_user);
            ll_friend_request = itemView.findViewById(R.id.ll_friend_request);
            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_reject_friend = itemView.findViewById(R.id.btn_reject_friend);
            btn_follow = itemView.findViewById(R.id.btn_follow);
            ll_follow = itemView.findViewById(R.id.ll_follow);
            ll_cancel = itemView.findViewById(R.id.ll_cancel);
            ll_unfriend = itemView.findViewById(R.id.ll_unfriend);
            btn_unfriend = itemView.findViewById(R.id.btn_unfriend);
            btn_cancel_request = itemView.findViewById(R.id.btn_cancel_request);
        }
    }
}
