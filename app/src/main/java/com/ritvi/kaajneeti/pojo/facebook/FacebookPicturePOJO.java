package com.ritvi.kaajneeti.pojo.facebook;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 28-02-2018.
 */

public class FacebookPicturePOJO {
    @SerializedName("data")
    FacebookPictureDataPOJO facebookPictureDataPOJO;

    public FacebookPictureDataPOJO getFacebookPictureDataPOJO() {
        return facebookPictureDataPOJO;
    }

    public void setFacebookPictureDataPOJO(FacebookPictureDataPOJO facebookPictureDataPOJO) {
        this.facebookPictureDataPOJO = facebookPictureDataPOJO;
    }
}
