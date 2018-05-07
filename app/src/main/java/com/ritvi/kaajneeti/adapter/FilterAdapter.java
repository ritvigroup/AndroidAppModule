package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.fragment.search.SearchActivityShowFragment;
import com.ritvi.kaajneeti.fragment.search.SearchPostShowFragment;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    private List<String> items;
    Activity activity;
    Fragment fragment;
    String type;
    String selected;

    public FilterAdapter(Activity activity, Fragment fragment, List<String> items, String type, String selected) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
        this.type = type;
        this.selected = selected;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_filter_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_filter.setText(items.get(position));
        holder.ll_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.radio_filter.isChecked()) {
                    holder.radio_filter.setChecked(false);
                } else {
                    holder.radio_filter.setChecked(true);
                }
            }
        });

        if (selected.equalsIgnoreCase(items.get(position))) {
            holder.radio_filter.setChecked(true);
        }
        holder.radio_filter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (fragment instanceof SearchActivityShowFragment) {
                        SearchActivityShowFragment searchActivityShowFragment = (SearchActivityShowFragment) fragment;
                        searchActivityShowFragment.selectCity(items.get(position), type);
                    } else if (fragment instanceof SearchPostShowFragment) {
                        SearchPostShowFragment searchPostShowFragment = (SearchPostShowFragment) fragment;
                        searchPostShowFragment.selectCity(items.get(position));
                    }
                } else {

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_filter;
        public RadioButton radio_filter;
        public TextView tv_filter;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_filter = itemView.findViewById(R.id.ll_filter);
            radio_filter = itemView.findViewById(R.id.radio_filter);
            tv_filter = itemView.findViewById(R.id.tv_filter);
        }
    }
}
