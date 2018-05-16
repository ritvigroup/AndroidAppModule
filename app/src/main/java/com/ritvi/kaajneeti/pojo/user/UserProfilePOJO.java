package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 28-03-2018.
 */

public class UserProfilePOJO implements Serializable {
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
    @SerializedName("UserTypeId")
    private String userTypeId;
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @SerializedName("Gender")
    private String gender;
    @SerializedName("MaritalStatus")
    private String maritalStatus;
    @SerializedName("Email")
    private String email;
    @SerializedName("DepartmentName")
    private String departmentName;
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
    @SerializedName("UserBio")
    private String userBio;
    @SerializedName("ProfileStatus")
    private String profileStatus;
    @SerializedName("AddedBy")
    private String addedBy;
    @SerializedName("UpdatedBy")
    private String updatedBy;
    @SerializedName("ProfilePhotoId")
    private String profilePhotoId;
    @SerializedName("ProfilePhotoPath")
    private String profilePhotoPath;
    @SerializedName("CoverPhotoId")
    private String coverPhotoId;
    @SerializedName("CoverPhotoPath")
    private String coverPhotoPath;
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
    @SerializedName("MyFavouriteLeader")
    private Integer MyFavouriteLeader;
    @SerializedName("AcceptedYesNo")
    private String AcceptedYesNo;
    @SerializedName("AcceptedOn")
    private String AcceptedOn;

    private boolean is_checked;
    private int selected_position;

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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
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

    public boolean isIs_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public int getSelected_position() {
        return selected_position;
    }

    public void setSelected_position(int selected_position) {
        this.selected_position = selected_position;
    }

    public Integer getMyFavouriteLeader() {
        return MyFavouriteLeader;
    }

    public void setMyFavouriteLeader(Integer myFavouriteLeader) {
        MyFavouriteLeader = myFavouriteLeader;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getAcceptedYesNo() {
        return AcceptedYesNo;
    }

    public void setAcceptedYesNo(String acceptedYesNo) {
        AcceptedYesNo = acceptedYesNo;
    }

    public String getAcceptedOn() {
        return AcceptedOn;
    }

    public void setAcceptedOn(String acceptedOn) {
        AcceptedOn = acceptedOn;
    }
}
