package com.ritvi.kaajneeti.fragment.homeactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.Constants;
import com.ritvi.kaajneeti.adapter.AnalyzeCategoryAdapter;
import com.ritvi.kaajneeti.fragment.analyze.ALLPostListFragment;
import com.ritvi.kaajneeti.fragment.analyze.AllEventFragment;
import com.ritvi.kaajneeti.fragment.analyze.AllPollFragment;
import com.ritvi.kaajneeti.fragment.analyze.ComplaintListFragment;
import com.ritvi.kaajneeti.fragment.analyze.InformationListFragment;
import com.ritvi.kaajneeti.fragment.analyze.SuggestionListFragment;
import com.ritvi.kaajneeti.pojo.analyze.AnalyzeCategoryPOJO;
import com.ritvi.kaajneeti.webservice.WebServiceBase;
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

public class InvestigateFragment extends Fragment {

    @BindView(R.id.rv_express)
    RecyclerView rv_express;
    @BindView(R.id.rv_explore)
    RecyclerView rv_explore;
    @BindView(R.id.rv_connect)
    RecyclerView rv_connect;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_investigate, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        callAPI();


    }

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_profile_id", Constants.userInfoPOJO.getUserProfileCitizen().getUserProfileId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
                        JSONObject resultJsonObject = jsonObject.optJSONObject("result");
                        String TotalEvent = resultJsonObject.optString("TotalEvent");
                        String TotalPoll = resultJsonObject.optString("TotalPoll");
                        String TotalPost = resultJsonObject.optString("TotalPost");
                        String TotalSuggestion = resultJsonObject.optString("TotalSuggestion");
                        String TotalInformation = resultJsonObject.optString("TotalInformation");
                        String TotalComplaint = resultJsonObject.optString("TotalComplaint");


                        List<AnalyzeCategoryPOJO> exploreAnalyzeCategoryPOJOS = new ArrayList<>();
                        exploreAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Post", TotalPost));
                        exploreAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Poll", TotalPoll));
                        exploreAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Event", TotalEvent));

                        attachRvAdapter(exploreAnalyzeCategoryPOJOS, rv_explore);

                        List<AnalyzeCategoryPOJO> connectAnalyzeCategoryPOJOS = new ArrayList<>();
                        connectAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Friends", "435"));
                        connectAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Leaders", "60"));
                        connectAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Favorite Leader", "45"));
                        connectAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Followers", "40"));
                        connectAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Followings", "150"));

                        attachRvAdapter(connectAnalyzeCategoryPOJOS, rv_connect);

                        List<AnalyzeCategoryPOJO> expressAnalyzeCategoryPOJOS = new ArrayList<>();
                        expressAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Complaints", TotalComplaint));
                        expressAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Suggestions", TotalSuggestion));
                        expressAnalyzeCategoryPOJOS.add(new AnalyzeCategoryPOJO("Informations", TotalInformation));

                        attachRvAdapter(expressAnalyzeCategoryPOJOS, rv_express);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_ALL_DATA", false).execute(WebServicesUrls.ALL_SUMMARY_DATA);
    }


    public void attachRvAdapter(List<AnalyzeCategoryPOJO> analyzeCategoryPOJOS, RecyclerView recyclerView) {

        AnalyzeCategoryAdapter analyzeCategoryAdapter = new AnalyzeCategoryAdapter(getActivity(), this, analyzeCategoryPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(analyzeCategoryAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    public void showComplaintListFragment() {
        ComplaintListFragment complaintListFragment = new ComplaintListFragment();
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, complaintListFragment, "complaintListFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showSuggestionListFragment() {
        SuggestionListFragment suggestionListFragment = new SuggestionListFragment();
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, suggestionListFragment, "suggestionListFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showInformationFragment() {
        InformationListFragment informationListFragment = new InformationListFragment();
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, informationListFragment, "informationListFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showAllPostFragment() {
        ALLPostListFragment allPostListFragment = new ALLPostListFragment();
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, allPostListFragment, "allPostListFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showALLEventFragment() {
        AllEventFragment allEventFragment = new AllEventFragment();
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, allEventFragment, "allEventFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void shoAllPollFragment() {
        AllPollFragment allPollFragment = new AllPollFragment();
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, allPollFragment, "allPollFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
