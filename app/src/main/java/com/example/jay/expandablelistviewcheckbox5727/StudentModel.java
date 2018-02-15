package com.example.jay.expandablelistviewcheckbox5727;

/**
 * Created by Jay on 15-02-2018.
 */

public class StudentModel {
    //this keyword came handy here :D
    public StudentModel(String userLoginID, String userName, String stakeHoldertype, boolean isSelected) {
        UserLoginID = userLoginID;
        UserName = userName;
        this.stakeHoldertype = stakeHoldertype;
        this.isSelected = isSelected;
    }

    public StudentModel() {

    }

    //@SerializedName("UserLoginID")
    //@Expose
    private String UserLoginID;
    //@SerializedName("UserName")
    //@Expose
    private String UserName;

    //@SerializedName("UserLocation")
    //@Expose
    private String UserLocation;

    //@SerializedName("UserLocationCode")
    //@Expose
    private String UserLocationCode;

    //@SerializedName("Department")
    //@Expose
    private String Department;
    //@SerializedName("DesignationName")
    //@Expose
    private String DesignationName;

    public String getDesignationName() {
        return DesignationName;
    }

    public void setDesignationName(String designationName) {
        DesignationName = designationName;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    //added by Jay on 23/01/2018
    private String stakeHoldertype;

    public String getStakeHoldertype() {
        return stakeHoldertype;
    }

    public void setStakeHoldertype(String stakeHoldertype) {
        this.stakeHoldertype = stakeHoldertype;
    }

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    //

    public String getUserLoginID() {
        return UserLoginID;
    }

    public void setUserLoginID(String userLoginID) {
        UserLoginID = userLoginID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserLocation() {
        return UserLocation;
    }

    public void setUserLocation(String userLocation) {
        UserLocation = userLocation;
    }

    public String getUserLocationCode() {
        return UserLocationCode;
    }

    public void setUserLocationCode(String userLocationCode) {
        UserLocationCode = userLocationCode;
    }
}
