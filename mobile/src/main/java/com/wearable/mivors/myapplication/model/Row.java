package com.wearable.mivors.myapplication.model;

/**
 * Created by ahmed on 2/22/2017.
 */
public class Row {

    private int id;
    private String lookupName;
    private String subject;
    private String created;
    private String status;
    private int primaryId;

    public Row() {
    }

    public Row(int id, String lookupName, String subject, String status) {
        this.id = id;
        this.lookupName = lookupName;
        this.subject = subject;
        this.status = status;
    }

    public Row(int id, int primaryId, String lookupName, String subject, String created, String status) {
        this.id = id;
        this.lookupName = lookupName;
        this.subject = subject;
        this.created = created;
        this.status = status;
        this.primaryId = primaryId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Row(int id, int primaryId, String lookupName, String subject, String status) {
        this.id = id;
        this.lookupName = lookupName;
        this.subject = subject;
        this.status = status;
        this.primaryId = primaryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLookupName() {
        return lookupName;
    }

    public void setLookupName(String lookupName) {
        this.lookupName = lookupName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(int primaryId) {
        this.primaryId = primaryId;
    }
}
