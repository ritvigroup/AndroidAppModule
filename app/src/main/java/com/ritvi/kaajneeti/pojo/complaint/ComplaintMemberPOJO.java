package com.ritvi.kaajneeti.pojo.complaint;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;

/**
 * Created by sunil on 17-04-2018.
 */

public class ComplaintMemberPOJO {
    @SerializedName("user_profile_detail")
    private OutGoingRequestPOJO outGoingRequestPOJO;
    @SerializedName("AcceptedYesNo")
    private String acceptedYesNo;
    @SerializedName("AcceptedOn")
    private String acceptedOn;

    public OutGoingRequestPOJO getOutGoingRequestPOJO() {
        return outGoingRequestPOJO;
    }

    public void setOutGoingRequestPOJO(OutGoingRequestPOJO outGoingRequestPOJO) {
        this.outGoingRequestPOJO = outGoingRequestPOJO;
    }

    public String getAcceptedYesNo() {
        return acceptedYesNo;
    }

    public void setAcceptedYesNo(String acceptedYesNo) {
        this.acceptedYesNo = acceptedYesNo;
    }

    public String getAcceptedOn() {
        return acceptedOn;
    }

    public void setAcceptedOn(String acceptedOn) {
        this.acceptedOn = acceptedOn;
    }
}
