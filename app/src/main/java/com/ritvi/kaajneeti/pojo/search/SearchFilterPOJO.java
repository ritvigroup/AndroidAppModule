package com.ritvi.kaajneeti.pojo.search;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.userdetail.WorkPOJO;

import java.util.List;

public class SearchFilterPOJO {
    @SerializedName("Location")
    List<SearchLocationPOJO> searchLocationPOJOS;
    @SerializedName("Qualification")
    List<SearchQualificationPOJO> searchQualificationPOJOS;
    @SerializedName("Work")
    List<WorkPOJO> workPOJOS;

    public List<SearchLocationPOJO> getSearchLocationPOJOS() {
        return searchLocationPOJOS;
    }

    public void setSearchLocationPOJOS(List<SearchLocationPOJO> searchLocationPOJOS) {
        this.searchLocationPOJOS = searchLocationPOJOS;
    }

    public List<SearchQualificationPOJO> getSearchQualificationPOJOS() {
        return searchQualificationPOJOS;
    }

    public void setSearchQualificationPOJOS(List<SearchQualificationPOJO> searchQualificationPOJOS) {
        this.searchQualificationPOJOS = searchQualificationPOJOS;
    }

    public List<WorkPOJO> getWorkPOJOS() {
        return workPOJOS;
    }

    public void setWorkPOJOS(List<WorkPOJO> workPOJOS) {
        this.workPOJOS = workPOJOS;
    }
}
