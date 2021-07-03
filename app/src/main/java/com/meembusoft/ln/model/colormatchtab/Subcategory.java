package com.meembusoft.ln.model.colormatchtab;

import org.parceler.Parcel;

@Parcel
public class Subcategory {

    private int id;
    private String name;
    private String image;
    private boolean isSelected = false;

    public Subcategory() {
    }

    public Subcategory(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.isSelected = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}