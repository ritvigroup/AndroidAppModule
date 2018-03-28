package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 28-03-2018.
 */

public class UserProfileDetailPOJO {
    @SerializedName("user_info")
    UserInfoPOJO userInfoPOJO;
    @SerializedName("profile")
    UserProfilePOJO userProfilePOJO;

    public UserInfoPOJO getUserInfoPOJO() {
        return userInfoPOJO;
    }

    public void setUserInfoPOJO(UserInfoPOJO userInfoPOJO) {
        this.userInfoPOJO = userInfoPOJO;
    }

    public UserProfilePOJO getUserProfilePOJO() {
        return userProfilePOJO;
    }

    public void setUserProfilePOJO(UserProfilePOJO userProfilePOJO) {
        this.userProfilePOJO = userProfilePOJO;
    }
}
