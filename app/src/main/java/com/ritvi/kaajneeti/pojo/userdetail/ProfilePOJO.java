package com.ritvi.kaajneeti.pojo.userdetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 30-03-2018.
 */

public class ProfilePOJO implements Serializable{
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
    @SerializedName("PoliticalPartyId")
    private String politicalPartyId;
    @SerializedName("PoliticalPartyName")
    private String politicalPartyName;
    @SerializedName("Address")
    private String address;
    @SerializedName("City")
    private String city;
    @SerializedName("State")
    private String state;
    @SerializedName("ZipCode")
    private String zipCode;
    @SerializedName("Country")
    private String country;
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
    @SerializedName("WebsiteUrl")
    private String websiteUrl;
    @SerializedName("FacebookPageUrl")
    private String facebookPageUrl;
    @SerializedName("TwitterPageUrl")
    private String twitterPageUrl;
    @SerializedName("GooglePageUrl")
    private String googlePageUrl;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;
    @SerializedName("UpdatedOn")
    private String updatedOn;
    @SerializedName("UpdatedOnTime")
    private String updatedOnTime;
    @SerializedName("MyFriend")
    private Integer myFriend;
    @SerializedName("Following")
    private Integer following;
    @SerializedName("Follower")
    private Integer follower;

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

    public String getPoliticalPartyId() {
        return politicalPartyId;
    }

    public void setPoliticalPartyId(String politicalPartyId) {
        this.politicalPartyId = politicalPartyId;
    }

    public String getPoliticalPartyName() {
        return politicalPartyName;
    }

    public void setPoliticalPartyName(String politicalPartyName) {
        this.politicalPartyName = politicalPartyName;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getFacebookPageUrl() {
        return facebookPageUrl;
    }

    public void setFacebookPageUrl(String facebookPageUrl) {
        this.facebookPageUrl = facebookPageUrl;
    }

    public String getTwitterPageUrl() {
        return twitterPageUrl;
    }

    public void setTwitterPageUrl(String twitterPageUrl) {
        this.twitterPageUrl = twitterPageUrl;
    }

    public String getGooglePageUrl() {
        return googlePageUrl;
    }

    public void setGooglePageUrl(String googlePageUrl) {
        this.googlePageUrl = googlePageUrl;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getAddedOnTime() {
        return addedOnTime;
    }

    public void setAddedOnTime(String addedOnTime) {
        this.addedOnTime = addedOnTime;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedOnTime() {
        return updatedOnTime;
    }

    public void setUpdatedOnTime(String updatedOnTime) {
        this.updatedOnTime = updatedOnTime;
    }

    public Integer getMyFriend() {
        return myFriend;
    }

    public void setMyFriend(Integer myFriend) {
        this.myFriend = myFriend;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }
}
