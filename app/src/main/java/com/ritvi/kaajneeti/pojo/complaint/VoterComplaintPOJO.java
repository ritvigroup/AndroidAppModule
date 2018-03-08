package com.ritvi.kaajneeti.pojo.complaint;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 07-02-2018.
 */

public class VoterComplaintPOJO {
    @SerializedName("id")
    String id;
    @SerializedName("complaint_id")
    String complaint_id;
    @SerializedName("c_name")
    String c_name;
    @SerializedName("c_father_name")
    String c_father_name;
    @SerializedName("c_email")
    String c_email;
    @SerializedName("c_aadhaar_number")
    String c_aadhaar_number;
    @SerializedName("c_subject")
    String c_subject;
    @SerializedName("c_description")
    String c_description;
    @SerializedName("c_status")
    String c_status;
    @SerializedName("c_added_on_dt")
    String c_added_on_dt;
    @SerializedName("c_added_on")
    String c_added_on;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_father_name() {
        return c_father_name;
    }

    public void setC_father_name(String c_father_name) {
        this.c_father_name = c_father_name;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public String getC_aadhaar_number() {
        return c_aadhaar_number;
    }

    public void setC_aadhaar_number(String c_aadhaar_number) {
        this.c_aadhaar_number = c_aadhaar_number;
    }

    public String getC_subject() {
        return c_subject;
    }

    public void setC_subject(String c_subject) {
        this.c_subject = c_subject;
    }

    public String getC_description() {
        return c_description;
    }

    public void setC_description(String c_description) {
        this.c_description = c_description;
    }

    public String getC_status() {
        return c_status;
    }

    public void setC_status(String c_status) {
        this.c_status = c_status;
    }

    public String getC_added_on_dt() {
        return c_added_on_dt;
    }

    public void setC_added_on_dt(String c_added_on_dt) {
        this.c_added_on_dt = c_added_on_dt;
    }

    public String getC_added_on() {
        return c_added_on;
    }

    public void setC_added_on(String c_added_on) {
        this.c_added_on = c_added_on;
    }
}
