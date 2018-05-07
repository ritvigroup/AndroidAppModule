package com.ritvi.kaajneeti.pojo;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.io.Serializable;
import java.util.List;

public class GroupPOJO implements Serializable{
    @SerializedName("FriendGroupId")
    private String friendGroupId;
    @SerializedName("FriendGroupName")
    private String friendGroupName;
    @SerializedName("FriendGroupPhoto")
    private String friendGroupPhoto;
    @SerializedName("FriendGroupStatus")
    private String friendGroupStatus;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;
    @SerializedName("UpdatedOn")
    private String updatedOn;
    @SerializedName("UpdatedOnTime")
    private String updatedOnTime;
    @SerializedName("GroupProfile")
    private UserProfilePOJO groupProfile;
    @SerializedName("GroupMembers")
    private List<UserProfilePOJO> groupMembers;

    public String getFriendGroupId() {
        return friendGroupId;
    }

    public void setFriendGroupId(String friendGroupId) {
        this.friendGroupId = friendGroupId;
    }

    public String getFriendGroupName() {
        return friendGroupName;
    }

    public void setFriendGroupName(String friendGroupName) {
        this.friendGroupName = friendGroupName;
    }

    public String getFriendGroupPhoto() {
        return friendGroupPhoto;
    }

    public void setFriendGroupPhoto(String friendGroupPhoto) {
        this.friendGroupPhoto = friendGroupPhoto;
    }

    public String getFriendGroupStatus() {
        return friendGroupStatus;
    }

    public void setFriendGroupStatus(String friendGroupStatus) {
        this.friendGroupStatus = friendGroupStatus;
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

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedOnTime() {
        return updatedOnTime;
    }

    public void setUpdatedOnTime(String updatedOnTime) {
        this.updatedOnTime = updatedOnTime;
    }

    public UserProfilePOJO getGroupProfile() {
        return groupProfile;
    }

    public void setGroupProfile(UserProfilePOJO groupProfile) {
        this.groupProfile = groupProfile;
    }

    public List<UserProfilePOJO> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<UserProfilePOJO> groupMembers) {
        this.groupMembers = groupMembers;
    }
}
