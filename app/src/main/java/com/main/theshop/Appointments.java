package com.main.theshop;

import java.util.Date;
import java.util.UUID;

public class Appointments {
    private String cutType;
    private String title;
    private Date scheduledDate;
    private Boolean isPaid;
    private Boolean isComplete;
    private UUID cutUUID;

    public Appointments() {
        this.cutUUID = UUID.randomUUID();
    }

    public Appointments(UUID uuid) {
        cutUUID = uuid;
        scheduledDate = new Date();
    }

    public String getCutType() {
        return cutType;
    }

    public void setCutType(String cutType) {
        this.cutType = cutType;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public UUID getCutUUID() {
        return cutUUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
