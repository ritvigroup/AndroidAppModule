package com.ritvi.kaajneeti.pojo.analyze;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 31-03-2018.
 */

public class SuggestionPOJO implements Serializable {
    @SerializedName("SuggestionId")
    private String suggestionId;
    @SerializedName("SuggestionUniqueId")
    private String suggestionUniqueId;
    @SerializedName("ApplicantName")
    private String applicantName;
    @SerializedName("ApplicantFatherName")
    private String applicantFatherName;
    @SerializedName("ApplicantGender")
    private String applicantGender;
    @SerializedName("ApplicantMobile")
    private String applicantMobile;
    @SerializedName("ApplicantEmail")
    private String applicantEmail;
    @SerializedName("ApplicantAadhaarNumber")
    private String applicantAadhaarNumber;
    @SerializedName("SuggestionSubject")
    private String suggestionSubject;
    @SerializedName("SuggestionDescription")
    private String suggestionDescription;
    @SerializedName("SuggestionStatus")
    private String suggestionStatus;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;
    @SerializedName("UpdatedOn")
    private String updatedOn;
    @SerializedName("UpdatedOnTime")
    private String updatedOnTime;
    @SerializedName("SuggestionProfile")
    private OutGoingRequestPOJO suggestionProfile;
    @SerializedName("SuggestionAttachment")
    private List<SuggestionAttachmentPOJO> suggestionAttachment;

    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getSuggestionUniqueId() {
        return suggestionUniqueId;
    }

    public void setSuggestionUniqueId(String suggestionUniqueId) {
        this.suggestionUniqueId = suggestionUniqueId;
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

    public String getApplicantGender() {
        return applicantGender;
    }

    public void setApplicantGender(String applicantGender) {
        this.applicantGender = applicantGender;
    }

    public String getApplicantMobile() {
        return applicantMobile;
    }

    public void setApplicantMobile(String applicantMobile) {
        this.applicantMobile = applicantMobile;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getApplicantAadhaarNumber() {
        return applicantAadhaarNumber;
    }

    public void setApplicantAadhaarNumber(String applicantAadhaarNumber) {
        this.applicantAadhaarNumber = applicantAadhaarNumber;
    }

    public String getSuggestionSubject() {
        return suggestionSubject;
    }

    public void setSuggestionSubject(String suggestionSubject) {
        this.suggestionSubject = suggestionSubject;
    }

    public String getSuggestionDescription() {
        return suggestionDescription;
    }

    public void setSuggestionDescription(String suggestionDescription) {
        this.suggestionDescription = suggestionDescription;
    }

    public String getSuggestionStatus() {
        return suggestionStatus;
    }

    public void setSuggestionStatus(String suggestionStatus) {
        this.suggestionStatus = suggestionStatus;
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

    public OutGoingRequestPOJO getSuggestionProfile() {
        return suggestionProfile;
    }

    public void setSuggestionProfile(OutGoingRequestPOJO suggestionProfile) {
        this.suggestionProfile = suggestionProfile;
    }

    public List<SuggestionAttachmentPOJO> getSuggestionAttachment() {
        return suggestionAttachment;
    }

    public void setSuggestionAttachment(List<SuggestionAttachmentPOJO> suggestionAttachment) {
        this.suggestionAttachment = suggestionAttachment;
    }
}
