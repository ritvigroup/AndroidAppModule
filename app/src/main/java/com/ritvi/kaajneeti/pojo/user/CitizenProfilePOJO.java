package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 21-03-2018.
 */

public class CitizenProfilePOJO implements Serializable{
    @SerializedName("UserProfileId")
    private String userProfileId;
    @SerializedName("UserId")
    private String userId;
    @SerializedName("ParentUserId")
    private String parentUserId;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("MiddleName")
    private String middleName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("Email")
    private String email;
    @SerializedName("UserProfileDeviceToken")
    private String userProfileDeviceToken;
    @SerializedName("Address")
    private String address;
    @SerializedName("Mobile")
    private String mobile;
    @SerializedName("AltMobile")
    private String altMobile;
    @SerializedName("ProfileStatus")
    private String profileStatus;
    @SerializedName("AddedBy")
    private String addedBy;
    @SerializedName("UpdatedBy")
    private String updatedBy;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("UpdatedOn")
    private String updatedOn;

    public CitizenProfilePOJO(String userProfileId, String userId, String parentUserId, String firstName, String middleName, String lastName, String email, String userProfileDeviceToken, String address, String mobile, String altMobile, String profileStatus, String addedBy, String updatedBy, String addedOn, String updatedOn) {
        this.userProfileId = userProfileId;
        this.userId = userId;
        this.parentUserId = parentUserId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.userProfileDeviceToken = userProfileDeviceToken;
        this.address = address;
        this.mobile = mobile;
        this.altMobile = altMobile;
        this.profileStatus = profileStatus;
        this.addedBy = addedBy;
        this.updatedBy = updatedBy;
        this.addedOn = addedOn;
        this.updatedOn = updatedOn;
    }

    public String getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserProfileDeviceToken() {
        return userProfileDeviceToken;
    }

    public void setUserProfileDeviceToken(String userProfileDeviceToken) {
        this.userProfileDeviceToken = userProfileDeviceToken;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAltMobile() {
        return altMobile;
    }

    public void setAltMobile(String altMobile) {
        this.altMobile = altMobile;
    }

    public String getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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
}
