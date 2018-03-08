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
import com.ritvi.kaajneeti.activity.ProfilePageActivity;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class SearchVoterAdapter extends RecyclerView.Adapter<SearchVoterAdapter.ViewHolder> {
    private List<String> items;
    Activity activity;
    Fragment fragment;

    public SearchVoterAdapter(Activity activity, Fragment fragment, List<String> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_search_voter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_voter_name.setText(items.get(position));
        holder.ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, ProfilePageActivity.class));
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_voter_name;
        public LinearLayout ll_user;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_voter_name = (TextView) itemView.findViewById(R.id.tv_voter_name);
            ll_user = (LinearLayout) itemView.findViewById(R.id.ll_user);
        }
    }
}
