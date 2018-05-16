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
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.fragment.post.PostViewFragment;
import com.ritvi.kaajneeti.pojo.home.PostAttachmentPOJO;
import com.ritvi.kaajneeti.pojo.home.PostPOJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class PostAttachmentViewAdapter extends RecyclerView.Adapter<PostAttachmentViewAdapter.ViewHolder> {
    private List<PostAttachmentPOJO> items=new ArrayList<>();
    Activity activity;
    Fragment fragment;
    PostPOJO postPOJO;

    public PostAttachmentViewAdapter(Activity activity, Fragment fragment, PostPOJO postPOJO) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
        this.postPOJO=postPOJO;
        items.clear();
        items.addAll(postPOJO.getPostAttachment());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_post_attachment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getAttachmentFile())
                .placeholder(R.drawable.ic_default_pic)
                .error(R.drawable.ic_default_pic)
                .dontAnimate()
                .into(holder.iv_post_attachment);

        holder.iv_post_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) activity;
                    homeActivity.replaceFragmentinFrameHome(new PostViewFragment(postPOJO),"PostViewFragment");
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
        public ImageView iv_post_attachment;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_post_attachment=itemView.findViewById(R.id.iv_post_attachment);
        }
    }
}
