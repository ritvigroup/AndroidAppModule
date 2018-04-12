package com.ritvi.kaajneeti.fragment.homeactivity;

import android.app.Dialog;
import android.content.Intent;
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
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.adapter.PaymentListAdapter;
import com.ritvi.kaajneeti.adapter.PaymentTransAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentDetailPOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentPOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentTransPOJO;
import com.ritvi.kaajneeti.testing.PayUMoneyIntegration;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 06-04-2018.
 */

public class WalletFragment extends Fragment{

    @BindView(R.id.rv_transactions)
    RecyclerView rv_transactions;
    @BindView(R.id.tv_topup)
    TextView tv_topup;
    @BindView(R.id.tv_amount)
    TextView tv_amount;

    List<PaymentTransPOJO> paymentTransPOJOS=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_wallet,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpaymentDialog();
            }
        });
        attachAdapter();
        getPaymentLogs();
    }

    public void getPaymentLogs(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        new WebServiceBaseResponseList<PaymentTransPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PaymentTransPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PaymentTransPOJO> responseListPOJO) {
                paymentTransPOJOS.clear();
                if(responseListPOJO.isSuccess()) {
                    paymentTransPOJOS.addAll(responseListPOJO.getResultList());
                    double amount=0;
                    for(PaymentTransPOJO paymentTransPOJO:responseListPOJO.getResultList()){
                        if(paymentTransPOJO.getDebitOrCredit().equals("0")){
                            amount-=getTransAmount(paymentTransPOJO.getTransactionAmount());
                        }else{
                            amount+=getTransAmount(paymentTransPOJO.getTransactionAmount());
                        }
                    }

                    tv_amount.setText("Rs. "+String.valueOf(amount));
                }
                paymentTransAdapter.notifyDataSetChanged();
            }

        },PaymentTransPOJO.class,"CALL_PAYMENT_LOGS_API",true).execute(WebServicesUrls.GET_PAYMENT_TRANS_LOGS);
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
    public void attachAdapter() {
        paymentTransAdapter = new PaymentTransAdapter(getActivity(), this, paymentTransPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_transactions.setHasFixedSize(true);
        rv_transactions.setAdapter(paymentTransAdapter);
        rv_transactions.setLayoutManager(linearLayoutManager);
        rv_transactions.setItemAnimator(new DefaultItemAnimator());
    }

    public void showpaymentDialog() {
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", UtilityFunction.getProfileID(Constants.userInfoPOJO)));
        new WebServiceBaseResponseList<PaymentPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PaymentPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PaymentPOJO> responseListPOJO) {
                if(responseListPOJO.isSuccess()){
                    showPaymentOptions(responseListPOJO.getResultList());
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),responseListPOJO.getMessage());
                }
            }
        },PaymentPOJO.class,"GET_ALL_PAYMENT_METHODS",true).execute(WebServicesUrls.ALL_PAYMENT_GATEWAY);
    }

    private void showPaymentOptions(List<PaymentPOJO> resultList) {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_payment_options);
        dialog1.setTitle("Select");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        RecyclerView rv_payment_options=dialog1.findViewById(R.id.rv_payment_options);
        Button btn_cancel=dialog1.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        PaymentListAdapter paymentListAdapter = new PaymentListAdapter(getActivity(), this, resultList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_payment_options.setHasFixedSize(true);
        rv_payment_options.setAdapter(paymentListAdapter);
        rv_payment_options.setLayoutManager(linearLayoutManager);
        rv_payment_options.setItemAnimator(new DefaultItemAnimator());

    }

    public void callPaymentDetailAPI(final PaymentPOJO paymentPOJO) {
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id",UtilityFunction.getProfileID(Constants.userInfoPOJO)));
        nameValuePairs.add(new BasicNameValuePair("payment_gateway_id",paymentPOJO.getPaymentGatewayId()));
        new WebServiceBaseResponse<PaymentDetailPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<PaymentDetailPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<PaymentDetailPOJO> responsePOJO) {
                if(responsePOJO.isSuccess()){
                    Intent intent=new Intent(getActivity(), PayUMoneyIntegration.class);
                    intent.putExtra("paymentPOJO",paymentPOJO);
                    startActivity(intent);
                }
            }
        },PaymentDetailPOJO.class,"GET_PAYMENT_DETAIL",true).execute(WebServicesUrls.PAYMENT_GATEWAY_API_DETAILS);
    }
}
