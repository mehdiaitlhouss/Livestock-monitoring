package com.miola.livestockmonitoring.model;

import androidx.annotation.Keep;

@Keep
public class Temperature {
    private String timestamp;
    private float value;

    public Temperature(String timestamp, float value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Temperature() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "timestamp='" + timestamp + '\'' +
                ", value=" + value +
                '}';
    }
}
