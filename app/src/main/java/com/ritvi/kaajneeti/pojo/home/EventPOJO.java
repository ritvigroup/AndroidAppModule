package com.ritvi.kaajneeti.pojo.home;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 30-03-2018.
 */

public class EventPOJO implements Serializable {
    @SerializedName("EventId")
    private String eventId;
    @SerializedName("EventUniqueId")
    private String eventUniqueId;
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
    @SerializedName("EventStatus")
    private String eventStatus;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;
    @SerializedName("UpdatedOn")
    private String updatedOn;
    @SerializedName("UpdatedOnTime")
    private String updatedOnTime;
    @SerializedName("EventProfile")
    private UserProfilePOJO eventProfile;
    @SerializedName("EventAttendee")
    private List<Object> eventAttendee;
    @SerializedName("EventAttachment")
    private List<EventAttachmentPOJO> eventAttachment;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventUniqueId() {
        return eventUniqueId;
    }

    public void setEventUniqueId(String eventUniqueId) {
        this.eventUniqueId = eventUniqueId;
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

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
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

    public UserProfilePOJO getEventProfile() {
        return eventProfile;
    }

    public void setEventProfile(UserProfilePOJO eventProfile) {
        this.eventProfile = eventProfile;
    }

    public List<Object> getEventAttendee() {
        return eventAttendee;
    }

    public void setEventAttendee(List<Object> eventAttendee) {
        this.eventAttendee = eventAttendee;
    }

    public List<EventAttachmentPOJO> getEventAttachment() {
        return eventAttachment;
    }

    public void setEventAttachment(List<EventAttachmentPOJO> eventAttachment) {
        this.eventAttachment = eventAttachment;
    }
}
