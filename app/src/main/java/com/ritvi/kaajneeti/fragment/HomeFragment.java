package com.ritvi.kaajneeti.fragment;

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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.Util.TagUtils;
import com.ritvi.kaajneeti.activity.CitizenHomeActivity;
import com.ritvi.kaajneeti.adapter.HomeNewsAdapter;
import com.ritvi.kaajneeti.adapter.InfoAdapter;
import com.ritvi.kaajneeti.pojo.newsapi.ArticlesPOJO;
import com.ritvi.kaajneeti.pojo.newsapi.NewsAPI;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;
import com.ritvi.kaajneeti.webservice.WebServicesUrls;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 01-02-2018.
 */

public class HomeFragment extends Fragment implements WebServicesCallBack {

    private static final String CALL_HOME_NEWS_API = "call_home_news_api";
    @BindView(R.id.rv_news)
    RecyclerView rv_news;
    @BindView(R.id.rv_info_directory)
    RecyclerView rv_info_directory;
    @BindView(R.id.tv_all_info)
    TextView tv_all_info;

    HomeNewsAdapter homeNewsAdapter;
    List<ArticlesPOJO> articlesPOJOS = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        attachInfoAdapter();
//        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
//        new WebServiceBase(nameValuePairs,getActivity(),this,CALL_HOME_NEWS_API,false).execute(WebServicesUrls.NEWS_API);
//        new GetWebServices(getActivity(),this,CALL_HOME_NEWS_API,false).execute(WebServicesUrls.NEWS_API);

        tv_all_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof CitizenHomeActivity){
                    CitizenHomeActivity citizenHomeActivity= (CitizenHomeActivity) getActivity();
                    citizenHomeActivity.viewPager.setCurrentItem(7);
                }
            }
        });

        StringRequest req = new StringRequest(WebServicesUrls.NEWS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TagUtils.getTag(), response.toString());
                        parseHomeNewsResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TagUtils.getTag(), "api error:-" + error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(req);
    }

    public void attachAdapter() {
        homeNewsAdapter = new HomeNewsAdapter(getActivity(), this, articlesPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_news.setHasFixedSize(true);
        rv_news.setAdapter(homeNewsAdapter);
        rv_news.setLayoutManager(linearLayoutManager);
        rv_news.setItemAnimator(new DefaultItemAnimator());

    }
    InfoAdapter infoAdapter;
    List<String> infoString=new ArrayList<>();
    public void attachInfoAdapter() {
        for(int i=0;i<10;i++){
            infoString.add("");
        }
        infoAdapter = new InfoAdapter(getActivity(), this, infoString);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_info_directory.setHasFixedSize(true);
        rv_info_directory.setAdapter(infoAdapter);
        rv_info_directory.setLayoutManager(linearLayoutManager);
        rv_info_directory.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(), apicall + " :- " + response);
        switch (apicall) {
            case CALL_HOME_NEWS_API:
                parseHomeNewsResponse(response);
                break;
        }
    }

    public void parseHomeNewsResponse(String response) {
        articlesPOJOS.clear();
        try {

            Gson gson = new Gson();
            NewsAPI newsAPI = gson.fromJson(response, NewsAPI.class);
            articlesPOJOS.addAll(newsAPI.getArticlesPOJOS());
        } catch (Exception e) {
            e.printStackTrace();
        }
        homeNewsAdapter.notifyDataSetChanged();
    }
}
