package com.ritvi.kaajneeti.pojo.newsfeeds;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 14-03-2018.
 */

public class Taggedusers {
    @SerializedName("PostTagId")
    String PostTagId;
    @SerializedName("PostId")
    String PostId;
    @SerializedName("UserProfileId")
    String UserProfileId;
    @SerializedName("TagStatus")
    String TagStatus;
    @SerializedName("TagApproved")
    String TagApproved;
    @SerializedName("AddedBy")
    String AddedBy;
    @SerializedName("first_name")
    String first_name;
    @SerializedName("last_name")
    String last_name;
    @SerializedName("profile_pic")
    String profile_pic;
    @SerializedName("AddedOn")
    String AddedOn;

    public String getPostTagId() {
        return PostTagId;
    }

    public void setPostTagId(String postTagId) {
        PostTagId = postTagId;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getUserProfileId() {
        return UserProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        UserProfileId = userProfileId;
    }

    public String getTagStatus() {
        return TagStatus;
    }

    public void setTagStatus(String tagStatus) {
        TagStatus = tagStatus;
    }

    public String getTagApproved() {
        return TagApproved;
    }

    public void setTagApproved(String tagApproved) {
        TagApproved = tagApproved;
    }

    public String getAddedBy() {
        return AddedBy;
    }

    public void setAddedBy(String addedBy) {
        AddedBy = addedBy;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getAddedOn() {
        return AddedOn;
    }

    public void setAddedOn(String addedOn) {
        AddedOn = addedOn;
    }
}
