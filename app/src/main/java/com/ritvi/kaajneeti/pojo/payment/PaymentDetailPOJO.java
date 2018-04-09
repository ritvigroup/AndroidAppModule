package com.ritvi.kaajneeti.pojo.payment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 06-04-2018.
 */

public class PaymentDetailPOJO {
    @SerializedName("PaymentGatewayAPI")
    private String paymentGatewayAPI;
    @SerializedName("PaymentGatewayId")
    private String paymentGatewayId;
    @SerializedName("ApiUrl")
    private String apiUrl;
    @SerializedName("ApiMerchantKey")
    private String apiMerchantKey;
    @SerializedName("ApiUsername")
    private String apiUsername;
    @SerializedName("ApiPassword")
    private String apiPassword;
    @SerializedName("ApiAccessKey")
    private String apiAccessKey;
    @SerializedName("ApiStatus")
    private String apiStatus;

    public String getPaymentGatewayAPI() {
        return paymentGatewayAPI;
    }

    public void setPaymentGatewayAPI(String paymentGatewayAPI) {
        this.paymentGatewayAPI = paymentGatewayAPI;
    }

    public String getPaymentGatewayId() {
        return paymentGatewayId;
    }

    public void setPaymentGatewayId(String paymentGatewayId) {
        this.paymentGatewayId = paymentGatewayId;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiMerchantKey() {
        return apiMerchantKey;
    }

    public void setApiMerchantKey(String apiMerchantKey) {
        this.apiMerchantKey = apiMerchantKey;
    }

    public String getApiUsername() {
        return apiUsername;
    }

    public void setApiUsername(String apiUsername) {
        this.apiUsername = apiUsername;
    }

    public String getApiPassword() {
        return apiPassword;
    }

    public void setApiPassword(String apiPassword) {
        this.apiPassword = apiPassword;
    }

    public String getApiAccessKey() {
        return apiAccessKey;
    }

    public void setApiAccessKey(String apiAccessKey) {
        this.apiAccessKey = apiAccessKey;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }
}
