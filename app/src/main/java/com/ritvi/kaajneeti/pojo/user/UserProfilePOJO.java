package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 31-01-2018.
 */

public class UserProfilePOJO {

    @SerializedName("citizen_id")
    private String citizenId;
    @SerializedName("profile_id")
    private String profileId;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("middlename")
    private String middlename;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("alt_mobile")
    private String altMobile;
    @SerializedName("gender")
    private String gender;
    @SerializedName("status")
    private String status;
    @SerializedName("created_on")
    private String createdOn;
    @SerializedName("updated_on")
    private String updatedOn;
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("country")
    private String country;
    @SerializedName("zipcode")
    private String zipcode;
    @SerializedName("about_me")
    private String aboutMe;
    @SerializedName("device_token")
    private String deviceToken;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    @SerializedName("profile_image")
    private String profileImage;
    @SerializedName("cover_image")
    private String coverImage;
    @SerializedName("facebook_profile_id")
    private String facebookProfileId;
    @SerializedName("google_profile_id")
    private String googleProfileId;
    @SerializedName("twitter_profile_id")
    private String twitterProfileId;

    public UserProfilePOJO(String citizenId, String profileId, String firstname, String middlename, String lastname, String fullname, String email, String username, String mobile, String altMobile, String gender, String status, String createdOn, String updatedOn, String address, String city, String state, String country, String zipcode, String aboutMe, String deviceToken, String dateOfBirth, String profileImage, String coverImage, String facebookProfileId, String googleProfileId, String twitterProfileId) {
        this.citizenId = citizenId;
        this.profileId = profileId;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.fullname = fullname;
        this.email = email;
        this.username = username;
        this.mobile = mobile;
        this.altMobile = altMobile;
        this.gender = gender;
        this.status = status;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
        this.aboutMe = aboutMe;
        this.deviceToken = deviceToken;
        this.dateOfBirth = dateOfBirth;
        this.profileImage = profileImage;
        this.coverImage = coverImage;
        this.facebookProfileId = facebookProfileId;
        this.googleProfileId = googleProfileId;
        this.twitterProfileId = twitterProfileId;
    }


    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
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

    public String getTwitterProfileId() {
        return twitterProfileId;
    }

    public void setTwitterProfileId(String twitterProfileId) {
        this.twitterProfileId = twitterProfileId;
    }

    @Override
    public String toString() {
        return "UserProfilePOJO{" +
                "citizenId='" + citizenId + '\'' +
                ", profileId='" + profileId + '\'' +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", altMobile='" + altMobile + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", updatedOn='" + updatedOn + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", facebookProfileId='" + facebookProfileId + '\'' +
                ", googleProfileId='" + googleProfileId + '\'' +
                ", twitterProfileId='" + twitterProfileId + '\'' +
                '}';
    }
}
