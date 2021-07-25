package com.meembusoft.ln.model;

public class Unit {

    private String name;
    private int factoryPrice;
    private int originalPrice;
    private int offerPrice;

    public Unit(String name, int factoryPrice, int originalPrice, int offerPrice) {
        this.name = name;
        this.factoryPrice = factoryPrice;
        this.originalPrice = originalPrice;
        this.offerPrice = offerPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFactoryPrice() {
        return factoryPrice;
    }

    public void setFactoryPrice(int factoryPrice) {
        this.factoryPrice = factoryPrice;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(int offerPrice) {
        this.offerPrice = offerPrice;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", factoryPrice=" + factoryPrice +
                ", originalPrice=" + originalPrice +
                ", offerPrice=" + offerPrice +
                '}';
    }
}