package com.meembusoft.ln.model.colormatchtab;


import java.util.ArrayList;
import java.util.List;

public class Product {

    private int id;
    private String name;
    private String image;
    private String vendor;
    private String note;
    private boolean availability = true;
    private List<Size> size = new ArrayList<>();

    public Product(int id, String name, String image, String vendor, String note, boolean availability, List<Size> size) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.vendor = vendor;
        this.note = note;
        this.availability = availability;
        this.size = size;
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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", vendor='" + vendor + '\'' +
                ", note='" + note + '\'' +
                ", availability=" + availability +
                ", size=" + size +
                '}';
    }
}