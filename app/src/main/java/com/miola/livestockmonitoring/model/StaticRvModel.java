package com.miola.livestockmonitoring.model;

public class StaticRvModel {
     private int image;
     private String text;
     private int background;

    public StaticRvModel(int image, String text, int background) {
        this.image = image;
        this.text = text;
        this.background = background;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
