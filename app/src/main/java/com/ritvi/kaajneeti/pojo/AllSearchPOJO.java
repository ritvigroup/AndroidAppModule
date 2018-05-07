package com.ritvi.kaajneeti.pojo;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.home.FeedPOJO;

import java.util.List;

public class AllSearchPOJO {
    @SerializedName("Event")
    List<FeedPOJO> eventFeeds;
    @SerializedName("Poll")
    List<FeedPOJO> pollFeeds;
    @SerializedName("Post")
    List<FeedPOJO> postFeeds;
    @SerializedName("Complaint")
    List<FeedPOJO> complaintFeeds;
    @SerializedName("Suggestion")
    List<FeedPOJO> suggestionFeeds;
    @SerializedName("Profile")
    List<FeedPOJO> profileFeeds;

    public List<FeedPOJO> getEventFeeds() {
        return eventFeeds;
    }

    public void setEventFeeds(List<FeedPOJO> eventFeeds) {
        this.eventFeeds = eventFeeds;
    }

    public List<FeedPOJO> getPollFeeds() {
        return pollFeeds;
    }

    public void setPollFeeds(List<FeedPOJO> pollFeeds) {
        this.pollFeeds = pollFeeds;
    }

    public List<FeedPOJO> getPostFeeds() {
        return postFeeds;
    }

    public void setPostFeeds(List<FeedPOJO> postFeeds) {
        this.postFeeds = postFeeds;
    }

    public List<FeedPOJO> getComplaintFeeds() {
        return complaintFeeds;
    }

    public void setComplaintFeeds(List<FeedPOJO> complaintFeeds) {
        this.complaintFeeds = complaintFeeds;
    }

    public List<FeedPOJO> getSuggestionFeeds() {
        return suggestionFeeds;
    }

    public void setSuggestionFeeds(List<FeedPOJO> suggestionFeeds) {
        this.suggestionFeeds = suggestionFeeds;
    }

    public List<FeedPOJO> getProfileFeeds() {
        return profileFeeds;
    }

    public void setProfileFeeds(List<FeedPOJO> profileFeeds) {
        this.profileFeeds = profileFeeds;
    }
}
