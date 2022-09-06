package com.miola.livestockmonitoring.model;

import androidx.annotation.Keep;

@Keep
public class Cow {
    private Gps gps;
    private Temperature temperature;

    public Cow(Gps gps, Temperature temperature) {
        this.gps = gps;
        this.temperature = temperature;
    }

    public Cow() {
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Cow{" +
                "gps=" + gps +
                ", temperature=" + temperature +
                '}';
    }
}
