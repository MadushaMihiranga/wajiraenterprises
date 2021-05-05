package com.wajiraenterprises.controller;

public class shipmenttimerange {

    private String number;
    private String dateofregisterd;

    public shipmenttimerange(String number, String dateofregisterd) {
        this.number = number;
        this.dateofregisterd = dateofregisterd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateofregisterd() {
        return dateofregisterd;
    }

    public void setDateofregisterd(String dateofregisterd) {
        this.dateofregisterd = dateofregisterd;
    }
}
