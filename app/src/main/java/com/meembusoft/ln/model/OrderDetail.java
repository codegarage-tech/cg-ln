package com.meembusoft.ln.model;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail {

    private String id = "";
    private String order_id_name = "";
    private String order_timestamp = "";
    private String receiver_name = "";
    private String shipping_address = "";
    private int total_price = 0;
    private int cart_quantity = 0;
    private int current_status = 0;
    private List<OrderStatus> status_list = new ArrayList<>();
//    private List<OrderItem> items = new ArrayList<>();

    public OrderDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id_name() {
        return order_id_name;
    }

    public void setOrder_id_name(String order_id_name) {
        this.order_id_name = order_id_name;
    }

    public String getOrder_timestamp() {
        return order_timestamp;
    }

    public void setOrder_timestamp(String order_timestamp) {
        this.order_timestamp = order_timestamp;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(int cart_quantity) {
        this.cart_quantity = cart_quantity;
    }

    public int getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(int current_status) {
        this.current_status = current_status;
    }

    public List<OrderStatus> getStatus_list() {
        return status_list;
    }

    public void setStatus_list(List<OrderStatus> status_list) {
        this.status_list = status_list;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", order_id_name='" + order_id_name + '\'' +
                ", order_timestamp='" + order_timestamp + '\'' +
                ", receiver_name='" + receiver_name + '\'' +
                ", shipping_address='" + shipping_address + '\'' +
                ", total_price=" + total_price +
                ", cart_quantity=" + cart_quantity +
                ", current_status=" + current_status +
                ", status_list=" + status_list +
                '}';
    }
}