package com.ritvi.kaajneeti.pojo.payment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 06-04-2018.
 */

public class PaymentPOJO implements Serializable{
    @SerializedName("PaymentGatewayId")
    private String paymentGatewayId;
    @SerializedName("PaymentGatewayName")
    private String paymentGatewayName;
    @SerializedName("PaymentGatewayDescription")
    private String paymentGatewayDescription;
    @SerializedName("PaymentGatewayStatus")
    private String paymentGatewayStatus;

    public String getPaymentGatewayId() {
        return paymentGatewayId;
    }

    public void setPaymentGatewayId(String paymentGatewayId) {
        this.paymentGatewayId = paymentGatewayId;
    }

    public String getPaymentGatewayName() {
        return paymentGatewayName;
    }

    public void setPaymentGatewayName(String paymentGatewayName) {
        this.paymentGatewayName = paymentGatewayName;
    }

    public String getPaymentGatewayDescription() {
        return paymentGatewayDescription;
    }

    public void setPaymentGatewayDescription(String paymentGatewayDescription) {
        this.paymentGatewayDescription = paymentGatewayDescription;
    }

    public String getPaymentGatewayStatus() {
        return paymentGatewayStatus;
    }

    public void setPaymentGatewayStatus(String paymentGatewayStatus) {
        this.paymentGatewayStatus = paymentGatewayStatus;
    }
}
