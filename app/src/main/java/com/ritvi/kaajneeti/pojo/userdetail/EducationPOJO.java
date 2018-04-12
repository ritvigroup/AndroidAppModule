package com.ritvi.kaajneeti.pojo.userdetail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 12-04-2018.
 */

public class EducationPOJO {
    @SerializedName("UserProfileEducationId")
    private String userProfileEducationId;
    @SerializedName("UserProfileId")
    private String userProfileId;
    @SerializedName("Qualification")
    private String qualification;
    @SerializedName("QualificationLocation")
    private String qualificationLocation;
    @SerializedName("QualificationUniversity")
    private String qualificationUniversity;
    @SerializedName("QualificationFrom")
    private String qualificationFrom;
    @SerializedName("QualificationTo")
    private String qualificationTo;
    @SerializedName("Persuing")
    private String persuing;
    @SerializedName("Status")
    private String status;
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

    public String getUserProfileEducationId() {
        return userProfileEducationId;
    }

    public void setUserProfileEducationId(String userProfileEducationId) {
        this.userProfileEducationId = userProfileEducationId;
    }

    public String getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getQualificationLocation() {
        return qualificationLocation;
    }

    public void setQualificationLocation(String qualificationLocation) {
        this.qualificationLocation = qualificationLocation;
    }

    public String getQualificationUniversity() {
        return qualificationUniversity;
    }

    public void setQualificationUniversity(String qualificationUniversity) {
        this.qualificationUniversity = qualificationUniversity;
    }

    public String getQualificationFrom() {
        return qualificationFrom;
    }

    public void setQualificationFrom(String qualificationFrom) {
        this.qualificationFrom = qualificationFrom;
    }

    public String getQualificationTo() {
        return qualificationTo;
    }

    public void setQualificationTo(String qualificationTo) {
        this.qualificationTo = qualificationTo;
    }

    public String getPersuing() {
        return persuing;
    }

    public void setPersuing(String persuing) {
        this.persuing = persuing;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
