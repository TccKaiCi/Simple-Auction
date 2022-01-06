package com.DTO;

/**
 * For clients & server
 */
public class ProductsDTO {

    private String strProductName;
    private int intStartingPrice;
    private String strImageUrl;
    private Boolean boolSoldStatus;


    public ProductsDTO() {

    }

    public ProductsDTO(String strProductName, int intStartingPrice, String strImageUrl, Boolean boolSoldStatus) {
        this.strProductName = strProductName;
        this.intStartingPrice = intStartingPrice;
        this.strImageUrl = strImageUrl;
        this.boolSoldStatus = boolSoldStatus;
    }
























    public String getStrProductName() {
        return strProductName;
    }

    public void setStrProductName(String strProductName) {
        this.strProductName = strProductName;
    }

    public int getIntStartingPrice() {
        return intStartingPrice;
    }

    public void setIntStartingPrice(int intStartingPrice) {
        this.intStartingPrice = intStartingPrice;
    }

    public String getStrImageUrl() {
        return strImageUrl;
    }

    public void setStrImageUrl(String strImageUrl) {
        this.strImageUrl = strImageUrl;
    }

    public Boolean getBoolSoldStatus() {
        return boolSoldStatus;
    }

    public void setBoolSoldStatus(Boolean boolSoldStatus) {
        this.boolSoldStatus = boolSoldStatus;
    }
}
