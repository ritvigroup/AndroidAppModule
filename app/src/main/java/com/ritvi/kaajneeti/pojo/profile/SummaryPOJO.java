package com.ritvi.kaajneeti.pojo.profile;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 23-04-2018.
 */

public class SummaryPOJO {
    @SerializedName("type")
    String type;
    @SerializedName("total")
    String total;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
