package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.pojo.complaint.ComplaintHistoryPOJO;

import java.util.List;

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


        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_description, tv_date, tv_profile_user,tv_attachments;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_profile_user = itemView.findViewById(R.id.tv_profile_user);
            tv_attachments = itemView.findViewById(R.id.tv_attachments);
        }
    }
}
