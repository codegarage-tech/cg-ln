package com.meembusoft.ln.model.colormatchtab;

import java.util.List;

public class Unit {

    private String name;
    private int originalPrice;
    private int offerPrice;

    public Unit(String name, int originalPrice, int offerPrice) {
        this.name = name;
        this.originalPrice = originalPrice;
        this.offerPrice = offerPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", originalPrice=" + originalPrice +
                ", offerPrice=" + offerPrice +
                '}';
    }
}