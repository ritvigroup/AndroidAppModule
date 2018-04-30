package com.ritvi.kaajneeti.pojo.userdetail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 23-04-2018.
 */

public class AddressPOJO {
    @SerializedName("UserProfileAddressId")
    private String userProfileAddressId;
    @SerializedName("UserProfileId")
    private String userProfileId;
    @SerializedName("Address")
    private String address;
    @SerializedName("City")
    private String city;
    @SerializedName("State")
    private String state;
    @SerializedName("Landmark")
    private String landmark;
    @SerializedName("Country")
    private String country;
    @SerializedName("Pincode")
    private String pincode;
    @SerializedName("Status")
    private String status;
    @SerializedName("Default")
    private String _default;
    @SerializedName("HomeWork")
    private String homeWork;
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

    public String getUserProfileAddressId() {
        return userProfileAddressId;
    }

    public void setUserProfileAddressId(String userProfileAddressId) {
        this.userProfileAddressId = userProfileAddressId;
    }

    public String getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String get_default() {
        return _default;
    }

    public void set_default(String _default) {
        this._default = _default;
    }

    public String getHomeWork() {
        return homeWork;
    }

    public void setHomeWork(String homeWork) {
        this.homeWork = homeWork;
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
