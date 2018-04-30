package com.ritvi.kaajneeti.pojo.profile;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;
import com.ritvi.kaajneeti.pojo.userdetail.AddressPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.EducationPOJO;
import com.ritvi.kaajneeti.pojo.userdetail.WorkPOJO;

import java.util.List;

/**
 * Created by sunil on 21-04-2018.
 */

public class FullProfilePOJO {
    @SerializedName("Profile")
    UserProfilePOJO profilePOJO;
    @SerializedName("Address")
    List<AddressPOJO> addressPOJOS;
    @SerializedName("Education")
    List<EducationPOJO> educationPOJOS;
    @SerializedName("Work")
    List<WorkPOJO> workPOJOList;
    @SerializedName("Friend")
    List<UserProfilePOJO> friendsProfilePOJOS;

    public UserProfilePOJO getProfilePOJO() {
        return profilePOJO;
    }

    public void setProfilePOJO(UserProfilePOJO profilePOJO) {
        this.profilePOJO = profilePOJO;
    }

    public List<AddressPOJO> getAddressPOJOS() {
        return addressPOJOS;
    }

    public void setAddressPOJOS(List<AddressPOJO> addressPOJOS) {
        this.addressPOJOS = addressPOJOS;
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

    public List<UserProfilePOJO> getFriendsProfilePOJOS() {
        return friendsProfilePOJOS;
    }

    public void setFriendsProfilePOJOS(List<UserProfilePOJO> friendsProfilePOJOS) {
        this.friendsProfilePOJOS = friendsProfilePOJOS;
    }
}
