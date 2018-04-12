package com.ritvi.kaajneeti.fragment.contribution;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.adapter.PaymentTransAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentTransPOJO;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 09-04-2018.
 */

public class ContributionReceivedFragment extends Fragment{

    @BindView(R.id.rv_logs)
    RecyclerView rv_logs;
    @BindView(R.id.tv_amount)
    TextView tv_amount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_cont_received,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attachAdapter();
        getPaymentLogs();
    }


    public void getPaymentLogs(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        new WebServiceBaseResponseList<PaymentTransPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PaymentTransPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PaymentTransPOJO> responseListPOJO) {
                paymentTransPOJOS.clear();
                if(responseListPOJO.isSuccess()) {
                    double amount=0;
                    for(PaymentTransPOJO paymentTransPOJO:responseListPOJO.getResultList()){
                        if(paymentTransPOJO.getTransactionStatus().equals("1")) {
                            paymentTransPOJOS.add(paymentTransPOJO);
                            if (!paymentTransPOJO.getDebitOrCredit().equals("0")) {
                                amount += getTransAmount(paymentTransPOJO.getTransactionAmount());
                            }
                        }
                    }

                    tv_amount.setText("Rs. "+String.valueOf(amount));
                }
                paymentTransAdapter.notifyDataSetChanged();
            }

        },PaymentTransPOJO.class,"CALL_PAYMENT_LOGS_API",true).execute(WebServicesUrls.CREDIT_TRANS_LOGS);
    }

    public double getTransAmount(String amount){
        try{
            double trans_amount=Double.parseDouble(amount);
            return trans_amount;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    PaymentTransAdapter paymentTransAdapter;
    List<PaymentTransPOJO> paymentTransPOJOS=new ArrayList<>();

    public void attachAdapter() {
        paymentTransAdapter = new PaymentTransAdapter(getActivity(), this, paymentTransPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_logs.setHasFixedSize(true);
        rv_logs.setAdapter(paymentTransAdapter);
        rv_logs.setLayoutManager(linearLayoutManager);
        rv_logs.setItemAnimator(new DefaultItemAnimator());
    }

}
