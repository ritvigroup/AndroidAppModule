package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.interfaces.PollAnsClickInterface;
import com.ritvi.kaajneeti.pojo.home.PollAnsPOJO;
import com.ritvi.kaajneeti.pojo.home.PollPOJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class PollFeedAnalyzeAdapter extends RecyclerView.Adapter<PollFeedAnalyzeAdapter.ViewHolder> {
    private List<PollAnsPOJO> items = new ArrayList<>();
    Activity activity;
    Fragment fragment;
    PollAnsClickInterface pollAnsClickInterface;
    PollPOJO pollPOJO;

    public PollFeedAnalyzeAdapter(Activity activity, Fragment fragment, PollPOJO pollPOJO) {
        this.items.clear();
        this.items.addAll(pollPOJO.getPollAnsPOJOS());
        this.activity = activity;
        this.fragment = fragment;
        this.pollPOJO = pollPOJO;
    }

    public void setOnAnsClicked(PollAnsClickInterface pollAnsClickInterface) {
        this.pollAnsClickInterface = pollAnsClickInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_poll_feed_ans_analyze_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        boolean is_media = false;
        for (PollAnsPOJO pollAnsPOJO : items) {
            if (pollAnsPOJO.getPollAnswerImage().length() > 0) {
                is_media = true;
            }
        }

        if (is_media) {
            holder.iv_poll_image.setVisibility(View.VISIBLE);
        } else {
            holder.iv_poll_image.setVisibility(View.GONE);
        }

        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getPollAnswerImage())
                .placeholder(R.drawable.ic_default_pic)
                .error(R.drawable.ic_default_pic)
                .dontAnimate()
                .into(holder.iv_poll_image);

        if (items.get(position).getPollAnswer().length() > 0) {

        } else {
            holder.tv_poll_media_ans.setVisibility(View.GONE);
        }

        holder.ll_poll_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pollAnsClickInterface.onAnsclicked(items.get(position).getPollAnswerId());
            }
        });


        try {
            int totalParticipation=pollPOJO.getPollTotalParticipation();
            int progress=(items.get(position).getTotalAnswerdMe()/totalParticipation)*100;
            holder.progress.setProgress(progress);
            holder.tv_percentage.setText("   "+String.valueOf(progress)+" %   ");
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        public TextView tv_percentage;
        public LinearLayout ll_poll_ans;
        public ProgressBar progress;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_poll_ans = itemView.findViewById(R.id.ll_poll_ans);
            tv_poll_media_ans = itemView.findViewById(R.id.tv_poll_media_ans);
            tv_percentage = itemView.findViewById(R.id.tv_percentage);
            iv_poll_image = itemView.findViewById(R.id.iv_poll_image);
            progress = itemView.findViewById(R.id.progress);
        }
    }


}
