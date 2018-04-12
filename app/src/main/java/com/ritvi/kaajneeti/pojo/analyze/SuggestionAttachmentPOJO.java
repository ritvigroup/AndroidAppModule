package com.ritvi.kaajneeti.pojo.analyze;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 31-03-2018.
 */

public class SuggestionAttachmentPOJO implements Serializable{
    @SerializedName("SuggestionAttachmentId")
    private String suggestionAttachmentId;
    @SerializedName("SuggestionId")
    private String suggestionId;
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
    @SerializedName("AddedOn")
    private String addedOn;

    public String getSuggestionAttachmentId() {
        return suggestionAttachmentId;
    }

    public void setSuggestionAttachmentId(String suggestionAttachmentId) {
        this.suggestionAttachmentId = suggestionAttachmentId;
    }

    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
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
}
