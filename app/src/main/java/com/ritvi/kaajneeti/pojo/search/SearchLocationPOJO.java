package com.ritvi.kaajneeti.pojo.search;

import com.google.gson.annotations.SerializedName;

public class SearchLocationPOJO {
    @SerializedName("City")
    private String city;
    @SerializedName("Country")
    private String country;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
