package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.fragment.homeactivity.InvestigateFragment;
import com.ritvi.kaajneeti.pojo.analyze.AnalyzeCategoryPOJO;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class AnalyzeCategoryAdapter extends RecyclerView.Adapter<AnalyzeCategoryAdapter.ViewHolder> {
    private List<AnalyzeCategoryPOJO> items;
    Activity activity;
    Fragment fragment;

    public AnalyzeCategoryAdapter(Activity activity, Fragment fragment, List<AnalyzeCategoryPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_analyze_category_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_counts.setText(items.get(position).getCount());
        holder.tv_type.setText(items.get(position).getType());

        holder.ll_analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.get(position).getType().equalsIgnoreCase("Complaints")){
                    if(fragment instanceof InvestigateFragment){
                        InvestigateFragment investigateFragment= (InvestigateFragment) fragment;
                        investigateFragment.showComplaintListFragment();
                    }
                }else if(items.get(position).getType().equalsIgnoreCase("Suggestions")){
                    if(fragment instanceof InvestigateFragment){
                        InvestigateFragment investigateFragment= (InvestigateFragment) fragment;
                        investigateFragment.showSuggestionListFragment();
                    }
                }else if(items.get(position).getType().equalsIgnoreCase("Informations")){
                    if(fragment instanceof InvestigateFragment){
                        InvestigateFragment investigateFragment= (InvestigateFragment) fragment;
                        investigateFragment.showInformationFragment();
                    }
                }else if(items.get(position).getType().equalsIgnoreCase("Post")){
                    if(fragment instanceof InvestigateFragment){
                        InvestigateFragment investigateFragment= (InvestigateFragment) fragment;
                        investigateFragment.showAllPostFragment();
                    }
                }else if(items.get(position).getType().equalsIgnoreCase("Event")){
                    if(fragment instanceof InvestigateFragment){
                        InvestigateFragment investigateFragment= (InvestigateFragment) fragment;
                        investigateFragment.showALLEventFragment();
                    }
                }else if(items.get(position).getType().equalsIgnoreCase("Poll")){
                    if(fragment instanceof InvestigateFragment){
                        InvestigateFragment investigateFragment= (InvestigateFragment) fragment;
                        investigateFragment.shoAllPollFragment();
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
        public TextView tv_counts,tv_type;
        public LinearLayout ll_analyze;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_counts=itemView.findViewById(R.id.tv_counts);
            tv_type=itemView.findViewById(R.id.tv_type);
            ll_analyze=itemView.findViewById(R.id.ll_analyze);
        }
    }
}
