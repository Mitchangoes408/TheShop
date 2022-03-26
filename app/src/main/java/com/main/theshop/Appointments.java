package com.main.theshop;

import java.util.Date;
import java.util.UUID;

public class Appointments {
    private String cutType;
    private Date scheduledDate;
    private String apptDetails;
    private UUID apptUUID;

    public Appointments() {

        this.apptUUID = UUID.randomUUID();

        //NEEDS DATE WHEN AN APPOINTMENT IS MADE FOR THE DB TO ACCESS LATER
        scheduledDate = new Date();
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

    public UUID getApptUUID() {
        return apptUUID;
    }

    public String getApptDetails() {
        return apptDetails;
    }

    public void setApptDetails(String apptDetails) {
        this.apptDetails = apptDetails;
    }
}
