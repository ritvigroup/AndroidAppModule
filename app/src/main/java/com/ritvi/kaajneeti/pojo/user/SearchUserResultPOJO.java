package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 28-03-2018.
 */

public class SearchUserResultPOJO {
    @SerializedName("UserProfileCitizen")
    List<UserProfilePOJO> citizenUserInfoPOJOS;
    @SerializedName("UserProfileLeader")
    List<UserProfilePOJO> leaderUserInfoPOJOS;

    public List<UserProfilePOJO> getCitizenUserInfoPOJOS() {
        return citizenUserInfoPOJOS;
    }

    public void setCitizenUserInfoPOJOS(List<UserProfilePOJO> citizenUserInfoPOJOS) {
        this.citizenUserInfoPOJOS = citizenUserInfoPOJOS;
    }

    public List<UserProfilePOJO> getLeaderUserInfoPOJOS() {
        return leaderUserInfoPOJOS;
    }

    public void setLeaderUserInfoPOJOS(List<UserProfilePOJO> leaderUserInfoPOJOS) {
        this.leaderUserInfoPOJOS = leaderUserInfoPOJOS;
    }
}
