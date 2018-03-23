package com.ritvi.kaajneeti.pojo.user.favorite;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.UserProfileLeaderPOJO;

/**
 * Created by sunil on 23-03-2018.
 */

public class UserProfileDetailPOJO {
    @SerializedName("user_info")
    UserInfoPOJO userInfoPOJO;
    @SerializedName("profile")
    UserProfileLeaderPOJO userProfileLeaderPOJO;

    public UserInfoPOJO getUserInfoPOJO() {
        return userInfoPOJO;
    }

    public void setUserInfoPOJO(UserInfoPOJO userInfoPOJO) {
        this.userInfoPOJO = userInfoPOJO;
    }

    public UserProfileLeaderPOJO getUserProfileLeaderPOJO() {
        return userProfileLeaderPOJO;
    }

    public void setUserProfileLeaderPOJO(UserProfileLeaderPOJO userProfileLeaderPOJO) {
        this.userProfileLeaderPOJO = userProfileLeaderPOJO;
    }
}
