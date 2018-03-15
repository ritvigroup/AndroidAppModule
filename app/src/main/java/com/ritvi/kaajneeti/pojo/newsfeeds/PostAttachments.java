package com.ritvi.kaajneeti.pojo.newsfeeds;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 14-03-2018.
 */

public class PostAttachments {
    @SerializedName("PostAttachmentId")
    private Integer postAttachmentId;
    @SerializedName("PostId")
    private Integer postId;
    @SerializedName("AttachmentTypeId")
    private String attachmentTypeId;
    @SerializedName("AttachmentFile")
    private String attachmentFile;
    @SerializedName("AttachmentOrginalFile")
    private String attachmentOrginalFile;
    @SerializedName("AttachmentOrder")
    private Integer attachmentOrder;
    @SerializedName("AttachmentStatus")
    private Integer attachmentStatus;
    @SerializedName("AddedBy")
    private String addedBy;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("DeletedOn")
    private String deletedOn;

    public Integer getPostAttachmentId() {
        return postAttachmentId;
    }

    public void setPostAttachmentId(Integer postAttachmentId) {
        this.postAttachmentId = postAttachmentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public Integer getAttachmentOrder() {
        return attachmentOrder;
    }

    public void setAttachmentOrder(Integer attachmentOrder) {
        this.attachmentOrder = attachmentOrder;
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
