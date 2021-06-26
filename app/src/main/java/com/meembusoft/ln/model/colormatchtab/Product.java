package com.meembusoft.ln.model.colormatchtab;


public class Product {

    private int id;
    private String name;
    private String image;
    private String vendor;

    public Product(int id, String name, String image, String vendor) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.vendor = vendor;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", vendor='" + vendor + '\'' +
                '}';
    }
}
