package com.ritvi.kaajneeti.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 20-03-2018.
 */

public class ResponsePOJO<T> {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    T result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
