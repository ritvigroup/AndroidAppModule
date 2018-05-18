package com.ritvi.kaajneeti.pojo.home;

import com.google.gson.annotations.SerializedName;

public class EventInterestPOJO {
    @SerializedName("EventInterestTypeId")
    private String eventInterestTypeId;
    @SerializedName("EventInterestTypeName")
    private String eventInterestTypeName;
    @SerializedName("EventInterestTypeStatus")
    private String eventInterestTypeStatus;
    @SerializedName("TotalCount")
    private String totalCount;

    public String getEventInterestTypeId() {
        return eventInterestTypeId;
    }

    public void setEventInterestTypeId(String eventInterestTypeId) {
        this.eventInterestTypeId = eventInterestTypeId;
    }

    public String getEventInterestTypeName() {
        return eventInterestTypeName;
    }

    public void setEventInterestTypeName(String eventInterestTypeName) {
        this.eventInterestTypeName = eventInterestTypeName;
    }

    public String getEventInterestTypeStatus() {
        return eventInterestTypeStatus;
    }

    public void setEventInterestTypeStatus(String eventInterestTypeStatus) {
        this.eventInterestTypeStatus = eventInterestTypeStatus;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
