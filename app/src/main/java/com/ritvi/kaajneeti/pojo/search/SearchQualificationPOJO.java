package com.ritvi.kaajneeti.pojo.search;

import com.google.gson.annotations.SerializedName;

public class SearchQualificationPOJO {
    @SerializedName("Qualification")
    private String qualification;
    @SerializedName("QualificationLocation")
    private String qualificationLocation;
    @SerializedName("QualificationUniversity")
    private String qualificationUniversity;

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getQualificationLocation() {
        return qualificationLocation;
    }

    public void setQualificationLocation(String qualificationLocation) {
        this.qualificationLocation = qualificationLocation;
    }

    public String getQualificationUniversity() {
        return qualificationUniversity;
    }

    public void setQualificationUniversity(String qualificationUniversity) {
        this.qualificationUniversity = qualificationUniversity;
    }
}
