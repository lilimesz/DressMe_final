package com.google.dressme.clothesPage;

public class Clothing {
    public String label, type;
    public Integer image, color;

    public Clothing(String label, String type, int color, int image) {
        this.label = label;
        this.type = type;
        this.color = color;
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

}
