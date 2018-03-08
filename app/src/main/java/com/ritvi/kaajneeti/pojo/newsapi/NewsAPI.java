package com.ritvi.kaajneeti.pojo.newsapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 23-02-2018.
 */

public class NewsAPI {
    @SerializedName("status")
    String status;
    @SerializedName("articles")
    List<ArticlesPOJO> articlesPOJOS;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ArticlesPOJO> getArticlesPOJOS() {
        return articlesPOJOS;
    }

    public void setArticlesPOJOS(List<ArticlesPOJO> articlesPOJOS) {
        this.articlesPOJOS = articlesPOJOS;
    }
}
