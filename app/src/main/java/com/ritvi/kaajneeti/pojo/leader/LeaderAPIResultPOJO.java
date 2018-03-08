package com.ritvi.kaajneeti.pojo.leader;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 05-02-2018.
 */

public class LeaderAPIResultPOJO {
    @SerializedName("status")
    String status;
    @SerializedName("user_detail")
    List<LeaderPOJO> leaderPOJOS;
    @SerializedName("message")
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LeaderPOJO> getLeaderPOJOS() {
        return leaderPOJOS;
    }

    public void setLeaderPOJOS(List<LeaderPOJO> leaderPOJOS) {
        this.leaderPOJOS = leaderPOJOS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
