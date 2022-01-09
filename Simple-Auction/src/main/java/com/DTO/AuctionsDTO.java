package com.DTO;

public class AuctionsDTO {
    private String strUserName;
    private String strProductName;
    private int intPurchasePrice;


    public AuctionsDTO() {

    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrProductName() {
        return strProductName;
    }

    public void setStrProductName(String strProductName) {
        this.strProductName = strProductName;
    }

    public int getIntPurchasePrice() {
        return intPurchasePrice;
    }

    public void setIntPurchasePrice(int intPurchasePrice) {
        this.intPurchasePrice = intPurchasePrice;
    }
}
