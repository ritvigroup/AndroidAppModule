package com.ritvi.kaajneeti.pojo.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 30-03-2018.
 */

public class FeedPOJO implements Serializable{
    @SerializedName("feedtype")
    String feedtype;
    @SerializedName("polldata")
    PollPOJO pollPOJO;
    @SerializedName("eventdata")
    EventPOJO eventPOJO;
    @SerializedName("postdata")
    PostPOJO postPOJO;

    public String getFeedtype() {
        return feedtype;
    }

    public void setFeedtype(String feedtype) {
        this.feedtype = feedtype;
    }

    public PollPOJO getPollPOJO() {
        return pollPOJO;
    }

    public void setPollPOJO(PollPOJO pollPOJO) {
        this.pollPOJO = pollPOJO;
    }

    public EventPOJO getEventPOJO() {
        return eventPOJO;
    }

    public void setEventPOJO(EventPOJO eventPOJO) {
        this.eventPOJO = eventPOJO;
    }

    public PostPOJO getPostPOJO() {
        return postPOJO;
    }

    public void setPostPOJO(PostPOJO postPOJO) {
        this.postPOJO = postPOJO;
    }
}
