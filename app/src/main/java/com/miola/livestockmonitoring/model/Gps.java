package com.miola.livestockmonitoring.model;

import androidx.annotation.Keep;

@Keep
public class Gps {
    private String name;
    private String timestamp;
    private float longitude;
    private float latitude;

    public Gps(String name, String timestamp, float longitude, float latitude) {
        this.name = name;
        this.timestamp = timestamp;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Gps() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Gps{" +
                "name='" + name + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
