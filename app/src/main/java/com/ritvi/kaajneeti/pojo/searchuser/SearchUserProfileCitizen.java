package com.ritvi.kaajneeti.pojo.searchuser;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.util.List;

/**
 * Created by sunil on 22-03-2018.
 */

public class SearchUserProfileCitizen {
    @SerializedName("UserProfileCitizen")
    List<UserProfilePOJO> searchUserPOJOList;

    public List<UserProfilePOJO> getSearchUserPOJOList() {
        return searchUserPOJOList;
    }

    public void setSearchUserPOJOList(List<UserProfilePOJO> searchUserPOJOList) {
        this.searchUserPOJOList = searchUserPOJOList;
    }
}
