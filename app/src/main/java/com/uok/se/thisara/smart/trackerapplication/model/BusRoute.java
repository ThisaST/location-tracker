package com.uok.se.thisara.smart.trackerapplication.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class BusRoute {

    private String routeId;
    private String routeNumber;
    private String routeName;
    private List<LatLng> routePath;
    private String routeStartingLocation;
    private String routeStoppingLocation;
    private String upOrDown;

    public BusRoute() {
    }


    public BusRoute(String routeNumber, String routeName, String upOrDown) {
        this.routeNumber = routeNumber;
        this.routeName = routeName;
        this.upOrDown = upOrDown;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getRouteName() {
        return routeName;
    }


    public String getRouteStartingLocation() {
        return routeStartingLocation;
    }

    public void setRouteStartingLocation(String routeStartingLocation) {
        this.routeStartingLocation = routeStartingLocation;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<LatLng> getRoutePath() {
        return routePath;
    }

    public void setRoutePath(List<LatLng> routePath) {
        this.routePath = routePath;
    }

    public String getRouteStoppingLocation() {
        return routeStoppingLocation;
    }

    public void setRouteStoppingLocation(String routeStoppingLocation) {
        this.routeStoppingLocation = routeStoppingLocation;
    }

    public String getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(String upOrDown) {
        this.upOrDown = upOrDown;
    }
}