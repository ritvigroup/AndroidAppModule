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
import com.ritvi.kaajneeti.Util.UtilityFunction;

import java.io.File;
import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class MediaListAdapter extends RecyclerView.Adapter<MediaListAdapter.ViewHolder> {
    private List<String> items;
    Activity activity;
    Fragment fragment;

    public MediaListAdapter(Activity activity, Fragment fragment, List<String> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_media_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String file_path = "";
        if (items.get(position).contains(".mp4")
                || items.get(position).contains(".MP4")) {
            file_path = UtilityFunction.saveThumbFile(new File(items.get(position)));
        } else {
            file_path = items.get(position);
        }

        Glide.with(activity.getApplicationContext())
                .load(file_path)
                .placeholder(R.drawable.ic_default_pic)
                .error(R.drawable.ic_default_pic)
                .dontAnimate()
                .into(holder.iv_media);

        holder.iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_media, iv_close;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_media = itemView.findViewById(R.id.iv_media);
            iv_close = itemView.findViewById(R.id.iv_close);
        }
    }
}
