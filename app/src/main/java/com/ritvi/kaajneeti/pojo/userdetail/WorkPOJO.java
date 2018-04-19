package com.ritvi.kaajneeti.pojo.userdetail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 12-04-2018.
 */

public class WorkPOJO {

    @SerializedName("UserProfileWorkId")
    private String userProfileWorkId;
    @SerializedName("UserProfileId")
    private String userProfileId;
    @SerializedName("WorkPosition")
    private String workPosition;
    @SerializedName("WorkPlace")
    private String workPlace;
    @SerializedName("WorkLocation")
    private String workLocation;
    @SerializedName("WorkFrom")
    private String workFrom;
    @SerializedName("WorkTo")
    private String workTo;
    @SerializedName("CurrentlyWorking")
    private String currentlyWorking;
    @SerializedName("PrivatePublic")
    private String privatePublic;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("UpdatedOn")
    private String updatedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;
    @SerializedName("UpdatedOnTime")
    private String updatedOnTime;

    public String getUserProfileWorkId() {
        return userProfileWorkId;
    }

    public void setUserProfileWorkId(String userProfileWorkId) {
        this.userProfileWorkId = userProfileWorkId;
    }

    public String getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        this.userProfileId = userProfileId;
    }

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

    public String getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(String workFrom) {
        this.workFrom = workFrom;
    }

    public String getWorkTo() {
        return workTo;
    }

    public void setWorkTo(String workTo) {
        this.workTo = workTo;
    }

    public String getCurrentlyWorking() {
        return currentlyWorking;
    }

    public void setCurrentlyWorking(String currentlyWorking) {
        this.currentlyWorking = currentlyWorking;
    }

    public String getPrivatePublic() {
        return privatePublic;
    }

    public void setPrivatePublic(String privatePublic) {
        this.privatePublic = privatePublic;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getAddedOnTime() {
        return addedOnTime;
    }

    public void setAddedOnTime(String addedOnTime) {
        this.addedOnTime = addedOnTime;
    }

    public String getUpdatedOnTime() {
        return updatedOnTime;
    }

    public void setUpdatedOnTime(String updatedOnTime) {
        this.updatedOnTime = updatedOnTime;
    }
}
