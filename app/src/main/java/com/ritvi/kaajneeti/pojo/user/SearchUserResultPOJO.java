package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 28-03-2018.
 */

public class SearchUserResultPOJO {
    @SerializedName("UserProfileCitizen")
    List<UserInfoPOJO> citizenUserInfoPOJOS;
    @SerializedName("UserProfileLeader")
    List<UserInfoPOJO> leaderUserInfoPOJOS;

    public List<UserInfoPOJO> getCitizenUserInfoPOJOS() {
        return citizenUserInfoPOJOS;
    }

    public void setCitizenUserInfoPOJOS(List<UserInfoPOJO> citizenUserInfoPOJOS) {
        this.citizenUserInfoPOJOS = citizenUserInfoPOJOS;
    }

    public List<UserInfoPOJO> getLeaderUserInfoPOJOS() {
        return leaderUserInfoPOJOS;
    }

    public void setLeaderUserInfoPOJOS(List<UserInfoPOJO> leaderUserInfoPOJOS) {
        this.leaderUserInfoPOJOS = leaderUserInfoPOJOS;
    }
}
