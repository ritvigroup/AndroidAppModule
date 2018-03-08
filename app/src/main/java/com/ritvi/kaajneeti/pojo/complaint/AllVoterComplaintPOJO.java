package com.ritvi.kaajneeti.pojo.complaint;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 07-02-2018.
 */

public class AllVoterComplaintPOJO {
    @SerializedName("status")
    String status;
    @SerializedName("complaints")
    List<VoterComplaintPOJO> voterComplaintPOJOS;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VoterComplaintPOJO> getVoterComplaintPOJOS() {
        return voterComplaintPOJOS;
    }

    public void setVoterComplaintPOJOS(List<VoterComplaintPOJO> voterComplaintPOJOS) {
        this.voterComplaintPOJOS = voterComplaintPOJOS;
    }
}
