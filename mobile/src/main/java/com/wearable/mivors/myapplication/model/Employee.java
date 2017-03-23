package com.wearable.mivors.myapplication.model;

/**
 * Created by mivors on 01/02/17.
 */

public class Employee {

    private String PERSON_NUMBER;
    private String PERSON_ID;
    private String FULL_NAME;
    private String DEPARTMENT;
    private String POSITION_NAME;
    private String JOB_NAME;
    private String LOCATION_NAME;

    public Employee() {
    }

    public Employee(String PERSON_NUMBER, String PERSON_ID, String FULL_NAME,
                    String DEPARTMENT, String POSITION_NAME, String JOB_NAME, String LOCATION_NAME) {
        this.PERSON_NUMBER = PERSON_NUMBER;
        this.PERSON_ID = PERSON_ID;
        this.FULL_NAME = FULL_NAME;
        this.DEPARTMENT = DEPARTMENT;
        this.POSITION_NAME = POSITION_NAME;
        this.JOB_NAME = JOB_NAME;
        this.LOCATION_NAME = LOCATION_NAME;
    }

    public String getPERSON_NUMBER() {
        return PERSON_NUMBER;
    }

    public void setPERSON_NUMBER(String PERSON_NUMBER) {
        this.PERSON_NUMBER = PERSON_NUMBER;
    }

    public String getPERSON_ID() {
        return PERSON_ID;
    }

    public void setPERSON_ID(String PERSON_ID) {
        this.PERSON_ID = PERSON_ID;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }

    public String getPOSITION_NAME() {
        return POSITION_NAME;
    }

    public void setPOSITION_NAME(String POSITION_NAME) {
        this.POSITION_NAME = POSITION_NAME;
    }

    public String getJOB_NAME() {
        return JOB_NAME;
    }

    public void setJOB_NAME(String JOB_NAME) {
        this.JOB_NAME = JOB_NAME;
    }

    public String getLOCATION_NAME() {
        return LOCATION_NAME;
    }

    public void setLOCATION_NAME(String LOCATION_NAME) {
        this.LOCATION_NAME = LOCATION_NAME;
    }
}
