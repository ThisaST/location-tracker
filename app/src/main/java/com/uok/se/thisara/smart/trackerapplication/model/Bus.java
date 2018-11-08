package com.uok.se.thisara.smart.trackerapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@Entity
public class Bus implements Serializable {

    @PrimaryKey
    private int busId;
    private String registrationNo;
    private String ownerName;
    private String imageId;
    private String busModel;

    @Ignore
    public Bus() {
    }

    public Bus(String registrationNo, String ownerName, String imageId, String busModel) {
        this.registrationNo = registrationNo;
        this.ownerName = ownerName;
        this.imageId = imageId;
        this.busModel = busModel;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getBusModel() {
        return busModel;
    }

    public void setBusModel(String busModel) {
        this.busModel = busModel;
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }*/
}
