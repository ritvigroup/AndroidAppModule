package com.ritvi.kaajneeti.pojo.payment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 09-04-2018.
 */

public class WalletDetailPOJO implements Serializable{
    @SerializedName("MyWalletBalance")
    private String myWalletBalance;
    @SerializedName("TotalDebit")
    private String totalDebit;
    @SerializedName("TotalCredit")
    private String totalCredit;

    public String getMyWalletBalance() {
        return myWalletBalance;
    }

    public void setMyWalletBalance(String myWalletBalance) {
        this.myWalletBalance = myWalletBalance;
    }

    public String getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(String totalDebit) {
        this.totalDebit = totalDebit;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }
}
