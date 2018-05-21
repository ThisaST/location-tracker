package com.uok.se.thisara.smart.trackerapplication.model;

public class Bus {

    private String registrationNo;
    private String ownerName;
    private Integer imageId;
    private String busModel;


    public Bus(String registrationNo, String ownerName, Integer imageId, String busModel) {
        this.registrationNo = registrationNo;
        this.ownerName = ownerName;
        this.imageId = imageId;
        this.busModel = busModel;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getBusModel() {
        return busModel;
    }

    public void setBusModel(String busModel) {
        this.busModel = busModel;
    }
}
