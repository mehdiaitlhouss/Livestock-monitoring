package com.miola.livestockmonitoring.model;

import androidx.annotation.Keep;

@Keep
public class Animal {

    private String name;
    private Temperature temperature;
    private Gps gps;

    public Animal(String name, Temperature temperature, Gps gps) {
        this.name = name;
        this.temperature = temperature;
        this.gps = gps;
    }

    public Animal() {
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", temperature=" + temperature +
                ", gps=" + gps +
                '}';
    }
}
