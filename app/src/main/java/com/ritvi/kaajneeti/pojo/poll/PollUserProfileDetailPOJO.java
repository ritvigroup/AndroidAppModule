package com.ritvi.kaajneeti.pojo.poll;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

/**
 * Created by sunil on 22-03-2018.
 */

public class PollUserProfileDetailPOJO {
    @SerializedName("user_info")
    UserProfilePOJO userProfilePOJO;

    public UserProfilePOJO getUserProfilePOJO() {
        return userProfilePOJO;
    }

    public void setUserProfilePOJO(UserProfilePOJO userProfilePOJO) {
        this.userProfilePOJO = userProfilePOJO;
    }
}
