package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.pojo.payment.PaymentTransPOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentTypePOJO;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class PaymentTransAdapter extends RecyclerView.Adapter<PaymentTransAdapter.ViewHolder>{
    private List<PaymentTypePOJO> items;
    Activity activity;
    Fragment fragment;
    int device_height = 0;

    public PaymentTransAdapter(Activity activity, Fragment fragment, List<PaymentTypePOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_payment_trans_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PaymentTransPOJO paymentTransPOJO;
        if(items.get(position).getFeedtype().equalsIgnoreCase("point")){
            paymentTransPOJO=items.get(position).getPointdataTransPOJO();
            holder.tv_trans_type.setText("Points");
        }else{
            paymentTransPOJO=items.get(position).getPaymentTransPOJO();
            holder.tv_trans_type.setText("Money");
        }

        if(paymentTransPOJO!=null) {
            holder.tv_amount.setText(paymentTransPOJO.getTransactionAmount());
            holder.tv_date.setText(paymentTransPOJO.getAddedOnTime().split(" ")[0]);
            if (paymentTransPOJO.getTransactionComment().length() > 10) {
                holder.tv_remarks.setText(paymentTransPOJO.getTransactionComment().substring(0, 10));
            } else {
                holder.tv_remarks.setText(paymentTransPOJO.getTransactionComment());
            }
            if (paymentTransPOJO.getDebitOrCredit().equals("0")) {
                holder.tv_type.setText("Debit");
            } else {
                holder.tv_type.setText("Credit");
            }
        }
        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_date;
        public TextView tv_type;
        public TextView tv_amount;
        public TextView tv_remarks;
        public TextView tv_trans_type;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_remarks = (TextView) itemView.findViewById(R.id.tv_remarks);
            tv_trans_type = (TextView) itemView.findViewById(R.id.tv_trans_type);
        }
    }
}
