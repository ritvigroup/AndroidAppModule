package com.ritvi.kaajneeti.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ritvi.kaajneeti.R;
import com.ritvi.kaajneeti.pojo.newsapi.ArticlesPOJO;
import com.ritvi.kaajneeti.webservice.WebServicesCallBack;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.ViewHolder> implements WebServicesCallBack {
    private List<ArticlesPOJO> items;
    Activity activity;
    Fragment fragment;
    int device_height = 0;

    public HomeNewsAdapter(Activity activity, Fragment fragment, List<ArticlesPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_news_articles, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(activity)
                .load(items.get(position).getUrlToImage())
                .into(holder.iv_article);


        holder.tv_title.setText(items.get(position).getTitle());

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onGetMsg(String apicall, String response) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_article;
        public TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_article = (ImageView) itemView.findViewById(R.id.iv_article);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
