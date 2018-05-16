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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.PaymentListAdapter;
import com.ritvi.kaajneeti.adapter.PaymentTransAdapter;
import com.ritvi.kaajneeti.fragment.wallet.WalletAmountFragment;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.ResponsePOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentDetailPOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentPOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentTransPOJO;
import com.ritvi.kaajneeti.testing.PayUMoneyIntegration;
import com.ritvi.kaajneeti.webservice.ResponseCallBack;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponse;
import com.ritvi.kaajneeti.webservice.WebServiceBaseResponseList;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 06-04-2018.
 */

public class WalletFragment extends Fragment {

    @BindView(R.id.rv_transactions)
    RecyclerView rv_transactions;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_convert_to_point)
    TextView tv_convert_to_point;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.iv_back)
    LinearLayout iv_back;

    List<PaymentTransPOJO> paymentTransPOJOS = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_wallet, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showpaymentDialog();
                if(getActivity() instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.replaceFragmentinFrameHome(new WalletAmountFragment(),"WalletAmountFragment");
                }
            }
        });
        attachAdapter();
        getPaymentLogs();

        tv_convert_to_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConvertDialog();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    public void showConvertDialog() {
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_rupees_to_point);
        dialog1.setTitle("Select");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_convert = dialog1.findViewById(R.id.btn_convert);
        final EditText et_rupees = dialog1.findViewById(R.id.et_rupees);
        final TextView tv_balance_rupees = dialog1.findViewById(R.id.tv_balance_rupees);

        tv_balance_rupees.setText("BALANCE AMOUNT:- "+tv_amount.getText().toString());

        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_rupees.getText().toString().length() > 0) {
                    try {
                        double balance = Double.parseDouble(et_rupees.getText().toString());
                        double remaining_balance = Double.parseDouble(balance_amount);
                        if (balance >= remaining_balance) {
                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Entered Amount should not be greater than balance Amount");
                        } else {
                            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                            nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
                            nameValuePairs.add(new BasicNameValuePair("rupee", String.valueOf(balance)));
                            new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
                                @Override
                                public void onGetMsg(String apicall, String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.optString("status").equals("success")) {
                                            ToastClass.showShortToast(getActivity().getApplicationContext(), "Rupees Converted");
                                            dialog1.dismiss();
                                            getPaymentLogs();
                                        } else {
                                            ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, "CONVERT_POINT_TO_RUPEE", true).execute(WebServicesUrls.CONVERT_RUPEES_TO_POINTS);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
    String balance_amount="";
    public void getPaymentLogs() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBaseResponseList<PaymentTransPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PaymentTransPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PaymentTransPOJO> responseListPOJO) {
                paymentTransPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    paymentTransPOJOS.addAll(responseListPOJO.getResultList());
                    double amount = 0;
                    for (PaymentTransPOJO paymentTransPOJO : responseListPOJO.getResultList()) {
                        if (paymentTransPOJO.getDebitOrCredit().equals("0")) {
                            amount -= getTransAmount(paymentTransPOJO.getTransactionAmount());
                        } else {
                            amount += getTransAmount(paymentTransPOJO.getTransactionAmount());
                        }
                    }
                    balance_amount=String.valueOf(amount);
                    tv_amount.setText("Rs. " + String.valueOf(amount));
                }
                paymentTransAdapter.notifyDataSetChanged();
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
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBaseResponseList<PaymentPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PaymentPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PaymentPOJO> responseListPOJO) {
                if (responseListPOJO.isSuccess()) {
                    showPaymentOptions(responseListPOJO.getResultList());
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                }
            }
        }, PaymentPOJO.class, "GET_ALL_PAYMENT_METHODS", true).execute(WebServicesUrls.ALL_PAYMENT_GATEWAY);
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

        RecyclerView rv_payment_options = dialog1.findViewById(R.id.rv_payment_options);
        Button btn_cancel = dialog1.findViewById(R.id.btn_cancel);

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
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("payment_gateway_id", paymentPOJO.getPaymentGatewayId()));
        new WebServiceBaseResponse<PaymentDetailPOJO>(nameValuePairs, getActivity(), new ResponseCallBack<PaymentDetailPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<PaymentDetailPOJO> responsePOJO) {
                if (responsePOJO.isSuccess()) {
                    Intent intent = new Intent(getActivity(), PayUMoneyIntegration.class);
                    intent.putExtra("paymentPOJO", paymentPOJO);
                    startActivity(intent);
                }
            }
        }, PaymentDetailPOJO.class, "GET_PAYMENT_DETAIL", true).execute(WebServicesUrls.PAYMENT_GATEWAY_API_DETAILS);
    }
}
