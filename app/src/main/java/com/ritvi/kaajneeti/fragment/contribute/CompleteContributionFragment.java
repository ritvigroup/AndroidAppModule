package com.ritvi.kaajneeti.fragment.contribute;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.PaymentListAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentPOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentTransPOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentTypePOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class CompleteContributionFragment extends Fragment {

    UserProfilePOJO userProfilePOJO;
    String total_contribution;

    public CompleteContributionFragment(UserProfilePOJO userProfilePOJO, String total_contribution) {
        this.userProfilePOJO = userProfilePOJO;
        this.total_contribution = total_contribution;
    }

    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.tv_available_balance)
    TextView tv_available_balance;
    @BindView(R.id.tv_wallet_amount)
    TextView tv_wallet_amount;
    @BindView(R.id.tv_remaining_amount)
    TextView tv_remaining_amount;
    @BindView(R.id.check_balance)
    CheckBox check_balance;
    @BindView(R.id.ll_payment_gateway)
    LinearLayout ll_payment_gateway;
    @BindView(R.id.btn_pay)
    Button btn_pay;

    @BindView(R.id.rv_payment_options)
    RecyclerView rv_payment_options;

    String calculated_amount = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_complete_contri, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPaymentLogs();
        showpaymentDialog();
        tv_total_amount.setText("Rs. " + total_contribution);
        calculated_amount = total_contribution;
        check_balance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    calculated_amount = String.valueOf(remaingAmount());
                    tv_remaining_amount.setText("Rs. "+calculated_amount);
                } else {
                    calculated_amount=total_contribution;
                    tv_remaining_amount.setText("Rs. "+calculated_amount);
                    ll_payment_gateway.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callpaymentSuccessfullAPI(total_contribution, "1");
            }
        });
    }


    public double remaingAmount() {
        try {
            double paid_amount = Double.parseDouble(total_contribution);
            double wallet_amount = CompleteContributionFragment.this.balance_amount;
            double remaining_amount = 0;
            if (wallet_amount >= paid_amount) {
                remaining_amount = 0;
                ll_payment_gateway.setVisibility(View.GONE);
            } else {
                remaining_amount = paid_amount - wallet_amount;
                ll_payment_gateway.setVisibility(View.VISIBLE);
            }
            return remaining_amount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void showpaymentDialog() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("payment_point", "All"));
        nameValuePairs.add(new BasicNameValuePair("debit_credit", "All"));
        new WebServiceBaseResponseList<PaymentTypePOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PaymentTypePOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PaymentTypePOJO> responseListPOJO) {
                if (responseListPOJO.isSuccess()) {
//                    paymentTransPOJOS.addAll(responseListPOJO.getResultList());
                    double money = 0;
                    double point = 0;
                    for (PaymentTypePOJO paymentTypePOJO : responseListPOJO.getResultList()) {
                        PaymentTransPOJO paymentTransPOJO;
                        if(paymentTypePOJO.getFeedtype().equalsIgnoreCase(Constants.PAYMENT_FEED_MONEY)){
                            paymentTransPOJO=paymentTypePOJO.getPaymentTransPOJO();
                            try{
                                if(paymentTransPOJO.getDebitOrCredit().equalsIgnoreCase("0")){
                                    money-=Double.parseDouble(paymentTransPOJO.getTransactionAmount());
                                }else{
                                    money+=Double.parseDouble(paymentTransPOJO.getTransactionAmount());
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else{
                            paymentTransPOJO=paymentTypePOJO.getPointdataTransPOJO();
                            try{
                                if(paymentTransPOJO.getDebitOrCredit().equalsIgnoreCase("0")){
                                    point-=Integer.parseInt(paymentTransPOJO.getTransactionAmount());
                                }else{
                                    point+=Integer.parseInt(paymentTransPOJO.getTransactionAmount());
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    }
                    balance_amount=money;
                }
            }

        }, PaymentTypePOJO.class, "CALL_PAYMENT_LOGS_API", true).execute(WebServicesUrls.PAYMENT_AND_PAINT_TRANS_LOG);
    }

    double balance_amount;

    public void getPaymentLogs() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBaseResponseList<PaymentTransPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PaymentTransPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PaymentTransPOJO> responseListPOJO) {
//                paymentTransPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
//                    paymentTransPOJOS.addAll(responseListPOJO.getResultList());
                    double amount = 0;
                    for (PaymentTransPOJO paymentTransPOJO : responseListPOJO.getResultList()) {
                        if (paymentTransPOJO.getDebitOrCredit().equals("0")) {
                            amount -= getTransAmount(paymentTransPOJO.getTransactionAmount());
                        } else {
                            amount += getTransAmount(paymentTransPOJO.getTransactionAmount());
                        }
                    }
//                    balance_amount=String.valueOf(amount);
                    balance_amount = amount;
                    tv_wallet_amount.setText("Rs. " + String.valueOf(amount));
                    tv_available_balance.setText("Available Balance is Rs. " + String.valueOf(amount));
                }
//                paymentTransAdapter.notifyDataSetChanged();
            }

        }, PaymentTransPOJO.class, "CALL_PAYMENT_LOGS_API", true).execute(WebServicesUrls.GET_PAYMENT_TRANS_LOGS);
    }

    public double getTransAmount(String amount) {
        try {
            double trans_amount = Double.parseDouble(amount);
            return trans_amount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void callpaymentSuccessfullAPI(String amount, String payment_gateway_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("payment_gateway_id", ""));
        nameValuePairs.add(new BasicNameValuePair("transaction_id", StringUtils.getRandomString(15)));
        nameValuePairs.add(new BasicNameValuePair("payment_to_user_profile_id", userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("transaction_date", UtilityFunction.getCurrentDate()));
        nameValuePairs.add(new BasicNameValuePair("transaction_amount", amount));
        nameValuePairs.add(new BasicNameValuePair("transaction_shipping_amount", amount));
        nameValuePairs.add(new BasicNameValuePair("transaction_status", "1"));
        nameValuePairs.add(new BasicNameValuePair("debit_or_credit", "0"));
        nameValuePairs.add(new BasicNameValuePair("comments", "Contribution"));
        nameValuePairs.add(new BasicNameValuePair("contribute", "1"));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                Log.d(TagUtils.getTag(), "trans response:-" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        ToastClass.showShortToast(getActivity().getApplicationContext(),"Thanks for your contribution");
//                        getActivity().onBackPressed();
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                        getActivity().finishAffinity();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_TRANS_API", true).execute(WebServicesUrls.SAVE_PAYMENT_TRANSACTIONS);
    }

}
