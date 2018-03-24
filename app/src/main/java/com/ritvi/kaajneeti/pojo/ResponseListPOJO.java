package com.ritvi.kaajneeti.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 20-03-2018.
 */

public class ResponseListPOJO<T> {

    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    List<T> resultList;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
