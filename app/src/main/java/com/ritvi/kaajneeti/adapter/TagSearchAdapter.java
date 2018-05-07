package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.fragment.search.AllSearchFragment;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class TagSearchAdapter extends RecyclerView.Adapter<TagSearchAdapter.ViewHolder>{
    private List<String> items;
    Activity activity;
    Fragment fragment;

    public TagSearchAdapter(Activity activity, Fragment fragment, List<String> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_tag_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.btn_tag.setText(items.get(position));
        holder.btn_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragment instanceof AllSearchFragment){
                    AllSearchFragment allSearchFragment= (AllSearchFragment) fragment;
                    allSearchFragment.showPosts(items.get(position));
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
        public Button btn_tag;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_tag = (Button) itemView.findViewById(R.id.btn_tag);
        }
    }
}
