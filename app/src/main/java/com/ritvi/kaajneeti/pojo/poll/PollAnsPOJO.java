package com.ritvi.kaajneeti.pojo.poll;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 22-03-2018.
 */

public class PollAnsPOJO {
    @SerializedName("PollAnswerId")
    private String pollAnswerId;
    @SerializedName("PollId")
    private String pollId;
    @SerializedName("PollAnswer")
    private String pollAnswer;
    @SerializedName("PollAnswerStatus")
    private String pollAnswerStatus;
    @SerializedName("TotalAnswerdMe")
    private Integer totalAnswerdMe;

    public String getPollAnswerId() {
        return pollAnswerId;
    }

    public void setPollAnswerId(String pollAnswerId) {
        this.pollAnswerId = pollAnswerId;
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public String getPollAnswer() {
        return pollAnswer;
    }

    public void setPollAnswer(String pollAnswer) {
        this.pollAnswer = pollAnswer;
    }

    public String getPollAnswerStatus() {
        return pollAnswerStatus;
    }

    public void setPollAnswerStatus(String pollAnswerStatus) {
        this.pollAnswerStatus = pollAnswerStatus;
    }

    public Integer getTotalAnswerdMe() {
        return totalAnswerdMe;
    }

    public void setTotalAnswerdMe(Integer totalAnswerdMe) {
        this.totalAnswerdMe = totalAnswerdMe;
    }
}
