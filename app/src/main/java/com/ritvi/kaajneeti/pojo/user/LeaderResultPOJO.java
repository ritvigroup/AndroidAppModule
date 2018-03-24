package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 23-03-2018.
 */

public class LeaderResultPOJO {

    @SerializedName("UserProfileLeader")
    List<LeaderProfilePOJO> leaderProfilePOJOS;

    public List<LeaderProfilePOJO> getLeaderProfilePOJOS() {
        return leaderProfilePOJOS;
    }

    public void setLeaderProfilePOJOS(List<LeaderProfilePOJO> leaderProfilePOJOS) {
        this.leaderProfilePOJOS = leaderProfilePOJOS;
    }

}
