package com.ritvi.kaajneeti.pojo.userdetail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 12-04-2018.
 */

public class ProfileResultPOJO {
    @SerializedName("Profile")
    ProfilePOJO profilePOJO;
    @SerializedName("Education")
    List<EducationPOJO> educationPOJOS;
    @SerializedName("Work")
    List<WorkPOJO> workPOJOList;

    public ProfilePOJO getProfilePOJO() {
        return profilePOJO;
    }

    public void setProfilePOJO(ProfilePOJO profilePOJO) {
        this.profilePOJO = profilePOJO;
    }

    public List<EducationPOJO> getEducationPOJOS() {
        return educationPOJOS;
    }

    public void setEducationPOJOS(List<EducationPOJO> educationPOJOS) {
        this.educationPOJOS = educationPOJOS;
    }

    public List<WorkPOJO> getWorkPOJOList() {
        return workPOJOList;
    }

    public void setWorkPOJOList(List<WorkPOJO> workPOJOList) {
        this.workPOJOList = workPOJOList;
    }
}
