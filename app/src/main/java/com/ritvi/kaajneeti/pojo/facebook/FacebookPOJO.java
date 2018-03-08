package com.ritvi.kaajneeti.pojo.facebook;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 28-02-2018.
 */

public class FacebookPOJO {
    @SerializedName("data")
    List<FacebookDataPOJO> facebookDataPOJOS;

    public List<FacebookDataPOJO> getFacebookDataPOJOS() {
        return facebookDataPOJOS;
    }

    public void setFacebookDataPOJOS(List<FacebookDataPOJO> facebookDataPOJOS) {
        this.facebookDataPOJOS = facebookDataPOJOS;
    }
}
