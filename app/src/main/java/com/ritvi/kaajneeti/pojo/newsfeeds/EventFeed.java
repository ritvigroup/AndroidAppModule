package com.ritvi.kaajneeti.pojo.newsfeeds;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 14-03-2018.
 */

public class EventFeed {
    @SerializedName("EventId")
    private Integer eventId;
    @SerializedName("EventName")
    private String eventName;
    @SerializedName("EventDescription")
    private String eventDescription;
    @SerializedName("EventLocation")
    private String eventLocation;
    @SerializedName("StartDate")
    private String startDate;
    @SerializedName("EndDate")
    private String endDate;
    @SerializedName("EveryYear")
    private String everyYear;
    @SerializedName("EveryMonth")
    private String everyMonth;
    @SerializedName("AddedBy")
    private String addedBy;
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
    @SerializedName("attachments")
    private List<EventAttachment> attachments;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEveryYear() {
        return everyYear;
    }

    public void setEveryYear(String everyYear) {
        this.everyYear = everyYear;
    }

    public String getEveryMonth() {
        return everyMonth;
    }

    public void setEveryMonth(String everyMonth) {
        this.everyMonth = everyMonth;
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

    public List<EventAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EventAttachment> attachments) {
        this.attachments = attachments;
    }
}
