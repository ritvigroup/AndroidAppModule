package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.pojo.payment.PointTransLogPOJO;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class PointLogAdapter extends RecyclerView.Adapter<PointLogAdapter.ViewHolder> {
    private List<PointTransLogPOJO> items;
    Activity activity;
    Fragment fragment;
    int device_height = 0;

    public PointLogAdapter(Activity activity, Fragment fragment, List<PointTransLogPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_point_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_remark.setText(items.get(position).getTransactionComment());
        holder.tv_date.setText(items.get(position).getTransactionDate());
        holder.tv_point.setText(items.get(position).getTransactionPoint());
        if(items.get(position).getDebitOrCredit().equals("1")) {
            holder.tv_type.setText("credit");
        }else{
            holder.tv_type.setText("debit");
        }

//        holder.ll_leader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showLeaderProfile(items.get(position));
//            }
//        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_date;
        public TextView tv_type;
        public TextView tv_point;
        public TextView tv_remark;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_point = (TextView) itemView.findViewById(R.id.tv_point);
            tv_remark = (TextView) itemView.findViewById(R.id.tv_remark);
        }
    }
}
