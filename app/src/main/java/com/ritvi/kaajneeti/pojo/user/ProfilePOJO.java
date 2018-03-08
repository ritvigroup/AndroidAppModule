package com.ritvi.kaajneeti.pojo.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 08-02-2018.
 */

public class ProfilePOJO {
    @SerializedName("c_profile_detail")
    ProfileRolePOJO c_profile_detail;
    @SerializedName("l_profile_detail")
    ProfileRolePOJO l_profile_detail;


    public ProfileRolePOJO getC_profile_detail() {
        return c_profile_detail;
    }

    public void setC_profile_detail(ProfileRolePOJO c_profile_detail) {
        this.c_profile_detail = c_profile_detail;
    }

    public ProfileRolePOJO getL_profile_detail() {
        return l_profile_detail;
    }

    public void setL_profile_detail(ProfileRolePOJO l_profile_detail) {
        this.l_profile_detail = l_profile_detail;
    }
}
