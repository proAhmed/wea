package com.wearable.mivors.myapplication.model;

/**
 * Created by ahmed on 2/20/2017.
 */
public class Data {
    private  int id;
    private String lookupName;
    private String createdTime;
    private String updatedTime;

    public Data(int id, String lookupName, String createdTime, String updatedTime) {
        this.id = id;
        this.lookupName = lookupName;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Data() {
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
