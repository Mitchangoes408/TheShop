package com.main.theshop;

import java.util.Date;

public class Appointments {
    private String cutType;
    private Date scheduledDate;
    private Boolean isPaid;
    private Boolean isComplete;

    public void Appointments(String cutType, Date scheduledDate, Boolean isComplete, Boolean isPaid) {
        this.cutType = cutType;
        this.scheduledDate = scheduledDate;
        this.isComplete = isComplete;
        this.isPaid = isPaid;
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
}
