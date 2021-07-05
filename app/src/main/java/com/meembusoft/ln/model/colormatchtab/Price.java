package com.meembusoft.ln.model.colormatchtab;

public class Price {

    private int original = 0;
    private int offer = 0;

    public Price(int original, int offer) {
        this.original = original;
        this.offer = offer;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    @Override
    public String toString() {
        return "{" +
                "original=" + original +
                ", offer=" + offer +
                '}';
    }
}
