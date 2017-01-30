package com.wearable.abobakr.myapplication.model;

/**
 * Created by abobakr on 29/01/17.
 */

public class UserData {

    private String userName;
    private String userId;
    private String userPhoto;
    private String department;
    private String late;
    private int imageLoc;
    private String violation;

    public UserData() {
    }

    public UserData(String userName, String userId, String userPhoto, int imageLoc, String violation, String late) {
        this.userName = userName;
        this.userId = userId;
        this.userPhoto = userPhoto;
        this.imageLoc = imageLoc;
        this.violation = violation;
        this.late = late;
    }

    public String getDepartment() {
        return department;
    }

    public UserData(String userName,String department,String violation, String late) {
        this.userName = userName;
        this.department = department;
        this.late = late;
        this.violation = violation;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public UserData(String userName, String violation, String late) {
        this.userName = userName;
         this.violation = violation;
        this.late = late;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public int getImageLoc() {
        return imageLoc;
    }

    public void setImageLoc(int imageLoc) {
        this.imageLoc = imageLoc;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }
}
