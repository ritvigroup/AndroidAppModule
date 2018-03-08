package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ritvi.kaajneeti.R;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class ConnectionInvitationAdapter extends RecyclerView.Adapter<ConnectionInvitationAdapter.ViewHolder> {
    private List<String> items;
    Activity activity;
    Fragment fragment;

    public ConnectionInvitationAdapter(Activity activity, Fragment fragment, List<String> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_conn_invitation_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.ll_report_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_report_item;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_report_item=itemView.findViewById(R.id.ll_report_item);
        }
    }
}
