package com.ritvi.kaajneeti.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 17-04-2018.
 */

public class DepartmentPOJO {
    @SerializedName("DepartmentId")
    private String departmentId;
    @SerializedName("DepartmentName")
    private String departmentName;
    @SerializedName("DepartmentDescription")
    private String departmentDescription;
    @SerializedName("DepartmentStatus")
    private String departmentStatus;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    public String getDepartmentStatus() {
        return departmentStatus;
    }

    public void setDepartmentStatus(String departmentStatus) {
        this.departmentStatus = departmentStatus;
    }
}
