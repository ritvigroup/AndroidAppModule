package com.ritvi.kaajneeti.pojo.newsfeeds;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 14-03-2018.
 */

public class PostFeed {
    @SerializedName("PostId")
    String PostId;
    @SerializedName("UserProfileId")
    String UserProfileId;
    @SerializedName("PostTitle")
    String PostTitle;
    @SerializedName("PostStatus")
    String PostStatus;
    @SerializedName("AddedOn")
    String AddedOn;
    @SerializedName("UpdatedOn")
    String UpdatedOn;
    @SerializedName("PostLocation")
    String PostLocation;
    @SerializedName("PostDescription")
    String PostDescription;
    @SerializedName("feeling")
    String feeling;
    @SerializedName("first_name")
    String first_name;
    @SerializedName("last_name")
    String last_name;
    @SerializedName("profile_pic")
    String profile_pic;
    @SerializedName("PostURL")
    String PostURL;
    @SerializedName("taggedUsers")
    List<Taggedusers> taggedusersList;
    @SerializedName("uploads")
    List<PostAttachments> postAttachments;

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

    public String getPostTitle() {
        return PostTitle;
    }

    public void setPostTitle(String postTitle) {
        PostTitle = postTitle;
    }

    public String getPostStatus() {
        return PostStatus;
    }

    public void setPostStatus(String postStatus) {
        PostStatus = postStatus;
    }

    public String getAddedOn() {
        return AddedOn;
    }

    public void setAddedOn(String addedOn) {
        AddedOn = addedOn;
    }

    public String getUpdatedOn() {
        return UpdatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        UpdatedOn = updatedOn;
    }

    public String getPostLocation() {
        return PostLocation;
    }

    public void setPostLocation(String postLocation) {
        PostLocation = postLocation;
    }

    public String getPostDescription() {
        return PostDescription;
    }

    public void setPostDescription(String postDescription) {
        PostDescription = postDescription;
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

    public String getPostURL() {
        return PostURL;
    }

    public void setPostURL(String postURL) {
        PostURL = postURL;
    }

    public List<Taggedusers> getTaggedusersList() {
        return taggedusersList;
    }

    public void setTaggedusersList(List<Taggedusers> taggedusersList) {
        this.taggedusersList = taggedusersList;
    }

    public List<PostAttachments> getPostAttachments() {
        return postAttachments;
    }

    public void setPostAttachments(List<PostAttachments> postAttachments) {
        this.postAttachments = postAttachments;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }
}
