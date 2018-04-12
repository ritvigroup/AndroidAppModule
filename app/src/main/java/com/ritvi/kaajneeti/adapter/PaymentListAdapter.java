package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.fragment.homeactivity.ContributeFragment;
import com.ritvi.kaajneeti.fragment.homeactivity.WalletFragment;
import com.ritvi.kaajneeti.pojo.payment.PaymentPOJO;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.ViewHolder>{
    private List<PaymentPOJO> items;
    Activity activity;
    Fragment fragment;
    int device_height = 0;

    public PaymentListAdapter(Activity activity, Fragment fragment, List<PaymentPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_payment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.btn_payment_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragment!=null&&fragment instanceof ContributeFragment){
                    ContributeFragment contributeFragment= (ContributeFragment) fragment;
//                    contributeFragment.callPaymentDetailAPI(items.get(position));
                }else if(fragment!=null&&fragment instanceof WalletFragment){
                    WalletFragment walletFragment= (WalletFragment) fragment;
                    walletFragment.callPaymentDetailAPI(items.get(position));
                }
            }
        });

        holder.btn_payment_name.setText(items.get(position).getPaymentGatewayName());

        holder.itemView.setTag(items.get(position));
    }


    private final String TAG = getClass().getSimpleName();

    @Override
    public int getItemCount() {
        return items.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public Button btn_payment_name;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_payment_name = (Button) itemView.findViewById(R.id.btn_payment_name);
        }
    }
}
