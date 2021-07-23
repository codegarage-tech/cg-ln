package com.meembusoft.ln.model;

public class Order {

    private String id = "";
    private String order_id_name = "";
    private String order_timestamp = "";
    private String receiver_name = "";
    private String shipping_address = "";
    private int total_price = 0;
    private int order_item_count = 0;
    private int current_status = 0;

    public Order() {
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

    public int getOrder_item_count() {
        return order_item_count;
    }

    public void setOrder_item_count(int order_item_count) {
        this.order_item_count = order_item_count;
    }

    public int getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(int current_status) {
        this.current_status = current_status;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", id_name='" + order_id_name + '\'' +
                ", order_timestamp='" + order_timestamp + '\'' +
                ", receiver_name='" + receiver_name + '\'' +
                ", receiver_address='" + shipping_address + '\'' +
                ", total_amount=" + total_price +
                ", order_item_count=" + order_item_count +
                ", order_status=" + current_status +
                '}';
    }
}