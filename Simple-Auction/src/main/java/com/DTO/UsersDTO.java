package com.DTO;

/**
 * For client and server
 */
public class UsersDTO {

    private String strUserName;
    private String strHashPassWord;
    private int intBalance;
    private Boolean boolLockStatus;

    public UsersDTO() {
        this.strUserName = "PLayer-Name: NULL";
    }

    public UsersDTO(String strUserName, String strHashPassWord, int intBalance, Boolean boolLockStatus) {
        this.strUserName = strUserName;
        this.strHashPassWord = strHashPassWord;
        this.intBalance = intBalance;
        this.boolLockStatus = boolLockStatus;
    }




    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrHashPassWord() {
        return strHashPassWord;
    }

    public void setStrHashPassWord(String strHashPassWord) {
        this.strHashPassWord = strHashPassWord;
    }

    public int getIntBalance() {
        return intBalance;
    }

    public void setIntBalance(int intBalance) {
        this.intBalance = intBalance;
    }

    public Boolean getBoolLockStatus() {
        return boolLockStatus;
    }

    public void setBoolLockStatus(Boolean boolLockStatus) {
        this.boolLockStatus = boolLockStatus;
    }}
