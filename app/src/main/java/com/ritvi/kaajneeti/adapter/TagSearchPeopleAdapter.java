package com.ritvi.kaajneeti.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;

import java.util.ArrayList;

/**
 * Created by sunil on 29-12-2017.
 */

public class TagSearchPeopleAdapter extends ArrayAdapter<UserInfoPOJO> {
    ArrayList<UserInfoPOJO> customers, tempCustomer, suggestions ;
    Context context;
    public TagSearchPeopleAdapter(Context context, ArrayList<UserInfoPOJO> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context=context;
        this.customers = objects;
        this.tempCustomer = new ArrayList<UserInfoPOJO>(objects);
        this.suggestions = new ArrayList<UserInfoPOJO>(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserInfoPOJO customer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inflate_tag_user_item, parent, false);
        }
        TextView tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
        ImageView iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
        if (tv_user_name != null)
            tv_user_name.setText(customer.getUserName());
        if (iv_user != null)
            Glide.with(context)
            .load(customer.getProfilePhotoPath())
            .placeholder(R.drawable.ic_default_profile_pic)
            .error(R.drawable.ic_default_profile_pic)
            .into(iv_user);
        // Now assign alternate color for rows
        if (position % 2 == 0)
            convertView.setBackgroundColor(Color.parseColor("#FAFAFA"));
        else
            convertView.setBackgroundColor(Color.parseColor("#FFFAAA"));

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            UserInfoPOJO customer = (UserInfoPOJO) resultValue;
            return customer.getUserName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (UserInfoPOJO people : tempCustomer) {
                    if (people.getUserName().toLowerCase().startsWith(constraint.toString().toLowerCase())
                            ||people.getUserEmail().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                Log.d(TagUtils.getTag(),"filter size:-"+suggestions.size());
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<UserInfoPOJO> c = (ArrayList<UserInfoPOJO>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (UserInfoPOJO cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}