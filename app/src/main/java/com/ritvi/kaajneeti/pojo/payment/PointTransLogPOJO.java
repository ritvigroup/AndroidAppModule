package com.ritvi.kaajneeti.pojo.payment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 23-04-2018.
 */

public class PointTransLogPOJO {
    @SerializedName("PointTransactionLogId")
    private String pointTransactionLogId;
    @SerializedName("PointByName")
    private String pointByName;
    @SerializedName("PointById")
    private String pointById;
    @SerializedName("TransactionId")
    private String transactionId;
    @SerializedName("TransactionDate")
    private String transactionDate;
    @SerializedName("TransactionPoint")
    private String transactionPoint;
    @SerializedName("TransactionChargePoint")
    private String transactionChargePoint;
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

    public String getPointTransactionLogId() {
        return pointTransactionLogId;
    }

    public void setPointTransactionLogId(String pointTransactionLogId) {
        this.pointTransactionLogId = pointTransactionLogId;
    }

    public String getPointByName() {
        return pointByName;
    }

    public void setPointByName(String pointByName) {
        this.pointByName = pointByName;
    }

    public String getPointById() {
        return pointById;
    }

    public void setPointById(String pointById) {
        this.pointById = pointById;
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

    public String getTransactionPoint() {
        return transactionPoint;
    }

    public void setTransactionPoint(String transactionPoint) {
        this.transactionPoint = transactionPoint;
    }

    public String getTransactionChargePoint() {
        return transactionChargePoint;
    }

    public void setTransactionChargePoint(String transactionChargePoint) {
        this.transactionChargePoint = transactionChargePoint;
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
}
