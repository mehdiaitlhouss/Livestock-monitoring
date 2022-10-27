package com.miola.livestockmonitoring.model;

public class DynamicRVModel {
    String name;
    int img;

    public DynamicRVModel(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public DynamicRVModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
