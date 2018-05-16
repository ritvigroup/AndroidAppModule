package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.fragment.profile.UserProfileFragment;
import com.ritvi.kaajneeti.pojo.complaint.ComplaintHistoryPOJO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class ComplaintHistoryAdapter extends RecyclerView.Adapter<ComplaintHistoryAdapter.ViewHolder> {
    private List<ComplaintHistoryPOJO> items;
    Activity activity;
    Fragment fragment;

    public ComplaintHistoryAdapter(Activity activity, Fragment fragment, List<ComplaintHistoryPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_complaint_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_date.setText(items.get(position).getAddedOn());
        holder.tv_description.setText(items.get(position).getHistoryDescription());
        holder.tv_title.setText(items.get(position).getHistoryTitle());

        String profile_name = "By - <b>" + items.get(position).getComplaintHistoryProfile().getFirstName()+" "+items.get(position).getComplaintHistoryProfile().getLastName() + "</b>";
        holder.tv_profile_user.setText(Html.fromHtml(profile_name));

        if(items.get(position).getComplaintHistoryAttachment().size()>0){
            holder.tv_attachments.setText(items.get(position).getComplaintHistoryAttachment().size()+" Attachments");
        }else{
            holder.tv_attachments.setVisibility(View.GONE);
        }

        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getComplaintHistoryProfile().getProfilePhotoPath())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(holder.cv_profile_pic);

        holder.cv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) activity;
                    homeActivity.replaceFragmentinFrameHome(new UserProfileFragment(items.get(position).getComplaintHistoryProfile().getUserId(),items.get(position).getComplaintHistoryProfile().getUserProfileId()),"userProfileFragment");
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
        public TextView tv_title, tv_description, tv_date, tv_profile_user,tv_attachments;
        public CircleImageView cv_profile_pic;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_profile_user = itemView.findViewById(R.id.tv_profile_user);
            tv_attachments = itemView.findViewById(R.id.tv_attachments);
            cv_profile_pic = itemView.findViewById(R.id.cv_profile_pic);
        }
    }
}
