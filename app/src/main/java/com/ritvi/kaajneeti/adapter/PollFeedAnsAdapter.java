package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.interfaces.PollAnsClickInterface;
import com.ritvi.kaajneeti.pojo.home.PollAnsPOJO;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class PollFeedAnsAdapter extends RecyclerView.Adapter<PollFeedAnsAdapter.ViewHolder> {
    private List<PollAnsPOJO> items;
    Activity activity;
    Fragment fragment;
    PollAnsClickInterface pollAnsClickInterface;

    public PollFeedAnsAdapter(Activity activity, Fragment fragment, List<PollAnsPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    public void setOnAnsClicked(PollAnsClickInterface pollAnsClickInterface){
        this.pollAnsClickInterface=pollAnsClickInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_poll_feed_ans_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        boolean is_media=false;
        for(PollAnsPOJO pollAnsPOJO:items){
            if(pollAnsPOJO.getPollAnswerImage().length()>0){
                is_media=true;
            }
        }

        if(is_media){
            holder.iv_poll_image.setVisibility(View.VISIBLE);
        }else{
            holder.iv_poll_image.setVisibility(View.GONE);
        }

        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getPollAnswerImage())
                .placeholder(R.drawable.ic_default_pic)
                .error(R.drawable.ic_default_pic)
                .dontAnimate()
                .into(holder.iv_poll_image);

        if(items.get(position).getPollAnswer().length()>0){

        }else{
            holder.tv_poll_media_ans.setVisibility(View.GONE);
        }

        holder.ll_poll_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pollAnsClickInterface.onAnsclicked(items.get(position).getPollAnswerId());
            }
        });

        holder.tv_poll_media_ans.setText(items.get(position).getPollAnswer());

        holder.itemView.setTag(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_poll_image;
        public TextView tv_poll_media_ans;
        public LinearLayout ll_poll_ans;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_poll_ans=itemView.findViewById(R.id.ll_poll_ans);
            tv_poll_media_ans=itemView.findViewById(R.id.tv_poll_media_ans);
            iv_poll_image=itemView.findViewById(R.id.iv_poll_image);
        }
    }


}
