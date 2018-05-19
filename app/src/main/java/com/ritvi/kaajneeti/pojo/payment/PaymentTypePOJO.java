package com.ritvi.kaajneeti.pojo.payment;

import com.google.gson.annotations.SerializedName;

public class PaymentTypePOJO {
    @SerializedName("feedtype")
    String feedtype;
    @SerializedName("paymentdata")
    PaymentTransPOJO paymentTransPOJO;
    @SerializedName("pointdata")
    PaymentTransPOJO pointdataTransPOJO;

    public String getFeedtype() {
        return feedtype;
    }

    public void setFeedtype(String feedtype) {
        this.feedtype = feedtype;
    }

    public PaymentTransPOJO getPaymentTransPOJO() {
        return paymentTransPOJO;
    }

    public void setPaymentTransPOJO(PaymentTransPOJO paymentTransPOJO) {
        this.paymentTransPOJO = paymentTransPOJO;
    }

    public PaymentTransPOJO getPointdataTransPOJO() {
        return pointdataTransPOJO;
    }

    public void setPointdataTransPOJO(PaymentTransPOJO pointdataTransPOJO) {
        this.pointdataTransPOJO = pointdataTransPOJO;
    }
}
