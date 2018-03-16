package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class CreatePostImageAdapter extends RecyclerView.Adapter<CreatePostImageAdapter.ViewHolder> {
    private List<String> items;
    Activity activity;
    Fragment fragment;

    public CreatePostImageAdapter(Activity activity, Fragment fragment, List<String> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_create_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(activity.getApplicationContext())
                .load(items.get(position))
                .into(holder.iv_image);

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_image;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_image=itemView.findViewById(R.id.iv_image);
        }
    }
}
