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
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.fragment.information.InformationViewFragment;
import com.ritvi.kaajneeti.pojo.analyze.InformationPOJO;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class InformationAnalyzeAdapter extends RecyclerView.Adapter<InformationAnalyzeAdapter.ViewHolder> {
    private List<InformationPOJO> items;
    Activity activity;
    Fragment fragment;

    public InformationAnalyzeAdapter(Activity activity, Fragment fragment, List<InformationPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_information_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_analyze.setText(items.get(position).getInformationSubject());
        holder.tv_id.setText("CID:-"+items.get(position).getInformationId());

        holder.ll_analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(activity, ViewComplaintActivity.class);
//                intent.putExtra("complaint",items.get(position));
//                activity.startActivity(intent);
                if(activity instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) activity;
                    homeActivity.replaceFragmentinFrameHome(new InformationViewFragment(items.get(position)),"InformationViewFragment");
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
        public TextView tv_analyze,tv_id;
        public LinearLayout ll_analyze;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_analyze=itemView.findViewById(R.id.tv_analyze);
            tv_id=itemView.findViewById(R.id.tv_id);
            ll_analyze=itemView.findViewById(R.id.ll_analyze);
        }
    }
}
