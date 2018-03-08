package com.ritvi.kaajneeti.pojo.facebook;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 28-02-2018.
 */

public class FacebookDataPOJO {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("picture")
    FacebookPicturePOJO facebookPicturePOJO;

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

    public FacebookPicturePOJO getFacebookPicturePOJO() {
        return facebookPicturePOJO;
    }

    public void setFacebookPicturePOJO(FacebookPicturePOJO facebookPicturePOJO) {
        this.facebookPicturePOJO = facebookPicturePOJO;
    }
}
