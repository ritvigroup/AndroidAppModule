package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 08-02-2018.
 */

public class ProfileRolePOJO {
    @SerializedName("up_user_profile_id")
    private String upUserProfileId;
    @SerializedName("up_user_role")
    private String upUserRole;
    @SerializedName("up_first_name")
    private String upFirstName;
    @SerializedName("up_middle_name")
    private String upMiddleName;
    @SerializedName("up_last_name")
    private String upLastName;
    @SerializedName("up_created_on")
    private String upCreatedOn;
    @SerializedName("up_status")
    private String upStatus;
    @SerializedName("up_image")
    private String upImage;
    @SerializedName("up_mobile")
    private String upMobile;
    @SerializedName("up_alt_mobile")
    private String upAltMobile;

    public String getUpUserProfileId() {
        return upUserProfileId;
    }

    public void setUpUserProfileId(String upUserProfileId) {
        this.upUserProfileId = upUserProfileId;
    }

    public String getUpUserRole() {
        return upUserRole;
    }

    public void setUpUserRole(String upUserRole) {
        this.upUserRole = upUserRole;
    }

    public String getUpFirstName() {
        return upFirstName;
    }

    public void setUpFirstName(String upFirstName) {
        this.upFirstName = upFirstName;
    }

    public String getUpMiddleName() {
        return upMiddleName;
    }

    public void setUpMiddleName(String upMiddleName) {
        this.upMiddleName = upMiddleName;
    }

    public String getUpLastName() {
        return upLastName;
    }

    public void setUpLastName(String upLastName) {
        this.upLastName = upLastName;
    }

    public String getUpCreatedOn() {
        return upCreatedOn;
    }

    public void setUpCreatedOn(String upCreatedOn) {
        this.upCreatedOn = upCreatedOn;
    }

    public String getUpStatus() {
        return upStatus;
    }

    public void setUpStatus(String upStatus) {
        this.upStatus = upStatus;
    }

    public String getUpImage() {
        return upImage;
    }

    public void setUpImage(String upImage) {
        this.upImage = upImage;
    }

    public String getUpMobile() {
        return upMobile;
    }

    public void setUpMobile(String upMobile) {
        this.upMobile = upMobile;
    }

    public String getUpAltMobile() {
        return upAltMobile;
    }

    public void setUpAltMobile(String upAltMobile) {
        this.upAltMobile = upAltMobile;
    }
}
