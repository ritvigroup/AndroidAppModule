package com.ritvi.kaajneeti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.pojo.user.favorite.FavoriteResultPOJO;

import java.util.ArrayList;

/**
 * Created by sunil on 29-12-2017.
 */

public class CustomAutoCompleteAdapter extends ArrayAdapter<FavoriteResultPOJO> {
    ArrayList<FavoriteResultPOJO> customers, tempCustomer, suggestions;
    Context context;

    public CustomAutoCompleteAdapter(Context context, ArrayList<FavoriteResultPOJO> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        this.customers = objects;
        this.tempCustomer = new ArrayList<FavoriteResultPOJO>(objects);
        this.suggestions = new ArrayList<FavoriteResultPOJO>(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FavoriteResultPOJO customer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inflate_leaders, parent, false);
        }
        TextView tv_leader_name = (TextView) convertView.findViewById(R.id.tv_leader_name);
        TextView tv_leader_email = (TextView) convertView.findViewById(R.id.tv_leader_email);
        tv_leader_name.setText(customer.getUserProfileDetailPOJO().getUserInfoPOJO().getUserName());
        tv_leader_email.setText("email");

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            FavoriteResultPOJO customer = (FavoriteResultPOJO) resultValue;
            return customer.getUserProfileDetailPOJO().getUserInfoPOJO().getUserName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Object people : tempCustomer) {
                    FavoriteResultPOJO favoriteResultPOJO= (FavoriteResultPOJO) people;
                    if (favoriteResultPOJO.getUserProfileDetailPOJO().getUserInfoPOJO().getUserName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
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
            ArrayList<FavoriteResultPOJO> c = (ArrayList<FavoriteResultPOJO>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (FavoriteResultPOJO cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}