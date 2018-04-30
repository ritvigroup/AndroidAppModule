package com.ritvi.kaajneeti.fragment.homeactivity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.ritvi.kaajneeti.adapter.PointLogAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.payment.PointTransLogPOJO;
import com.ritvi.kaajneeti.webservice.ResponseListCallback;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
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
 * Created by sunil on 12-03-2018.
 */

public class WithdrawalFragment extends Fragment {

    @BindView(R.id.rv_points_log)
    RecyclerView rv_points_log;
    @BindView(R.id.tv_points)
    TextView tv_points;
    @BindView(R.id.tv_convert)
    TextView tv_convert;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_withdrawal, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attachAdapter();

        getPointTransLogs();
        getMyTotalWalletPoints();

        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getPointTransLogs();
                        getMyTotalWalletPoints();
                    }
                }
        );

        tv_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_points.getText().toString().length()>0) {
                    showConvertDialog(tv_points.getText().toString());
                }
            }
        });
    }

    public void showConvertDialog(final String total_points){
        final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_convert_to_rupees);
        dialog1.setTitle("Select");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_convert=dialog1.findViewById(R.id.btn_convert);
        final EditText et_point=dialog1.findViewById(R.id.et_point);
        final TextView tv_balance_point=dialog1.findViewById(R.id.tv_balance_point);

        tv_balance_point.setText("BALANCE POINTS :- " +total_points);

        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_point.getText().toString().length()>0){
                    try{
                        double point=Double.parseDouble(et_point.getText().toString());
                        double remaining_point=Double.parseDouble(total_points);
                        if(point>=remaining_point){
                            ToastClass.showShortToast(getActivity().getApplicationContext(),"Entered point should not be greater than balance point");
                        }else{
                            ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
                            nameValuePairs.add(new BasicNameValuePair("user_profile_id",Constants.userProfilePOJO.getUserProfileId()));
                            nameValuePairs.add(new BasicNameValuePair("point",String.valueOf(point)));
                            new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
                                @Override
                                public void onGetMsg(String apicall, String response) {
                                    try{
                                        JSONObject jsonObject=new JSONObject(response);
                                        if(jsonObject.optString("status").equals("success")){
                                            ToastClass.showShortToast(getActivity().getApplicationContext(),"Points Converted");
                                            dialog1.dismiss();
                                            getMyTotalWalletPoints();
                                            getPointTransLogs();
                                        }else{
                                            ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            },"CONVERT_POINT_TO_RUPEE",true).execute(WebServicesUrls.CONVERT_POINT_TO_RUPEE);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void getMyTotalWalletPoints() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("success")) {
                        String MyPointBalance = jsonObject.optJSONObject("result").optString("MyPointBalance");
                        tv_points.setText(MyPointBalance);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_WALLET_API", false).execute(WebServicesUrls.MY_TOTAL_POINTS);
    }

    public void getPointTransLogs() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userProfilePOJO.getUserProfileId()));
        new WebServiceBaseResponseList<PointTransLogPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<PointTransLogPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PointTransLogPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        pointTransLogPOJOS.clear();
                        pointTransLogPOJOS.addAll(responseListPOJO.getResultList());
                        pointLogAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                swiperefresh.setRefreshing(false);
            }
        }, PointTransLogPOJO.class, "point trans log", false).execute(WebServicesUrls.POINT_TRANS_LOG);
    }

    PointLogAdapter pointLogAdapter;
    List<PointTransLogPOJO> pointTransLogPOJOS = new ArrayList<>();

    public void attachAdapter() {
        pointLogAdapter = new PointLogAdapter(getActivity(), null, pointTransLogPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_points_log.setHasFixedSize(true);
        rv_points_log.setAdapter(pointLogAdapter);
        rv_points_log.setLayoutManager(linearLayoutManager);
        rv_points_log.setItemAnimator(new DefaultItemAnimator());
    }

}
