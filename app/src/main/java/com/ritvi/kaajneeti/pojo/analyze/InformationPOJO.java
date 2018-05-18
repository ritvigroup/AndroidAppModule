package com.ritvi.kaajneeti.pojo.analyze;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 31-03-2018.
 */

public class InformationPOJO implements Serializable {
    @SerializedName("InformationId")
    private String informationId;
    @SerializedName("InformationUniqueId")
    private String informationUniqueId;
    @SerializedName("InformationPrivacy")
    private String informationPrivacy;
    @SerializedName("ApplicantName")
    private String applicantName;
    @SerializedName("ApplicantFatherName")
    private String applicantFatherName;
    @SerializedName("ApplicantGender")
    private Object applicantGender;
    @SerializedName("ApplicantMobile")
    private String applicantMobile;
    @SerializedName("ApplicantEmail")
    private Object applicantEmail;
    @SerializedName("ApplicantAadhaarNumber")
    private Object applicantAadhaarNumber;
    @SerializedName("InformationSubject")
    private String informationSubject;
    @SerializedName("InformationDescription")
    private String informationDescription;
    @SerializedName("InformationStatus")
    private String informationStatus;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;
    @SerializedName("UpdatedOn")
    private String updatedOn;
    @SerializedName("UpdatedOnTime")
    private String updatedOnTime;
    @SerializedName("InformationProfile")
    private UserProfilePOJO informationProfile;
    @SerializedName("InformationAttachment")
    private List<InformationAttachmentPOJO> informationAttachment;

    public String getInformationId() {
        return informationId;
    }

    public void setInformationId(String informationId) {
        this.informationId = informationId;
    }

    public String getInformationUniqueId() {
        return informationUniqueId;
    }

    public void setInformationUniqueId(String informationUniqueId) {
        this.informationUniqueId = informationUniqueId;
    }

    public String getInformationPrivacy() {
        return informationPrivacy;
    }

    public void setInformationPrivacy(String informationPrivacy) {
        this.informationPrivacy = informationPrivacy;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantFatherName() {
        return applicantFatherName;
    }

    public void setApplicantFatherName(String applicantFatherName) {
        this.applicantFatherName = applicantFatherName;
    }

    public Object getApplicantGender() {
        return applicantGender;
    }

    public void setApplicantGender(Object applicantGender) {
        this.applicantGender = applicantGender;
    }

    public String getApplicantMobile() {
        return applicantMobile;
    }

    public void setApplicantMobile(String applicantMobile) {
        this.applicantMobile = applicantMobile;
    }

    public Object getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(Object applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public Object getApplicantAadhaarNumber() {
        return applicantAadhaarNumber;
    }

    public void setApplicantAadhaarNumber(Object applicantAadhaarNumber) {
        this.applicantAadhaarNumber = applicantAadhaarNumber;
    }

    public String getInformationSubject() {
        return informationSubject;
    }

    public void setInformationSubject(String informationSubject) {
        this.informationSubject = informationSubject;
    }

    public String getInformationDescription() {
        return informationDescription;
    }

    public void setInformationDescription(String informationDescription) {
        this.informationDescription = informationDescription;
    }

    public String getInformationStatus() {
        return informationStatus;
    }

    public void setInformationStatus(String informationStatus) {
        this.informationStatus = informationStatus;
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

    public UserProfilePOJO getInformationProfile() {
        return informationProfile;
    }

    public void setInformationProfile(UserProfilePOJO informationProfile) {
        this.informationProfile = informationProfile;
    }

    public List<InformationAttachmentPOJO> getInformationAttachment() {
        return informationAttachment;
    }

    public void setInformationAttachment(List<InformationAttachmentPOJO> informationAttachment) {
        this.informationAttachment = informationAttachment;
    }
}
