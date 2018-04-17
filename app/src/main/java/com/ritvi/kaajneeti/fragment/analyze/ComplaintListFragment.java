package com.ritvi.kaajneeti.fragment.analyze;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.Util.ToastClass;
import com.ritvi.kaajneeti.Util.UtilityFunction;
import com.ritvi.kaajneeti.adapter.ComplaintAnalyzeAdapter;
import com.ritvi.kaajneeti.pojo.ResponseListPOJO;
import com.ritvi.kaajneeti.pojo.analyze.ComplaintPOJO;
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
 * Created by sunil on 31-03-2018.
 */

@SuppressLint("ValidFragment")
public class ComplaintListFragment extends Fragment {

    @BindView(R.id.rv_complaints)
    RecyclerView rv_complaints;
    @BindView(R.id.spinner_comp_type)
    Spinner spinner_comp_type;

    boolean is_group;

    public ComplaintListFragment(boolean is_group) {
        this.is_group = is_group;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_complaint_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();

        if (is_group) {
            callMyAssociatedComplaintsAPI();
        } else {
            callAPI();
        }

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i(TagUtils.getTag(), "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i(TagUtils.getTag(), "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        spinner_comp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                complaintPOJOS.clear();
                switch (i) {
                    case 0:
                        complaintPOJOS.addAll(masterPOJOS);
                        break;
                    case 1:
                        complaintPOJOS.addAll(getComplaintList("1"));
                        break;
                    case 2:
                        complaintPOJOS.addAll(getComplaintList("3"));
                        break;
                    case 3:
                        complaintPOJOS.addAll(getComplaintList("2"));
                        break;
                }
                complaintAnalyzeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public List<ComplaintPOJO> getComplaintList(String comp_type) {
        List<ComplaintPOJO> complaintPOJOS = new ArrayList<>();
        for (ComplaintPOJO complaintPOJO : masterPOJOS) {
            if (complaintPOJO.getComplaintTypeId().equals(comp_type)) {
                complaintPOJOS.add(complaintPOJO);
            }
        }
        return complaintPOJOS;
    }

    public void refresh() {

        if (is_group) {
            callMyAssociatedComplaintsAPI();
        } else {
            callAPI();
        }

    }

    public void callMyAssociatedComplaintsAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", UtilityFunction.getProfileID(Constants.userInfoPOJO)));
        new WebServiceBaseResponseList<ComplaintPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<ComplaintPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<ComplaintPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {
                        complaintPOJOS.clear();
                        complaintPOJOS.addAll(responseListPOJO.getResultList());
                        complaintAnalyzeAdapter.notifyDataSetChanged();
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ComplaintPOJO.class, "CALL_COMPLAINT_API", true).execute(WebServicesUrls.GET_MY_ALL_ASSOCIATED_COMPLAINT);
    }

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));

        new WebServiceBaseResponseList<ComplaintPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<ComplaintPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO responseListPOJO) {
                complaintPOJOS.clear();
                try {
                    if (responseListPOJO.isSuccess()) {
                        masterPOJOS.addAll(responseListPOJO.getResultList());
                        complaintPOJOS.addAll(responseListPOJO.getResultList());
                    } else {
                        ToastClass.showShortToast(getActivity().getApplicationContext(), responseListPOJO.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                complaintAnalyzeAdapter.notifyDataSetChanged();
            }
        }, ComplaintPOJO.class, "call_complaint_list_api", true).execute(WebServicesUrls.COMPLAINT_LIST);
    }

    List<ComplaintPOJO> complaintPOJOS = new ArrayList<>();
    List<ComplaintPOJO> masterPOJOS = new ArrayList<>();
    ComplaintAnalyzeAdapter complaintAnalyzeAdapter;

    public void attachAdapter() {

        complaintAnalyzeAdapter = new ComplaintAnalyzeAdapter(getActivity(), this, complaintPOJOS, is_group);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_complaints.setHasFixedSize(true);
        rv_complaints.setAdapter(complaintAnalyzeAdapter);
        rv_complaints.setLayoutManager(linearLayoutManager);
        rv_complaints.setItemAnimator(new DefaultItemAnimator());
    }


}
