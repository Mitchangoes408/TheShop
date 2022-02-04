package com.main.theshop;

import java.util.UUID;

public class User {
    private String mUserName;
    private String mPassword;
    private UUID id;
    private boolean isClient;

    public User(String userName, String password, boolean userType) {
        this.mUserName = userName;
        this.mPassword = password;
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
}
