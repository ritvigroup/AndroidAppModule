package com.ritvi.kaajneeti.pojo.payment;

import com.google.gson.annotations.SerializedName;
import com.ritvi.kaajneeti.pojo.user.OutGoingRequestPOJO;

/**
 * Created by sunil on 06-04-2018.
 */

public class PaymentTransPOJO {
    @SerializedName("PaymentTransactionLogId")
    private String paymentTransactionLogId;
    @SerializedName("PaymentGatewayId")
    private String paymentGatewayId;
    @SerializedName("TransactionId")
    private String transactionId;
    @SerializedName("TransactionDate")
    private String transactionDate;
    @SerializedName("TransactionAmount")
    private String transactionAmount;
    @SerializedName("TransactionShippingAmount")
    private String transactionShippingAmount;
    @SerializedName("DebitOrCredit")
    private String debitOrCredit;
    @SerializedName("TransactionComment")
    private String transactionComment;
    @SerializedName("TransactionStatus")
    private String transactionStatus;
    @SerializedName("AddedOn")
    private String addedOn;
    @SerializedName("AddedOnTime")
    private String addedOnTime;
    @SerializedName("PaymentBy")
    private OutGoingRequestPOJO paymentProfilePOJO;

    public String getPaymentTransactionLogId() {
        return paymentTransactionLogId;
    }

    public void setPaymentTransactionLogId(String paymentTransactionLogId) {
        this.paymentTransactionLogId = paymentTransactionLogId;
    }

    public String getPaymentGatewayId() {
        return paymentGatewayId;
    }

    public void setPaymentGatewayId(String paymentGatewayId) {
        this.paymentGatewayId = paymentGatewayId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionShippingAmount() {
        return transactionShippingAmount;
    }

    public void setTransactionShippingAmount(String transactionShippingAmount) {
        this.transactionShippingAmount = transactionShippingAmount;
    }

    public String getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

    public String getTransactionComment() {
        return transactionComment;
    }

    public void setTransactionComment(String transactionComment) {
        this.transactionComment = transactionComment;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getAddedOnTime() {
        return addedOnTime;
    }

    public void setAddedOnTime(String addedOnTime) {
        this.addedOnTime = addedOnTime;
    }

    public OutGoingRequestPOJO getPaymentProfilePOJO() {
        return paymentProfilePOJO;
    }

    public void setPaymentProfilePOJO(OutGoingRequestPOJO paymentProfilePOJO) {
        this.paymentProfilePOJO = paymentProfilePOJO;
    }
}
