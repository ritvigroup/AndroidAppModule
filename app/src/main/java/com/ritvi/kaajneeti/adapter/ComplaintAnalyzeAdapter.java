package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.ViewComplaintActivity;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class ComplaintAnalyzeAdapter extends RecyclerView.Adapter<ComplaintAnalyzeAdapter.ViewHolder> {
    private List<ComplaintPOJO> items;
    Activity activity;
    Fragment fragment;

    public ComplaintAnalyzeAdapter(Activity activity, Fragment fragment, List<ComplaintPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_complaint_analyze_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_analyze.setText(items.get(position).getComplaintSubject());
        holder.tv_id.setText("CID:-"+items.get(position).getComplaintId());

        holder.ll_analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, ViewComplaintActivity.class);
                intent.putExtra("complaint",items.get(position));
                activity.startActivity(intent);
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_analyze,tv_id;
        public LinearLayout ll_analyze;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_analyze=itemView.findViewById(R.id.tv_analyze);
            tv_id=itemView.findViewById(R.id.tv_id);
            ll_analyze=itemView.findViewById(R.id.ll_analyze);
        }
    }
}
