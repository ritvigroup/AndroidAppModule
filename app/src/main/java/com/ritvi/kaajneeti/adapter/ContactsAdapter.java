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

import com.bumptech.glide.Glide;
import com.github.tamir7.contacts.Contact;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.activity.ProfilePageActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<Contact> items;
    Activity activity;
    Fragment fragment;

    public ContactsAdapter(Activity activity, Fragment fragment, List<Contact> items) {
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

        if (items.get(position).getPhoneNumbers().size() > 0) {
            holder.tv_voter_name.setText(items.get(position).getPhoneNumbers().get(0).getNumber());
        }
        if (items.get(position).getEmails().size() > 0) {
            holder.tv_email.setText(items.get(position).getEmails().get(0).getAddress());
        }
        Glide.with(activity.getApplicationContext())
                .load(items.get(position).getPhotoUri())
                .placeholder(R.drawable.ic_default_profile_pic)
                .error(R.drawable.ic_default_profile_pic)
                .dontAnimate()
                .into(holder.cv_profile_pic);
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
        public TextView tv_email;
        public CircleImageView cv_profile_pic;
        public LinearLayout ll_user;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_voter_name = (TextView) itemView.findViewById(R.id.tv_voter_name);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            cv_profile_pic = (CircleImageView) itemView.findViewById(R.id.cv_profile_pic);
            ll_user = (LinearLayout) itemView.findViewById(R.id.ll_user);
        }
    }
}
