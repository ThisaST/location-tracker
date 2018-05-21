package com.uok.se.thisara.smart.trackerapplication.model;

/**
 * Created by malware on 4/3/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusLocation {

    @SerializedName("accuracy")
    @Expose
    private Float accuracy;

    @SerializedName("altitude")
    @Expose
    private Double altitude;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("provider")
    @Expose
    private String provider;

    @SerializedName("speed")
    @Expose
    private Float speed;

    @SerializedName("current_time")
    @Expose
    private Long currentTime;

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long time) {
        this.currentTime = time;
    }

}
