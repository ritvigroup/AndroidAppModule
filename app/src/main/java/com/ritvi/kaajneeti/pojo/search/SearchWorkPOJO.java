package com.ritvi.kaajneeti.pojo.search;

import com.google.gson.annotations.SerializedName;

public class SearchWorkPOJO {

    @SerializedName("WorkPosition")
    private String workPosition;
    @SerializedName("WorkPlace")
    private String workPlace;
    @SerializedName("WorkLocation")
    private String workLocation;

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }
}
