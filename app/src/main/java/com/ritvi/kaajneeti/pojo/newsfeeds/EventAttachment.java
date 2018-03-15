package com.ritvi.kaajneeti.pojo.newsfeeds;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 14-03-2018.
 */

public class EventAttachment {
    @SerializedName("EventAttachmentId")
    private Integer eventAttachmentId;
    @SerializedName("EventId")
    private Integer eventId;
    @SerializedName("AttachmentTypeId")
    private String attachmentTypeId;
    @SerializedName("AttachmentFile")
    private String attachmentFile;
    @SerializedName("AttachmentOrginalFile")
    private String attachmentOrginalFile;
    @SerializedName("AttachmentOrder")
    private String attachmentOrder;
    @SerializedName("EventMain")
    private Integer eventMain;
    @SerializedName("AttachmentStatus")
    private Integer attachmentStatus;
    @SerializedName("AddedBy")
    private String addedBy;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("DeletedOn")
    private String deletedOn;

    public Integer getEventAttachmentId() {
        return eventAttachmentId;
    }

    public void setEventAttachmentId(Integer eventAttachmentId) {
        this.eventAttachmentId = eventAttachmentId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(String attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }

    public String getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(String attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public String getAttachmentOrginalFile() {
        return attachmentOrginalFile;
    }

    public void setAttachmentOrginalFile(String attachmentOrginalFile) {
        this.attachmentOrginalFile = attachmentOrginalFile;
    }

    public String getAttachmentOrder() {
        return attachmentOrder;
    }

    public void setAttachmentOrder(String attachmentOrder) {
        this.attachmentOrder = attachmentOrder;
    }

    public Integer getEventMain() {
        return eventMain;
    }

    public void setEventMain(Integer eventMain) {
        this.eventMain = eventMain;
    }

    public Integer getAttachmentStatus() {
        return attachmentStatus;
    }

    public void setAttachmentStatus(Integer attachmentStatus) {
        this.attachmentStatus = attachmentStatus;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(String deletedOn) {
        this.deletedOn = deletedOn;
    }
}
