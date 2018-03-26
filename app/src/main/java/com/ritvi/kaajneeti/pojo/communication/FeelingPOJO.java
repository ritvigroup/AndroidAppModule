package com.ritvi.kaajneeti.pojo.communication;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 26-03-2018.
 */

public class FeelingPOJO {
    @SerializedName("FeelingId")
    private String feelingId;
    @SerializedName("FeelingName")
    private String feelingName;
    @SerializedName("FeelingImagePath")
    private String feelingImagePath;

    public String getFeelingId() {
        return feelingId;
    }

    public void setFeelingId(String feelingId) {
        this.feelingId = feelingId;
    }

    public String getFeelingName() {
        return feelingName;
    }

    public void setFeelingName(String feelingName) {
        this.feelingName = feelingName;
    }

    public String getFeelingImagePath() {
        return feelingImagePath;
    }

    public void setFeelingImagePath(String feelingImagePath) {
        this.feelingImagePath = feelingImagePath;
    }
}
