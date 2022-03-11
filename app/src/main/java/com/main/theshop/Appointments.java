package com.main.theshop;

import java.util.Date;
import java.util.UUID;

public class Appointments {
    private String cutType;
    private String title;
    private Date scheduledDate;
    private Boolean isPaid;
    private Boolean isComplete;
    private UUID apptUUID;

    public Appointments() {
        scheduledDate = new Date();
        this.apptUUID = UUID.randomUUID();
    }

    public Appointments(UUID uuid) {
        apptUUID = uuid;
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

    public UUID getApptUUID() {
        return apptUUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
