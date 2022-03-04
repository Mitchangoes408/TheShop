package com.main.theshop;

import java.util.Date;
import java.util.UUID;

public class Cuts {
    private UUID mId;           //holds cut ID
    private String mTitle;      //cut description
    private Date mDate;         //date of cut

    public Cuts() {
        this.mId = UUID.randomUUID();
    }

    public Cuts(UUID id) {
        mId = id;
        mDate = new Date();
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
}
