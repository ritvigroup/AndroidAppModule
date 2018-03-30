package com.ritvi.kaajneeti.pojo.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 30-03-2018.
 */

public class EventAttachmentPOJO implements Serializable{
    @SerializedName("EventAttachmentId")
    private String eventAttachmentId;
    @SerializedName("EventId")
    private String eventId;
    @SerializedName("AttachmentTypeId")
    private String attachmentTypeId;
    @SerializedName("AttachmentType")
    private String attachmentType;
    @SerializedName("AttachmentFile")
    private String attachmentFile;
    @SerializedName("AttachmentOrginalFile")
    private String attachmentOrginalFile;
    @SerializedName("AttachmentThumb")
    private String attachmentThumb;
    @SerializedName("AttachmentOrder")
    private String attachmentOrder;
    @SerializedName("AttachmentStatus")
    private String attachmentStatus;
//    @SerializedName("AddedBy")
//    private AddedBy addedBy;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;

    public String getEventAttachmentId() {
        return eventAttachmentId;
    }

    public void setEventAttachmentId(String eventAttachmentId) {
        this.eventAttachmentId = eventAttachmentId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(String attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
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

    public String getAttachmentThumb() {
        return attachmentThumb;
    }

    public void setAttachmentThumb(String attachmentThumb) {
        this.attachmentThumb = attachmentThumb;
    }

    public String getAttachmentOrder() {
        return attachmentOrder;
    }

    public void setAttachmentOrder(String attachmentOrder) {
        this.attachmentOrder = attachmentOrder;
    }

    public String getAttachmentStatus() {
        return attachmentStatus;
    }

    public void setAttachmentStatus(String attachmentStatus) {
        this.attachmentStatus = attachmentStatus;
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
}
