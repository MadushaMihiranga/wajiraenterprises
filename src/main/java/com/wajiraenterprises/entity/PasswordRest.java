package com.wajiraenterprises.entity;

public class PasswordRest {


    private String Email;

    private String userType;

    public PasswordRest(String Email, String userType) {
        this.Email = Email;
        this.userType = userType;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
