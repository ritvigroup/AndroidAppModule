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
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.activity.AddComplaintDescriptionActivity;
import com.ritvi.kaajneeti.activity.AttachFragment;
import com.ritvi.kaajneeti.activity.CreateEventActivity;
import com.ritvi.kaajneeti.pojo.event.EventAttachment;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class EventAttachAdapter extends RecyclerView.Adapter<EventAttachAdapter.ViewHolder> {
    private List<EventAttachment> items;
    Activity activity;
    Fragment fragment;

    public EventAttachAdapter(Activity activity, Fragment fragment, List<EventAttachment> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_event_attach, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(!items.get(position).isIs_demo()){
            if(items.get(position).getType().equals(Constants.EVENT_IMAGE_ATTACH)){
                Glide.with(activity.getApplicationContext())
                        .load(items.get(position).getFile_path())
                        .into(holder.iv_image);
            }else if(items.get(position).getType().equals(Constants.EVENT_VIDEO_ATTACH)){
                Glide.with(activity.getApplicationContext())
                        .load(items.get(position).getThumb_path())
                        .into(holder.iv_image);
            }
        }

        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.get(position).isIs_demo()) {
                    if (activity instanceof CreateEventActivity) {
                        CreateEventActivity createEventActivity = (CreateEventActivity) activity;
                        createEventActivity.showAttachDialog();
                    }else if(activity instanceof AddComplaintDescriptionActivity){
                        AddComplaintDescriptionActivity addComplaintDescriptionActivity= (AddComplaintDescriptionActivity) activity;
                        addComplaintDescriptionActivity.showAttachDialog();
                    }else if(fragment!=null){
                        if(fragment instanceof AttachFragment){
                            AttachFragment attachFragment= (AttachFragment) fragment;
                            attachFragment.showAttachDialog();
                        }
                    }
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
        public ImageView iv_image;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_image=itemView.findViewById(R.id.iv_image);
        }
    }
}
