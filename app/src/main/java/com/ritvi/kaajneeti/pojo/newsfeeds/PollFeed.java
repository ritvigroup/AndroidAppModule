package com.ritvi.kaajneeti.pojo.newsfeeds;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 14-03-2018.
 */

public class PollFeed {
    @SerializedName("PollId")
    private String pollId;
    @SerializedName("PollQuestion")
    private String pollQuestion;
    @SerializedName("PollPrivacy")
    private String pollPrivacy;
    @SerializedName("ValidFromDate")
    private String validFromDate;
    @SerializedName("ValidEndDate")
    private String validEndDate;
    @SerializedName("PollStatus")
    private Integer pollStatus;
    @SerializedName("AddedBy")
    private Integer addedBy;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("UpdatedBy")
    private String updatedBy;
    @SerializedName("UpdatedOn")
    private String updatedOn;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("profile_pic")
    private String profilePic;
    @SerializedName("poll_ans")
    private List<PollAns> pollAnsList;

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public String getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    public String getPollPrivacy() {
        return pollPrivacy;
    }

    public void setPollPrivacy(String pollPrivacy) {
        this.pollPrivacy = pollPrivacy;
    }

    public String getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(String validFromDate) {
        this.validFromDate = validFromDate;
    }

    public String getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(String validEndDate) {
        this.validEndDate = validEndDate;
    }

    public Integer getPollStatus() {
        return pollStatus;
    }

    public void setPollStatus(Integer pollStatus) {
        this.pollStatus = pollStatus;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<PollAns> getPollAnsList() {
        return pollAnsList;
    }

    public void setPollAnsList(List<PollAns> pollAnsList) {
        this.pollAnsList = pollAnsList;
    }
}
