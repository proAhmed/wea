package com.wearable.mivors.myapplication.model;

/**
 * Created by ahmed on 2/23/2017.
 */
public class MangerModel {

    private int primaryId;
    private int status;

    public MangerModel() {
    }

    public MangerModel(int primaryId, int status) {
        this.primaryId = primaryId;
        this.status = status;
    }

    public int getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(int primaryId) {
        this.primaryId = primaryId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
