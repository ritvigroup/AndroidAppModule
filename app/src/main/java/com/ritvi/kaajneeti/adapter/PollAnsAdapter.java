package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.fragment.CreatePollFragment;
import com.ritvi.kaajneeti.pojo.PollMediaAns;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class PollAnsAdapter extends RecyclerView.Adapter<PollAnsAdapter.ViewHolder> {
    private List<PollMediaAns> items;
    Activity activity;
    Fragment fragment;

    public PollAnsAdapter(Activity activity, Fragment fragment, List<PollMediaAns> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_poll_media_ans, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        boolean is_media=false;
        for(PollMediaAns pollMediaAns:items){
            if(pollMediaAns.getFile_path().length()>0){
                is_media=true;
            }
        }

        if(is_media){
            holder.iv_poll_image.setVisibility(View.VISIBLE);
        }else{
            holder.iv_poll_image.setVisibility(View.GONE);
        }

        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getFile_path())
                .placeholder(R.drawable.ic_default_pic)
                .error(R.drawable.ic_default_pic)
                .dontAnimate()
                .into(holder.iv_poll_image);

        holder.iv_image_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragment instanceof CreatePollFragment){
                    CreatePollFragment createPollFragment= (CreatePollFragment) fragment;
                    createPollFragment.selectMedia(position);
                }
            }
        });

        holder.et_poll_media_ans.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                items.get(position).setAns(holder.et_poll_media_ans.getText().toString());
            }
        });

        holder.et_poll_media_ans.setText(items.get(position).getAns());

        holder.itemView.setTag(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_poll_image;
        public ImageView iv_image_pick;
        public EditText et_poll_media_ans;
        public LinearLayout ll_poll_ans;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_poll_ans=itemView.findViewById(R.id.ll_poll_ans);
            et_poll_media_ans=itemView.findViewById(R.id.et_poll_media_ans);
            iv_poll_image=itemView.findViewById(R.id.iv_poll_image);
            iv_image_pick=itemView.findViewById(R.id.iv_image_pick);
        }
    }


}
