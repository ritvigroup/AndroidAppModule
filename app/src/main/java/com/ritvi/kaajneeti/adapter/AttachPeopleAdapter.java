package com.ritvi.kaajneeti.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.List;

/**
 * Created by sunil on 23-03-2018.
 */

public class AttachPeopleAdapter extends BaseAdapter {

    private Context context;
    private List<UserProfilePOJO> names;

    public AttachPeopleAdapter(Context context, List<UserProfilePOJO> names) {
        this.context = context;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public String getItem(int position) {
        return names.get(position).getUserName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = LayoutInflater.from(context).inflate(R.layout.row_item_word, null);
//
//        TextView tv_user_name = view.findViewById(R.id.tv_user_name);
//        ImageView iv_remove_user = view.findViewById(R.id.iv_remove_user);
//
//        tv_user_name.setText(names.get(position).getUserName());

        TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.row_item_word, null);

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.selector_items);
        drawable.setColorFilter(com.riontech.staggeredtextgridview.utils.ColorGenerator.MATERIAL.getRandomColor(), PorterDuff.Mode.SRC_ATOP);

        view.setBackgroundDrawable(drawable);

        try {
            view.setText(names.get(position).getUserName());
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ((TextView) v).getText().toString(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}