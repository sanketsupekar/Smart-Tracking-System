package com.computerstudent.smarttrackingdevice.activity_history;

public class LocationHistoryDatabase {
    String time;
    Double latitude;
    Double longitude;
    public LocationHistoryDatabase()
    {

    }

    public LocationHistoryDatabase(String time, Double latitude, Double longitude) {
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}

