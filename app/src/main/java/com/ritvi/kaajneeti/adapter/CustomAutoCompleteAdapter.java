package com.ritvi.kaajneeti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.pojo.user.UserInfoPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.ArrayList;

/**
 * Created by sunil on 29-12-2017.
 */

public class CustomAutoCompleteAdapter extends ArrayAdapter<UserProfilePOJO> {
    ArrayList<UserProfilePOJO> customers, tempCustomer, suggestions;
    Context context;

    public CustomAutoCompleteAdapter(Context context, ArrayList<UserProfilePOJO> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        this.customers = objects;
        this.tempCustomer = new ArrayList<UserProfilePOJO>(objects);
        this.suggestions = new ArrayList<UserProfilePOJO>(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserProfilePOJO customer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inflate_leaders, parent, false);
        }
        TextView tv_leader_name = (TextView) convertView.findViewById(R.id.tv_leader_name);
        TextView tv_leader_email = (TextView) convertView.findViewById(R.id.tv_leader_email);
        tv_leader_name.setText(customer.getFirstName() + " " + customer.getLastName());
        tv_leader_email.setText(customer.getEmail());

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            UserProfilePOJO customer = (UserProfilePOJO) resultValue;
            return customer.getFirstName()+" "+customer.getLastName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Object people : tempCustomer) {
                    UserProfilePOJO favoriteResultPOJO = (UserProfilePOJO) people;
                    if (favoriteResultPOJO.getFirstName().toLowerCase().startsWith(constraint.toString().toLowerCase())
                            || favoriteResultPOJO.getLastName().toLowerCase().startsWith(constraint.toString().toLowerCase())
                            || favoriteResultPOJO.getEmail().toLowerCase().startsWith(constraint.toString().toLowerCase())
                            || favoriteResultPOJO.getMobile().toLowerCase().startsWith(constraint.toString().toLowerCase())
                            || favoriteResultPOJO.getAltMobile().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(favoriteResultPOJO);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<UserProfilePOJO> c = (ArrayList<UserProfilePOJO>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (UserProfilePOJO cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}