package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 28-03-2018.
 */

public class OutGoingRequestPOJO implements Serializable{
    @SerializedName("user_profile_detail")
    UserProfileDetailPOJO userProfileDetailPOJO;
    @SerializedName("AcceptedYesNo")
    private String acceptedYesNo;
    @SerializedName("AcceptedOn")
    private String acceptedOn;



    public UserProfileDetailPOJO getUserProfileDetailPOJO() {
        return userProfileDetailPOJO;
    }

    public void setUserProfileDetailPOJO(UserProfileDetailPOJO userProfileDetailPOJO) {
        this.userProfileDetailPOJO = userProfileDetailPOJO;
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
