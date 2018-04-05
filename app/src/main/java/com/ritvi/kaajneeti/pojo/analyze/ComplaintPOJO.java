package com.ritvi.kaajneeti.pojo.analyze;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 31-03-2018.
 */

public class ComplaintPOJO implements Serializable {
    @SerializedName("ComplaintId")
    private String complaintId;
    @SerializedName("ComplaintUniqueId")
    private String complaintUniqueId;
    @SerializedName("ComplaintTypeId")
    private String complaintTypeId;
    @SerializedName("ApplicantName")
    private String applicantName;
    @SerializedName("ApplicantFatherName")
    private String applicantFatherName;
    @SerializedName("ComplaintDepartment")
    private String complaintDepartment;
    @SerializedName("ComplaintSubject")
    private String complaintSubject;
    @SerializedName("ComplaintDescription")
    private String complaintDescription;
    @SerializedName("ComplaintStatus")
    private String complaintStatus;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("UpdatedOn")
    private String updatedOn;
    @SerializedName("ComplaintProfile")
    private OutGoingRequestPOJO complaintProfile;
    @SerializedName("ComplaintMember")
    private List<OutGoingRequestPOJO> complaintMember;
    @SerializedName("ComplaintAttachment")
    private List<ComplaintAttachmentPOJO> complaintAttachmentPOJOS;

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getComplaintUniqueId() {
        return complaintUniqueId;
    }

    public void setComplaintUniqueId(String complaintUniqueId) {
        this.complaintUniqueId = complaintUniqueId;
    }

    public String getComplaintTypeId() {
        return complaintTypeId;
    }

    public void setComplaintTypeId(String complaintTypeId) {
        this.complaintTypeId = complaintTypeId;
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

    public String getComplaintDepartment() {
        return complaintDepartment;
    }

    public void setComplaintDepartment(String complaintDepartment) {
        this.complaintDepartment = complaintDepartment;
    }

    public String getComplaintSubject() {
        return complaintSubject;
    }

    public void setComplaintSubject(String complaintSubject) {
        this.complaintSubject = complaintSubject;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
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

    public OutGoingRequestPOJO getComplaintProfile() {
        return complaintProfile;
    }

    public void setComplaintProfile(OutGoingRequestPOJO complaintProfile) {
        this.complaintProfile = complaintProfile;
    }

    public List<OutGoingRequestPOJO> getComplaintMember() {
        return complaintMember;
    }

    public void setComplaintMember(List<OutGoingRequestPOJO> complaintMember) {
        this.complaintMember = complaintMember;
    }

    public List<ComplaintAttachmentPOJO> getComplaintAttachmentPOJOS() {
        return complaintAttachmentPOJOS;
    }

    public void setComplaintAttachmentPOJOS(List<ComplaintAttachmentPOJO> complaintAttachmentPOJOS) {
        this.complaintAttachmentPOJOS = complaintAttachmentPOJOS;
    }
}
