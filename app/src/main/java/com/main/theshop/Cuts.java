package com.main.theshop;

import java.util.Date;
import java.util.UUID;

public class Cuts {
    private UUID mId;           //holds cut ID
    private String mTitle;      //cut description
    private Date mDate;         //date of cut
    private String isFavorite;
    private String cutDetails;
    private String cutType;

    public Cuts() {
        isFavorite = "false";
        this.mId = UUID.randomUUID();
    }

    public Cuts(UUID id) {
        mId = id;
        isFavorite = "false";
        mDate = new Date();
    }

    public String isFavorite() {
        return isFavorite;
    }

    public void setFavorite(String favoriteVal) {
        this.isFavorite = favoriteVal;
    }

    public UUID getmId() {
        return mId;
    }

    public String getPhotoFileName() {
        return "IMG_" + getmId().toString() + ".jpg";
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getCutDetails() {
        return cutDetails;
    }

    public void setCutDetails(String cutDetails) {
        this.cutDetails = cutDetails;
    }

    public String getCutType() {
        return cutType;
    }

    public void setCutType(String cutType) {
        this.cutType = cutType;
    }
}
