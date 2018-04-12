package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 28-03-2018.
 */

public class OutGoingRequestPOJO implements Serializable{
    @SerializedName("user_profile_detail")
    UserProfileDetailPOJO userProfileDetailPOJO;


    public UserProfileDetailPOJO getUserProfileDetailPOJO() {
        return userProfileDetailPOJO;
    }

    public void setUserProfileDetailPOJO(UserProfileDetailPOJO userProfileDetailPOJO) {
        this.userProfileDetailPOJO = userProfileDetailPOJO;
    }


}
