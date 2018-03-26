package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 26-03-2018.
 */

public class UserProfileDetailPOJO {
    @SerializedName("user_info")
    UserProfilePOJO userProfilePOJO;

    public UserProfilePOJO getUserProfilePOJO() {
        return userProfilePOJO;
    }

    public void setUserProfilePOJO(UserProfilePOJO userProfilePOJO) {
        this.userProfilePOJO = userProfilePOJO;
    }
}
