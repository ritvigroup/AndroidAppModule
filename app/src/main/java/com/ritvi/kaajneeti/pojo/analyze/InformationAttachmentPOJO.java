package com.ritvi.kaajneeti.pojo.analyze;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 31-03-2018.
 */

public class InformationAttachmentPOJO implements Serializable{
    @SerializedName("InformationAttachmentId")
    private String informationAttachmentId;
    @SerializedName("InformationId")
    private String informationId;
    @SerializedName("AttachmentTypeId")
    private String attachmentTypeId;
    @SerializedName("AttachmentType")
    private String attachmentType;
    @SerializedName("AttachmentFile")
    private String attachmentFile;
    @SerializedName("AttachmentOrginalFile")
    private String attachmentOrginalFile;
    @SerializedName("AttachmentOrder")
    private String attachmentOrder;
    @SerializedName("AttachmentStatus")
    private String attachmentStatus;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;

    public String getInformationAttachmentId() {
        return informationAttachmentId;
    }

    public void setInformationAttachmentId(String informationAttachmentId) {
        this.informationAttachmentId = informationAttachmentId;
    }

    public String getInformationId() {
        return informationId;
    }

    public void setInformationId(String informationId) {
        this.informationId = informationId;
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
