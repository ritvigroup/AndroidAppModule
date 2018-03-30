package com.ritvi.kaajneeti.pojo.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 30-03-2018.
 */

public class PostAttachmentPOJO implements Serializable{
    @SerializedName("PostAttachmentId")
    private String postAttachmentId;
    @SerializedName("PostId")
    private String postId;
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

    public String getPostAttachmentId() {
        return postAttachmentId;
    }

    public void setPostAttachmentId(String postAttachmentId) {
        this.postAttachmentId = postAttachmentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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
