package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 28-03-2018.
 */

public class UserInfoPOJO implements Serializable{

    @SerializedName("UserId")
    private String userId;
    @SerializedName("UserUniqueId")
    private String userUniqueId;
    @SerializedName("LoginDeviceToken")
    private String loginDeviceToken;
    @SerializedName("UserStatus")
    private String userStatus;
    @SerializedName("LoginStatus")
    private String loginStatus;
    @SerializedName("UserName")
    private String userName;
    @SerializedName("UserEmail")
    private String userEmail;
    @SerializedName("UserMobile")
    private String userMobile;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;
    @SerializedName("UpdatedOn")
    private String updatedOn;
    @SerializedName("UpdatedOnTime")
    private String updatedOnTime;
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @SerializedName("Gender")
    private String gender;
    @SerializedName("ProfilePhotoId")
    private String profilePhotoId;
    @SerializedName("ProfilePhotoPath")
    private String profilePhotoPath;
    @SerializedName("CoverPhotoId")
    private String coverPhotoId;
    @SerializedName("CoverPhotoPath")
    private String coverPhotoPath;
    @SerializedName("FacebookProfileId")
    private String facebookProfileId;
    @SerializedName("GoogleProfileId")
    private String googleProfileId;
    @SerializedName("LinkedinProfileId")
    private String linkedinProfileId;
    @SerializedName("UserProfileLeader")
    private UserProfilePOJO userProfileLeader;
    @SerializedName("UserProfileCitizen")
    private UserProfilePOJO userProfileCitizen;
    @SerializedName("MyFavouriteLeader")
    private Integer myFavouriteLeader;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public void setUserUniqueId(String userUniqueId) {
        this.userUniqueId = userUniqueId;
    }

    public String getLoginDeviceToken() {
        return loginDeviceToken;
    }

    public void setLoginDeviceToken(String loginDeviceToken) {
        this.loginDeviceToken = loginDeviceToken;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePhotoId() {
        return profilePhotoId;
    }

    public void setProfilePhotoId(String profilePhotoId) {
        this.profilePhotoId = profilePhotoId;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getCoverPhotoId() {
        return coverPhotoId;
    }

    public void setCoverPhotoId(String coverPhotoId) {
        this.coverPhotoId = coverPhotoId;
    }

    public String getCoverPhotoPath() {
        return coverPhotoPath;
    }

    public void setCoverPhotoPath(String coverPhotoPath) {
        this.coverPhotoPath = coverPhotoPath;
    }

    public String getFacebookProfileId() {
        return facebookProfileId;
    }

    public void setFacebookProfileId(String facebookProfileId) {
        this.facebookProfileId = facebookProfileId;
    }

    public String getGoogleProfileId() {
        return googleProfileId;
    }

    public void setGoogleProfileId(String googleProfileId) {
        this.googleProfileId = googleProfileId;
    }

    public String getLinkedinProfileId() {
        return linkedinProfileId;
    }

    public void setLinkedinProfileId(String linkedinProfileId) {
        this.linkedinProfileId = linkedinProfileId;
    }

    public UserProfilePOJO getUserProfileLeader() {
        return userProfileLeader;
    }

    public void setUserProfileLeader(UserProfilePOJO userProfileLeader) {
        this.userProfileLeader = userProfileLeader;
    }

    public UserProfilePOJO getUserProfileCitizen() {
        return userProfileCitizen;
    }

    public void setUserProfileCitizen(UserProfilePOJO userProfileCitizen) {
        this.userProfileCitizen = userProfileCitizen;
    }

    public Integer getMyFavouriteLeader() {
        return myFavouriteLeader;
    }

    public void setMyFavouriteLeader(Integer myFavouriteLeader) {
        this.myFavouriteLeader = myFavouriteLeader;
    }
}
