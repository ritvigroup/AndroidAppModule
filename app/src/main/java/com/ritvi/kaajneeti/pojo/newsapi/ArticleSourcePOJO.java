package com.ritvi.kaajneeti.pojo.newsapi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 23-02-2018.
 */

public class ArticleSourcePOJO {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
