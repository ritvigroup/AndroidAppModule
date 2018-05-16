package com.ritvi.kaajneeti.fragment.wallet;

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
import android.widget.EditText;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.StringUtils;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.activity.HomeActivity;
import com.ritvi.kaajneeti.adapter.PaymentListAdapter;
import com.ritvi.kaajneeti.fragment.contribute.CompleteContributionFragment;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.payment.PaymentPOJO;
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

public class WalletAmountFragment extends Fragment{

    @BindView(R.id.rv_payment_options)
    RecyclerView rv_payment_options;
    @BindView(R.id.btn_pay)
    Button btn_pay;
    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.btn_50)
    Button btn_50;
    @BindView(R.id.btn_100)
    Button btn_100;
    @BindView(R.id.btn_500)
    Button btn_500;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_wallet_amount,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAmount("50");
            }
        });

        btn_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAmount("100");
            }
        });

        btn_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAmount("500");
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callpaymentSuccessfullAPI(et_amount.getText().toString(),"0");
            }
        });
        showpaymentDialog();
    }


    public void showpaymentDialog() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBaseResponseList<PaymentPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PaymentPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PaymentPOJO> responseListPOJO) {
                if (responseListPOJO.isSuccess()) {
                    PaymentListAdapter paymentListAdapter = new PaymentListAdapter(getActivity(), WalletAmountFragment.this, responseListPOJO.getResultList());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rv_payment_options.setHasFixedSize(true);
                    rv_payment_options.setAdapter(paymentListAdapter);
                    rv_payment_options.setLayoutManager(linearLayoutManager);
                    rv_payment_options.setItemAnimator(new DefaultItemAnimator());
                } else {
                    ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                }
            }
        }, PaymentPOJO.class, "GET_ALL_PAYMENT_METHODS", true).execute(WebServicesUrls.ALL_PAYMENT_GATEWAY);
    }

    public void addAmount(String amount) {
        try {
            int entered_amount = Integer.parseInt(amount);
            if (et_amount.getText().toString().length() == 0) {
                et_amount.setText(amount);
            } else {
                int previous_amount = Integer.parseInt(et_amount.getText().toString());
                et_amount.setText(String.valueOf(previous_amount + entered_amount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callpaymentSuccessfullAPI(String amount, String payment_gateway_id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("payment_gateway_id", payment_gateway_id));
        nameValuePairs.add(new BasicNameValuePair("transaction_id", StringUtils.getRandomString(15)));
        nameValuePairs.add(new BasicNameValuePair("payment_to_user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        nameValuePairs.add(new BasicNameValuePair("transaction_date", UtilityFunction.getCurrentDate()));
        nameValuePairs.add(new BasicNameValuePair("transaction_amount", amount));
        nameValuePairs.add(new BasicNameValuePair("transaction_shipping_amount", amount));
        nameValuePairs.add(new BasicNameValuePair("transaction_status", "1"));
        nameValuePairs.add(new BasicNameValuePair("debit_or_credit", "1"));
        nameValuePairs.add(new BasicNameValuePair("comments", "Contribution"));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                Log.d(TagUtils.getTag(), "trans response:-" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        ToastClass.showShortToast(getActivity().getApplicationContext(),"Amount Added Successfully");
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
