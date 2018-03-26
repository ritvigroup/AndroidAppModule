package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 26-03-2018.
 */

public class AllUserProfilePOJO {
    @SerializedName("UserProfileLeader")
    List<LeaderProfilePOJO> leaderProfilePOJOList;
    @SerializedName("UserProfileCitizen")
    List<UserProfilePOJO> citizenProfilePOJOList;

    public List<LeaderProfilePOJO> getLeaderProfilePOJOList() {
        return leaderProfilePOJOList;
    }

    public void setLeaderProfilePOJOList(List<LeaderProfilePOJO> leaderProfilePOJOList) {
        this.leaderProfilePOJOList = leaderProfilePOJOList;
    }

    public List<UserProfilePOJO> getCitizenProfilePOJOList() {
        return citizenProfilePOJOList;
    }

    public void setCitizenProfilePOJOList(List<UserProfilePOJO> citizenProfilePOJOList) {
        this.citizenProfilePOJOList = citizenProfilePOJOList;
    }
}
