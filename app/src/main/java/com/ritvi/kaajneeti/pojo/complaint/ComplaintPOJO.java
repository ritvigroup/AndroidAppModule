package com.ritvi.kaajneeti.pojo.complaint;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;

import java.util.List;

/**
 * Created by sunil on 19-02-2018.
 */

public class ComplaintPOJO {
    @SerializedName("ComplaintHistoryId")
    private String complaintHistoryId;
    @SerializedName("ComplaintId")
    private String complaintId;
    @SerializedName("ParentComplaintHistoryId")
    private String parentComplaintHistoryId;
    @SerializedName("HistoryTitle")
    private String historyTitle;
    @SerializedName("HistoryDescription")
    private String historyDescription;
    @SerializedName("HistoryStatus")
    private String historyStatus;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;
    @SerializedName("ComplaintHistoryProfile")
    private OutGoingRequestPOJO complaintHistoryProfile;
    @SerializedName("ComplaintHistoryAttachment")
    private List<Object> complaintHistoryAttachment = null;
    @SerializedName("ComplaintHistoryHistory")
    private List<Object> complaintHistoryHistory = null;

    public String getComplaintHistoryId() {
        return complaintHistoryId;
    }

    public void setComplaintHistoryId(String complaintHistoryId) {
        this.complaintHistoryId = complaintHistoryId;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getParentComplaintHistoryId() {
        return parentComplaintHistoryId;
    }

    public void setParentComplaintHistoryId(String parentComplaintHistoryId) {
        this.parentComplaintHistoryId = parentComplaintHistoryId;
    }

    public String getHistoryTitle() {
        return historyTitle;
    }

    public void setHistoryTitle(String historyTitle) {
        this.historyTitle = historyTitle;
    }

    public String getHistoryDescription() {
        return historyDescription;
    }

    public void setHistoryDescription(String historyDescription) {
        this.historyDescription = historyDescription;
    }

    public String getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(String historyStatus) {
        this.historyStatus = historyStatus;
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

    public OutGoingRequestPOJO getComplaintHistoryProfile() {
        return complaintHistoryProfile;
    }

    public void setComplaintHistoryProfile(OutGoingRequestPOJO complaintHistoryProfile) {
        this.complaintHistoryProfile = complaintHistoryProfile;
    }

    public List<Object> getComplaintHistoryAttachment() {
        return complaintHistoryAttachment;
    }

    public void setComplaintHistoryAttachment(List<Object> complaintHistoryAttachment) {
        this.complaintHistoryAttachment = complaintHistoryAttachment;
    }

    public List<Object> getComplaintHistoryHistory() {
        return complaintHistoryHistory;
    }

    public void setComplaintHistoryHistory(List<Object> complaintHistoryHistory) {
        this.complaintHistoryHistory = complaintHistoryHistory;
    }
}
