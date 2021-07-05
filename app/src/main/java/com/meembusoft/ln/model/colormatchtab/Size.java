package com.meembusoft.ln.model.colormatchtab;

import java.util.ArrayList;
import java.util.List;

public class Size {

    private String name;
    private List<Price> price = new ArrayList<>();

    public Size(String name, List<Price> price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(List<Price> price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
