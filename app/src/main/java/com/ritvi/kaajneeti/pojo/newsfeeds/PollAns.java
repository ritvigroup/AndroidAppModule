package com.ritvi.kaajneeti.pojo.newsfeeds;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 14-03-2018.
 */

public class PollAns {
    @SerializedName("PollAnswerId")
    private Integer pollAnswerId;
    @SerializedName("PollId")
    private Integer pollId;
    @SerializedName("PollAnswer")
    private String pollAnswer;
    @SerializedName("PollAnswerStatus")
    private Integer pollAnswerStatus;
    @SerializedName("AddedBy")
    private String addedBy;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("UpdatedBy")
    private String updatedBy;
    @SerializedName("UpdatedOn")
    private String updatedOn;

    public Integer getPollAnswerId() {
        return pollAnswerId;
    }

    public void setPollAnswerId(Integer pollAnswerId) {
        this.pollAnswerId = pollAnswerId;
    }

    public Integer getPollId() {
        return pollId;
    }

    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    public String getPollAnswer() {
        return pollAnswer;
    }

    public void setPollAnswer(String pollAnswer) {
        this.pollAnswer = pollAnswer;
    }

    public Integer getPollAnswerStatus() {
        return pollAnswerStatus;
    }

    public void setPollAnswerStatus(Integer pollAnswerStatus) {
        this.pollAnswerStatus = pollAnswerStatus;
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
}
