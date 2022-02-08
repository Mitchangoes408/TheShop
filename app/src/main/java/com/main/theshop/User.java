package com.main.theshop;

import java.util.UUID;

public class User {
    private String mUserName, fullName, email, phoneNumber;
    private String mPassword;
    private UUID id;
    private Boolean isClient;

    public User(String userName, String password, String fullName, String email, String phoneNumber, boolean userType) {
        this.mUserName = userName;
        this.mPassword = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isClient = userType;
        this.id = UUID.randomUUID();
    }

    public void setmPassword(String newPassword) {
        this.mPassword = newPassword;
    }

    public void setmUserName(String newUserName) {
        this.mUserName = newUserName;
    }

    public String getmUserName() {
        return this.mUserName;
    }

    public String getmPassword() {
        return this.mPassword;
    }

    public UUID getId() {
        return this.id;
    }

    public boolean getUserType() { return this.isClient; }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
