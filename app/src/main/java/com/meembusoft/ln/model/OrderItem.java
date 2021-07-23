package com.meembusoft.ln.model;

import org.parceler.Parcel;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
@Parcel
public class OrderItem {

    private String product_id = "";
    private String product_name = "";
    private String product_image = "";
    private String product_unit = "";
    private int product_unit_price = 0;
    private int quantity = 0;

    public OrderItem() {
    }

    public OrderItem(String product_id, String product_name, String product_image, String product_unit, int product_unit_price, int quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_unit = product_unit;
        this.product_unit_price = product_unit_price;
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public int getProduct_unit_price() {
        return product_unit_price;
    }

    public void setProduct_unit_price(int product_unit_price) {
        this.product_unit_price = product_unit_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_image='" + product_image + '\'' +
                ", product_unit='" + product_unit + '\'' +
                ", product_unit_price=" + product_unit_price +
                ", quantity=" + quantity +
                '}';
    }
}