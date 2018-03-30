package com.ritvi.kaajneeti.pojo.home;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 30-03-2018.
 */

public class PollPOJO implements Serializable{
    @SerializedName("PollId")
    private String pollId;
    @SerializedName("PollUniqueId")
    private String pollUniqueId;
    @SerializedName("PollQuestion")
    private String pollQuestion;
    @SerializedName("ValidFromDate")
    private String validFromDate;
    @SerializedName("ValidEndDate")
    private String validEndDate;
    @SerializedName("PollPrivacy")
    private String pollPrivacy;
    @SerializedName("PollStatus")
    private String pollStatus;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;
    @SerializedName("UpdatedOn")
    private String updatedOn;
    @SerializedName("UpdatedOnTime")
    private String updatedOnTime;
    @SerializedName("PollProfile")
    private OutGoingRequestPOJO profileDetailPOJO;
    @SerializedName("PollTotalParticipation")
    private Integer pollTotalParticipation;
    @SerializedName("PollAnswerWithTotalParticipation")
    private List<PollAnsPOJO> pollAnsPOJOS;

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public String getPollUniqueId() {
        return pollUniqueId;
    }

    public void setPollUniqueId(String pollUniqueId) {
        this.pollUniqueId = pollUniqueId;
    }

    public String getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
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

    public String getPollPrivacy() {
        return pollPrivacy;
    }

    public void setPollPrivacy(String pollPrivacy) {
        this.pollPrivacy = pollPrivacy;
    }

    public String getPollStatus() {
        return pollStatus;
    }

    public void setPollStatus(String pollStatus) {
        this.pollStatus = pollStatus;
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

    public OutGoingRequestPOJO getProfileDetailPOJO() {
        return profileDetailPOJO;
    }

    public void setProfileDetailPOJO(OutGoingRequestPOJO profileDetailPOJO) {
        this.profileDetailPOJO = profileDetailPOJO;
    }

    public Integer getPollTotalParticipation() {
        return pollTotalParticipation;
    }

    public void setPollTotalParticipation(Integer pollTotalParticipation) {
        this.pollTotalParticipation = pollTotalParticipation;
    }

    public List<PollAnsPOJO> getPollAnsPOJOS() {
        return pollAnsPOJOS;
    }

    public void setPollAnsPOJOS(List<PollAnsPOJO> pollAnsPOJOS) {
        this.pollAnsPOJOS = pollAnsPOJOS;
    }
}
